create or replace
trigger trigger_replic_amelio
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
    v_dejaPresent integer := 0;
    v_numAleatoire integer := 0;
    v_idMovie Movies.idMovie%type;
    v_count integer := 0;
    v_flag integer := 0;
    v_copieFound integer := 0;
    begin
        --Récupération de l'id du film par facilité
        v_idMovie := listCopies(listCopies.first).idMovie;
        
        select replicated into v_flag from movies where idMovie = v_idMovie;
        /*
        select count(*) into v_dejaPresent from copies where idMovie = v_idMovie;
        v_dejaPresent := v_dejaPresent - listCopies.count;*/
        
        --Si aucunes copies pour ce film n'a déja été faite:
        --if(v_dejaPresent = 0) then
        if(v_flag = 0) then
            v_count := listCopies.count;
            PACKAGE_ERROR.report_and_go('v_count: ' || v_count, -20001);
            v_numAleatoire := round(dbms_random.value(1, (v_count / 2)));
            PACKAGE_ERROR.report_and_go('v_numAleatoire: ' || v_numAleatoire, -20001);
            
            for i in 1 .. listCopies.count
            loop
                if(i <= v_numAleatoire) then
                    v_idMovie := listCopies(i).idMovie;
                    
                    --Insertion des Movies dans CC1:
                    BEGIN
                        insert into MOVIES_CC1
                            select idMovie, imdb_id, name, overview, rating, released, trailer, translated, votes, runtime
                            from movies where movies.idMovie = v_idMovie;
                    EXCEPTION
                        WHEN DUP_VAL_ON_INDEX THEN NULL;
                    END;
                    
                    select count(*) into v_copieFound from COPIES_CC1 where idCopie = listCopies(i).idCopie and physique = listCopies(i).physique and deleted = '1';
                    if(v_copieFound = 0) then
                        insert into COPIES_CC1(idCopie, physique, idMovie, deleted) 
                        values(listCopies(i).idCopie, listCopies(i).physique, listCopies(i).idMovie, '0');
                    else
                        update COPIES_CC1 set deleted = '0'
                        where idCopie = listCopies(i).idCopie and physique = listCopies(i).physique and deleted = '1';
                    end if;
                    
                    delete copies where idCopie = listCopies(i).idCopie;
                end if;
            end loop;
            
            if(v_flag = 0) then
                PACKAGE_ERROR.report_and_go('v_idMovie: ' || v_idMovie, -20001);

                insert into CASTS_CC1(idCast, nameCast)
                    (select distinct Casts.idCast, Casts.nameCast from Casts, jouer
                    where jouer.idMovie = v_idMovie and Casts.idCast = jouer.idCast
                    and not exists(select * from CASTS_CC1 where idCast = Casts.idCast));

                insert into JOUER_CC1
                    (select * from jouer where idMovie = v_idMovie
                    and not exists(
                        select * from JOUER_CC1 
                        where idCast = Jouer.idCast
                        AND nameJob = Jouer.nameJob
                        AND idMovie = Jouer.idMovie));

                insert into COUNTRIES_CC1(nameCountry)
                    (select distinct Countries.nameCountry from Countries, Participer
                    where Participer.idMovie = v_idMovie and Countries.nameCountry = Participer.nameCountry
                    and not exists(select * from COUNTRIES_CC1 where nameCountry = Countries.nameCountry));

                insert into PARTICIPER_CC1
                    (select * from participer where idMovie = v_idMovie
                    and not exists(
                        select * from PARTICIPER_CC1
                        where nameCountry = participer.nameCountry
                        AND idMovie = participer.idMovie));

                insert into STUDIOS_CC1(idStudio, name)
                    (select distinct Studios.idStudio, Studios.name from Studios, Produire
                    where Produire.idMovie = v_idMovie and Studios.idStudio = Produire.idStudio
                    and not exists(select * from STUDIOS_CC1 where idStudio = Studios.idStudio));
                   
                insert into PRODUIRE_CC1
                    (select * from produire where idMovie = v_idMovie
                    and not exists(
                        select * from PRODUIRE_CC1
                        where idStudio = produire.idStudio
                        and idMovie = produire.idMovie));

                insert into GENRES_CC1(nameGenre)
                    (select distinct Genres.nameGenre from Genres, Appartenir
                    where Appartenir.idMovie = v_idMovie and Genres.nameGenre = Appartenir.nameGenre
                    and not exists(select * from GENRES_CC1 where nameGenre = Genres.nameGenre));

                insert into APPARTENIR_CC1
                    (select * from Appartenir where idMovie = v_idMovie
                    and not exists(
                        select * from APPARTENIR_CC1
                        where nameGenre = appartenir.nameGenre
                        and idMovie = appartenir.idMovie));

                /*insert into CLASSIFIER_CC1(nameCertification, idMovie)
                    (select Classifier.nameCertification, Classifier.idMovie from Classifier
                    where not exists(select * from CLASSIFIER_CC1 where nameCertification = Classifier.nameCertification));*/
                    
                /*insert into CLASSIFIER_CC1(nameCertification, idMovie)
                    (select Classifier.nameCertification, Classifier.idMovie from Classifier);*/
                    
                insert into CLASSIFIER_CC1
                    select * from Classifier where idMovie = v_idMovie
                    and not exists(
                        (select * from CLASSIFIER_CC1
                        where nameCertification = Classifier.nameCertification
                        and idMovie = Classifier.idMovie));

                insert into LANGUAGES_CC1(namelanguage)
                    (select distinct Languages.namelanguage from Languages, Parler
                    where Parler.idMovie = v_idMovie and Languages.namelanguage = Parler.namelanguage
                    and not exists(select * from LANGUAGES_CC1 where namelanguage = Languages.namelanguage));

                insert into PARLER_CC1
                    select * from parler where idMovie = v_idMovie
                    and not exists(
                        (select * from PARLER_CC1
                        where nameLanguage = parler.nameLanguage
                        and idMovie = parler.idMovie));

                insert into POSTERS_CC1(idPoster, sizePoster, height, width, image_blob, idMovie)
                    (select Posters.idPoster, Posters.sizePoster, Posters.height, Posters.width, Posters.image_blob, Posters.idMovie from Posters
                    where not exists(select * from POSTERS_CC1 where idPoster = Posters.idPoster));
                    
                update movies set replicated = 1 where idMovie = v_idMovie;
            end if;
        end if;
end after statement;

end trigger_replic_amelio;