create or replace
trigger AlimCC2
for insert on COPIES
compound trigger

    type copies_t is table of copies%rowtype;
    listCopies copies_t := copies_t();
    
after each row is
begin
    listCopies.extend;
    listCopies(listCopies.last).idCopie := :new.idCopie;
    listCopies(listCopies.last).physique := :new.physique;
    listCopies(listCopies.last).idMovie := :new.idMovie;
end after each row;


after statement is
    v_idMovie Movies.idMovie%type;
    v_idMovieBefore Movies.idMovie%type;
    v_copie copies%rowtype;
    v_movie Movies%rowtype;
    v_toReplic integer := 1;
    v_count integer;
    v_nameBlob MOVIES_CC2.name_image_blob%type;
    v_image_blob MOVIES_CC2.image_blob%type;
    v_temp integer;
    v_copieFound integer := 0;
    v_idNext integer;
    Exc_Sequence Exception;
    PRAGMA EXCEPTION_INIT(Exc_Sequence, -08002);
    v_count_copie integer := 0;
    v_count_current integer := 0;
    v_reste integer := 0;
begin
    v_idMovieBefore := listCopies(listCopies.first).idMovie;
    for i in listCopies.first .. listCopies.last
    loop
        v_copie := listCopies(i);
        v_idMovie := v_copie.idMovie;
        v_toReplic := 1;
        if(v_idMovie != v_idMovieBefore) then
            v_count_copie := 0;
        end if;
        package_error.report_and_go('v_copie.idCopie: ' || v_copie.idCopie, -20001);
        v_count_current := 0;
        for j in listCopies.first .. listCopies.last
        loop
            if(listCopies(j).idMovie = v_idMovie) then
                v_count_current := v_count_current + 1;
            end if;
        end loop;
        v_reste := v_count_current MOD 3;
        v_count_current := v_count_current - v_reste;
        
        if(v_count_current >= 3 AND v_idMovie = v_idMovieBefore) then
            if(v_count_copie < 3) then
                package_error.report_and_go('v_count_copie : ' ||  v_count_copie, -20001);
                -- ici c'est bien que par 3 copie qu'on passe.
                
                select count(*) into v_count
                from dual
                where v_copie.idCopie in(
                    select idCopie
                    from copies
                    where idMovie in(
                        select idMovie
                        from classifier
                        where nameCertification = 'G'
                    )
                    OR idMovie in(
                        select idMovie
                        from appartenir
                        where nameGenre = 'Animation'
                        OR nameGenre = 'Kids'
                        OR nameGenre = 'Family'
                    )
                );
                if(v_count > 0) then
                    package_error.report_and_go('Bonne catégorie', -20001);
                    select * into v_movie from Movies where idMovie = v_copie.idMovie;
                    --Replication du film
                    BEGIN
                        insert into MOVIES_CC2(idMovie, imdb_id, name, overview, rating, released, trailer, translated, votes, runtime, name_image_blob)
                        values(
                        v_movie.idMovie,
                        v_movie.imdb_id,
                        v_movie.name,
                        v_movie.overview,
                        v_movie.rating,
                        v_movie.released,
                        v_movie.trailer,
                        v_movie.translated,
                        v_movie.votes,
                        v_movie.runtime,
                        v_movie.idMovie || '.jpg'
                        );
                    EXCEPTION
                        WHEN DUP_VAL_ON_INDEX THEN v_toReplic := 0;
                    END;
                    
                    package_error.report_and_go('Réplication film: ok', -20001);
                    --replication du poster
                    BEGIN
                        UPDATE MOVIES_CC2 SET image_blob = (select image_blob from Posters where idMovie = v_copie.idMovie);
                    EXCEPTION
                        WHEN DUP_VAL_ON_INDEX THEN null;
                    END;
                    package_error.report_and_go('Réplication poster: ok', -20001);
                    
                    --initialisation de la sequence
                    BEGIN
                        select SEQ_ID_COPIE_CC2.currval into v_temp from dual;
                    Exception
                        WHEN Exc_Sequence then null;
                        WHEN others then null;
                    END;
                    
                    v_idNext := SEQ_ID_COPIE_CC2.nextval;
                    package_error.report_and_go('v_idNext : ' ||  v_idNext, -20001);
                    
                    --On verifie si la copie a pas déja été supprimé logiquement
                    select count(*) into v_copieFound 
                    from COPIES_CC2
                    where idCopie = v_copie.idCopie 
                    and physique = v_copie.physique 
                    and deleted = '1';
                    if(v_copieFound = 0) then
                        package_error.report_and_go('insertion de la copie', -20001);
                        insert into COPIES_CC2(idCopie, physique, idMovie, deleted) 
                        values(v_copie.idCopie, v_copie.physique, v_copie.idMovie, '0');
                    else
                        package_error.report_and_go('update de la copie', -20001);
                        update COPIES_CC2 set deleted = '0'
                        where idCopie = v_copie.idCopie 
                        and physique = v_copie.physique 
                        and deleted = '1';
                    end if;
                    
                    delete copies where idCopie = v_copie.idCopie;
                    
                    --Replication reste
                    if(v_toReplic = 1) then
                        package_error.report_and_go('Replication des détails', -20001);
                        insert into CASTS_CC2(idCast, nameCast)
                            (select distinct Casts.idCast, Casts.nameCast from Casts, jouer
                            where jouer.idMovie = v_idMovie and Casts.idCast = jouer.idCast
                            and not exists(select * from CASTS_CC2 where idCast = Casts.idCast));

                        insert into JOUER_CC2
                            (select * from jouer where idMovie = v_idMovie
                            and not exists(
                                select * from JOUER_CC2
                                where idCast = Jouer.idCast
                                AND nameJob = Jouer.nameJob
                                AND idMovie = Jouer.idMovie));

                        insert into COUNTRIES_CC2(nameCountry)
                            (select distinct Countries.nameCountry from Countries, Participer
                            where Participer.idMovie = v_idMovie and Countries.nameCountry = Participer.nameCountry
                            and not exists(select * from COUNTRIES_CC2 where nameCountry = Countries.nameCountry));

                        insert into PARTICIPER_CC2
                            (select * from participer where idMovie = v_idMovie
                            and not exists(
                                select * from PARTICIPER_CC2
                                where nameCountry = participer.nameCountry
                                AND idMovie = participer.idMovie));

                        insert into STUDIOS_CC2(idStudio, name)
                            (select distinct Studios.idStudio, Studios.name from Studios, Produire
                            where Produire.idMovie = v_idMovie and Studios.idStudio = Produire.idStudio
                            and not exists(select * from STUDIOS_CC2 where idStudio = Studios.idStudio));
                           
                        insert into PRODUIRE_CC2
                            (select * from produire where idMovie = v_idMovie
                            and not exists(
                                select * from PRODUIRE_CC2
                                where idStudio = produire.idStudio
                                and idMovie = produire.idMovie));

                        insert into GENRES_CC2(nameGenre)
                            (select distinct Genres.nameGenre from Genres, Appartenir
                            where Appartenir.idMovie = v_idMovie and Genres.nameGenre = Appartenir.nameGenre
                            and not exists(select * from GENRES_CC2 where nameGenre = Genres.nameGenre));

                        insert into APPARTENIR_CC2
                            (select * from Appartenir where idMovie = v_idMovie
                            and not exists(
                                select * from APPARTENIR_CC2
                                where nameGenre = appartenir.nameGenre
                                and idMovie = appartenir.idMovie));
                                
                        insert into CLASSIFIER_CC2
                            select * from Classifier where idMovie = v_idMovie
                            and not exists(
                                (select * from CLASSIFIER_CC2
                                where nameCertification = Classifier.nameCertification
                                and idMovie = Classifier.idMovie));

                        insert into LANGUAGES_CC2(namelanguage)
                            (select distinct Languages.namelanguage from Languages, Parler
                            where Parler.idMovie = v_idMovie and Languages.namelanguage = Parler.namelanguage
                            and not exists(select * from LANGUAGES_CC2 where namelanguage = Languages.namelanguage));

                        insert into PARLER_CC2
                            select * from parler where idMovie = v_idMovie
                            and not exists(
                                (select * from PARLER_CC2
                                where nameLanguage = parler.nameLanguage
                                and idMovie = parler.idMovie));
                    end if;
                end if;
                v_count_copie := v_count_copie + 1;
            end if;
        end if;
    end loop;
end after statement;
end AlimCC2;