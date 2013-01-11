--*****************************************************************
--			INTERFACE
--*****************************************************************
create or replace
PACKAGE PACKAGE_PROGRAMMATION AS
    FUNCTION getAllProjection(p_name varchar2) return PROJECTION_T;
    FUNCTION getDetail(p_idMovie integer) return DETAIL_T;
    FUNCTION getDispos(p_name varchar2) return DISPOS_T;
    FUNCTION verifProjection(p_idMovie Movies.idMovie%type, p_idCopie Copies.idCopie%type, p_date varchar2, p_salle Salle.numeroSalle%type) return integer;
    PROCEDURE insertProjection(p_date varchar2, p_salle Salle.numeroSalle%type, p_idCopie Copies.idCopie%type);
    PROCEDURE prolongerProjection(p_date varchar2, p_salle Salle.numeroSalle%type, p_idCopie Copies.idCopie%type, p_duree integer);
END PACKAGE_PROGRAMMATION;

/
--*****************************************************************
--			BODY
--*****************************************************************

create or replace
PACKAGE BODY PACKAGE_PROGRAMMATION
AS 
    FUNCTION getAllProjection(p_name varchar2) return PROJECTION_T
    AS
        v_projection PROJECTION_T := PROJECTION_T();
        type v_projection_type is table of projection%rowtype;
        v_projection_t v_projection_type;
        v_movie Movies%rowtype;
    BEGIN
        --On récupere toutes les projection trié par date
        select *  bulk collect into v_projection_t from PROJECTION order by dateHeureProjection;
        PACKAGE_ERROR.report_and_go('v_projection_t.count: ' || v_projection_t.count, -20001);
        
        --On parcourt la liste des projections
        for i in v_projection_t.first .. v_projection_t.last
        loop
            v_projection.extend;
            PACKAGE_ERROR.report_and_go('i: ' || i || ' (' || v_projection_t(i).idCopie || ')', -20001);
            --On récupere les informations sur le film concerné par la projection
            select * into v_movie from movies
            where idMovie = (
                select idMovie from copies
                where idCopie = v_projection_t(i).idCopie)
            order by idMovie;
            PACKAGE_ERROR.report_and_go('i: ' || i || ' OK', -20001);
            
            --On complete la liste du type personalisée
            v_projection(v_projection.last) := PROJECTION_SPEC(
                TO_DATE(TO_CHAR(v_projection_t(i).dateHeureProjection, 'DD/MM/YYYY HH24:mi'), 'DD/MM/YYYY HH24:mi'),
                v_projection_t(i).numeroSalle, 
                v_projection_t(i).idCopie, 
                v_movie.idMovie, 
                v_movie.name, 
                v_movie.runtime
            );
        end loop;
        PACKAGE_ERROR.report_and_go('v_projection.count: ' || v_projection.count, -20001);
        return v_projection;
    Exception
        --when no_data_found then null;
        when others then raise;
    END;
    
    FUNCTION getDetail(p_idMovie integer) return DETAIL_T
    AS
        retour DETAIL_T := DETAIL_T();
        v_movie Movies%rowtype;
        v_affiche blob := empty_blob();
        v_actors varchar2_t := varchar2_t();
        v_directors varchar2_t := varchar2_t();
        v_studios varchar2_t := varchar2_t();
        v_langues varchar2_t := varchar2_t();
        v_genres varchar2_t := varchar2_t();
        v_certifications varchar2_t := varchar2_t();
        v_copies integer_t := integer_t();
        v_projections varchar2_t := varchar2_t();
    BEGIN
        BEGIN
        select * into v_movie from movies where idMovie = p_idMovie;
        Exception
            when no_data_found then null;
            when others then raise;
        END;
        
        BEGIN
        select image_blob into v_affiche from Posters where idMovie = p_idMovie;
        Exception
            when no_data_found then null;
            when others then raise;
        END;
        
        BEGIN
        select Casts.nameCast bulk collect into v_actors
        from Casts, Jouer
        where Casts.idCast = Jouer.idCast
        and nameJob = 'actor'
        and idMovie = p_idMovie;
        Exception
            when no_data_found then null;
            when others then raise;
        END;
        
        BEGIN
        select nameCast bulk collect into v_directors
        from Casts, Jouer
        where Casts.idCast = Jouer.idCast
        and nameJob = 'director'
        and idMovie = p_idMovie;
        Exception
            when no_data_found then null;
            when others then raise;
        END;
        
        BEGIN
        select Studios.name bulk collect into v_studios
        from Studios, Produire
        where Studios.idStudio = Produire.idStudio
        and idMovie = p_idMovie;
        Exception
            when no_data_found then null;
            when others then raise;
        END;
        
        BEGIN
        select Languages.namelanguage bulk collect into v_langues
        from Languages, Parler
        where Languages.namelanguage = Parler.namelanguage
        and idMovie = p_idMovie;
        Exception
            when no_data_found then null;
            when others then raise;
        END;
        
        BEGIN
        select Genres.nameGenre bulk collect into v_genres
        from Genres, Appartenir
        where Genres.nameGenre = Appartenir.nameGenre
        and idMovie = p_idMovie;
        Exception
            when no_data_found then null;
            when others then raise;
        END;
        
        BEGIN
        select Classifier.nameCertification bulk collect into v_certifications
        from Classifier
        where idMovie = p_idMovie;
        Exception
            when no_data_found then null;
            when others then raise;
        END;
        
        BEGIN
        select idCopie bulk collect into v_copies
        from Copies
        where idMovie = p_idMovie;
        Exception
            when no_data_found then null;
            when others then raise;
        END;
        
        BEGIN
        select 'h: ' || dateHeureProjection || ' c: ' || idCopie
        bulk collect into v_projections
        from Projection
        where Projection.idCopie in (
            select distinct idCopie from Copies where idMovie = p_idMovie)
        order by dateHeureProjection;
        
        Exception
            when no_data_found then null;
            when others then raise;
        END;
        
        retour.extend;
        retour(retour.last) := DETAIL_SPEC(
            v_movie.idMovie,
            v_movie.imdb_id,
            v_movie.name,
            v_movie.overview,
            v_movie.rating,
            v_movie.released,
            v_movie.trailer,
            v_movie.translated,
            v_movie.votes,
            v_affiche,
            v_actors,
            v_directors,
            v_studios,
            v_langues,
            v_genres,
            v_certifications,
            v_copies,
            v_projections,
            v_movie.runtime);
            
        return retour;
    Exception
        when no_data_found then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstNoData,NULL,NULL,'PACKAGE_Programmation','getDetail');
        when others then raise;
    END;
    
    FUNCTION getDispos(p_name varchar2) return DISPOS_T
    AS
        v_dispos DISPOS_T := DISPOS_T();
        v_idCopies integer_t;
        type movies_t is table of movies%rowtype;
        v_movies movies_t;
        v_salles integer_t;
    BEGIN
        --On récupere les films qui possède une copie dans le complexe
        select * bulk collect into v_movies
        from movies
        where idMovie in(
            select distinct idMovie from copies)
        order by name;
        
        select numeroSalle bulk collect into v_salles from Salle;
            
        for i in v_movies.first .. v_movies.last
        loop
            select idCopie bulk collect into v_idCopies
            from copies
            where idMovie = v_movies(i).idMovie;
            
            v_dispos.extend;
            v_dispos(v_dispos.last) := DISPOS_SPEC(v_movies(i).idMovie, v_movies(i).name, v_idCopies, v_salles, v_movies(i).runtime);
        end loop;
        
        return v_dispos;
    Exception
        when no_data_found then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstNoData,NULL,NULL,'PACKAGE_Programmation','getDispos');
        when others then raise;
    END;
    
    FUNCTION verifProjection(p_idMovie Movies.idMovie%type, p_idCopie Copies.idCopie%type, p_date varchar2, p_salle Salle.numeroSalle%type) return integer
    AS
        v_retour integer;
        v_date date;
        v_count_copiesEnCours integer;
        v_count_salleEnCours integer;
    BEGIN
        PACKAGE_ERROR.report_and_go('p_date: ' || p_date, -20001);
        v_date := TO_DATE(p_date, 'DD/MM/YYYY HH24:MI');
        
        --On vérifie d'abord si la copie n'est pas déja joué ailleurs dans une autre salle à l'heure X.
        --Donc, la copie est-elle dispo dans cette tranche horaire ?
        select count(*) into v_count_copiesEnCours
        from projection 
        where idCopie = p_idCopie
        and
        TO_DATE(TO_CHAR(dateHeureProjection, 'DD/MM/YYYY HH24:mi'), 'DD/MM/YYYY HH24:mi') 
            <= TO_DATE(p_date, 'DD/MM/YYYY HH24:mi')
        and
        TO_DATE(TO_CHAR(dateHeureProjection + ((select runtime from Movies where idMovie = (select idMovie from Copies where idCopie = p_idCopie)) /1440), 'DD/MM/YYYY HH24:mi'), 'DD/MM/YYYY HH24:mi')
            >= TO_DATE(p_date, 'DD/MM/YYYY HH24:mi');
            
        PACKAGE_ERROR.report_and_go('v_count_copiesEnCours: ' || v_count_copiesEnCours, -20001);
        if(v_count_copiesEnCours > 0) then
            return -1;
        end if;
        
        --On vérifie ensuite si la salle ne joue pas déja une copie à cette heure X.
        --Donc, la salle est-elle dispo dans cette tranche horaire ?
        select distinct count(*) into v_count_salleEnCours
        from projection p, movies m
        where p.idCopie in(
            select idCopie from copies
            where idMovie = m.idMovie)
        and numeroSalle = p_salle
        and
        TO_DATE(TO_CHAR(dateHeureProjection, 'DD/MM/YYYY HH24:mi'), 'DD/MM/YYYY HH24:mi') 
            <= TO_DATE(p_date, 'DD/MM/YYYY HH24:mi')
        and
        TO_DATE(TO_CHAR(dateHeureProjection + ((select runtime from Movies where idMovie = m.idMovie) /1440), 'DD/MM/YYYY HH24:mi'), 'DD/MM/YYYY HH24:mi')
            >= TO_DATE(p_date, 'DD/MM/YYYY HH24:mi');
            
        PACKAGE_ERROR.report_and_go('v_count_salleEnCours: ' || v_count_salleEnCours, -20001);
        
        if(v_count_salleEnCours > 0) then
            return -2;
        end if;

        return 1;
    Exception
        when no_data_found then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstNoData,NULL,NULL,'PACKAGE_Programmation','verifProjection');
        when others then raise;
    END;
    
    PROCEDURE insertProjection(p_date varchar2, p_salle Salle.numeroSalle%type, p_idCopie Copies.idCopie%type)
    AS
        v_date date;
    BEGIN
        v_date := TO_DATE(p_date, 'DD/MM/YYYY HH24:MI');
        insert into Projection(dateHeureProjection, numeroSalle, idCopie)
        values(v_date, p_salle, p_idCopie);
    Exception
        when no_data_found then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstNoData,NULL,NULL,'PACKAGE_Programmation','insertProjection');
        when dup_val_on_index then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstDup,NULL,NULL,'PACKAGE_Programmation','insertProjection');
        when others then raise;
    END;
    
    PROCEDURE prolongerProjection(p_date varchar2, p_salle Salle.numeroSalle%type, p_idCopie Copies.idCopie%type, p_duree integer)
    AS
        type projection_t is table of Projection%rowtype;
        v_projections projection_t := projection_t();
        v_date date;
        v_indice integer;
    BEGIN
        v_date := TO_DATE(p_date, 'DD/MM/YYYY HH24:MI');
        
        
        --On récupère les projection qui corresponde à la date, au numero de salle
        select * bulk collect into v_projections
        from projection
        where numeroSalle = p_salle
        and To_Char(dateHeureProjection, 'DD/MM/YYYY') = To_Char(v_date, 'DD/MM/YYYY')
        AND dateHeureProjection >= v_date
        order by dateHeureProjection;
        
        if(v_projections.count > 0) then
            for i in v_projections.first .. v_projections.last-1
            loop
                v_indice := v_projections.last+1 - i;
                update projection set dateHeureProjection = v_projections(v_indice).dateHeureProjection + (p_duree/1440)
                where dateHeureProjection = v_projections(v_indice).dateHeureProjection
                and numeroSalle = p_salle
                and idCopie = p_idCopie;
            end loop;
        end if;
    Exception
        when no_data_found then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstNoData,NULL,NULL,'PACKAGE_Programmation','prolongerProjection');
        when dup_val_on_index then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstDup,NULL,NULL,'PACKAGE_Programmation','prolongerProjection');
        when others then raise;
    END;
    
END PACKAGE_PROGRAMMATION;