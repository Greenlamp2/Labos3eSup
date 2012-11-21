--*****************************************************************
--			INTERFACE
--*****************************************************************
create or replace
PACKAGE PACKAGE_RECHERCHE AS
    FUNCTION doSearch(requete varchar2) return MOVIES_T;
    FUNCTION getDataWithId(p_idMovie MOVIES.idMovie%type) return MOVIES_SPEC;
    FUNCTION getCount(p_requete varchar2) return integer;
    FUNCTION getDetails(p_idMovie integer) return MOVIES_T_DETAILS;
    PROCEDURE insertCommande(p_idMovie MOVIES.idMovie%type, p_nbCopie integer, p_physique integer);
    /*
    FUNCTION doSearchCB(requete varchar2) return MOVIES_T;
    FUNCTION getDataWithIdCB(p_idMovie MOVIES.idMovie%type) return MOVIES_SPEC;
    
	FUNCTION testCall(p_nom varchar2) return varchar2;
    FUNCTION testCall2(p_nom varchar2) return MOVIES_T;
    FUNCTION getByNbTickets(p_min integer, p_max integer) return integer_t;
    FUNCTION getByNbProjection(p_min integer, p_max integer, p_dateMin date) return integer_t;
    FUNCTION getByNbProjection(p_min integer, p_max integer, p_dateMin date, p_movie MOVIES.idMovie%type) return integer_t;
    FUNCTION getByFrequence(p_min integer, p_max integer) return integer_t;
    FUNCTION getByWeekProjection(p_min integer, p_max integer) return integer_t;
    FUNCTION getByCopyNotScheduled(p_min integer, p_max integer) return integer_t;
    FUNCTION getNbWeekBetweenTwoDates(p_dateMin date, p_dateMax date) return integer;*/
    
END PACKAGE_RECHERCHE;

/
--*****************************************************************
--			BODY
--*****************************************************************

create or replace
PACKAGE BODY PACKAGE_RECHERCHE 
AS 
    FUNCTION doSearch(requete varchar2) return MOVIES_T
    AS
        v_movies MOVIES_T := MOVIES_T();
        v_ids integer_t := integer_t();
    BEGIN
        execute immediate requete BULK COLLECT into v_ids;
        PACKAGE_ERROR.report_and_go('v_ids.count: ' || v_ids.count, -20001);
        if(v_ids.count > 0) then
            for i in v_ids.first .. v_ids.last
            loop
                v_movies.extend;
                v_movies(v_movies.last) := getDataWithId(v_ids(i));
            end loop;
        end if;
        return v_movies;
    EXCEPTION
        WHEN others THEN raise;
    END;
    
    FUNCTION getCount(p_requete varchar2) return integer
    AS
        v_count integer := 0;
    BEGIN
        execute immediate p_requete into v_count;
        return v_count;
    EXCEPTION
        WHEN others THEN raise;
    END;
    
    FUNCTION getDetails(p_idMovie integer) return MOVIES_T_DETAILS
    AS
        v_actors varchar2_t;
        v_directors varchar2_t;
        v_released MOVIES.released%type;
        v_details MOVIES_T_DETAILS := MOVIES_T_DETAILS();
    BEGIN
        select nameCast bulk collect into v_actors from casts, jouer
        where jouer.idCast = casts.idCast
        and jouer.idMovie = p_idMovie
        and nameJob = 'actor';
        
        select nameCast bulk collect into v_directors from casts, jouer
        where jouer.idCast = casts.idCast
        and jouer.idMovie = p_idMovie
        and nameJob = 'director';
        
        select released into v_released
        from movies 
        where idMovie = p_idMovie;
        
        v_details.extend;
        v_details(v_details.last) := MOVIES_DETAILS(p_idMovie, v_actors, v_directors, v_released);
        
        return v_details;
    EXCEPTION
        WHEN others THEN raise;
    END;
    
    FUNCTION getDataWithId(p_idMovie MOVIES.idMovie%type) return MOVIES_SPEC
    AS
        v_movie_spec MOVIES_SPEC;
        v_name MOVIES.name%type := NULL;
        affiche blob := empty_blob();
        nbCopieDispo integer := 0;
        popularite integer := 0;
        frequenceProg integer := 0;
        perenite integer := 0;
    BEGIN
        select name into v_name from MOVIES where idMovie = p_idMovie;
        select image_blob into affiche from posters where idMovie = p_idMovie;
        select count(*) into nbCopieDispo
        from copies c 
        where idMovie = p_idMovie 
        and idCopie not in(select idCopie from projection);
        
        select sum(ct.nbre) into popularite from commanderTicket ct, projection p
        where ct.dateHeureProjection = p.dateHeureProjection
        AND ct.numeroSalle = p.numeroSalle
        and p.idCopie in (select idCopie from copies where idMovie = p_idMovie);
        
        select round(
            (decode(max(dateHeureProjection), min(dateHeureProjection), 1, max(dateHeureProjection) - min(dateHeureProjection))) /
            ((to_number(to_char(max(dateHeureProjection), 'WW')) -
            to_number(to_char(min(dateHeureProjection), 'WW')) +
            52 * (to_number(to_char(max(dateHeureProjection), 'YYYY')) -
            to_number(to_char(min(dateHeureProjection), 'YYYY'))))+1)
        ) into frequenceProg
        from dual, projection p
        where p.idCopie in(
        select idCopie from copies where idMovie = p_idMovie);
        
        select 
        ((to_number(to_char(max(p.dateHeureProjection), 'WW')) -
        to_number(to_char(min(p.dateHeureProjection), 'WW')) +
        52 * (to_number(to_char(max(p.dateHeureProjection), 'YYYY')) -
        to_number(to_char(min(p.dateHeureProjection), 'YYYY'))))+1) into perenite
        from projection p
        where p.idCopie in (select idCopie from copies where idMovie = p_idMovie);
        
        PACKAGE_ERROR.report_and_go('p_idMovie: ' || p_idMovie || ' popularite: ' || popularite || ' frequenceProg: ' || frequenceProg || ' perenite: ' || perenite);
        
        v_movie_spec := MOVIES_SPEC(p_idMovie, v_name, affiche, nbCopieDispo, popularite, frequenceProg, perenite);
        return v_movie_spec;
    EXCEPTION
        WHEN others THEN raise;
    END;
    
    PROCEDURE insertCommande(p_idMovie MOVIES.idMovie%type, p_nbCopie integer, p_physique integer)
    AS
        v_nb integer := 0;
    BEGIN
        insert into COMMANDER_CB(idMovie, idComplexe, dateHeure, nbCopie, physique)
        values(
            p_idMovie,
            (select idComplexe from COMPLEXE_CB where nameComplexe = 'CC1'),
            sysdate,
            p_nbCopie,
            p_physique);
    EXCEPTION
        WHEN dup_val_on_index then
            select nbCopie into v_nb from COMMANDER_CB where idMovie = p_idMovie and physique = p_physique;
            v_nb := v_nb + p_nbCopie;
            update COMMANDER_CB SET nbCopie = v_nb
            WHERE idMovie = p_idMovie and physique = p_physique;
        WHEN others THEN raise;
    END;
    
    /*
    
    FUNCTION doSearchCB(requete varchar2) return MOVIES_T
    AS
        v_movies MOVIES_T := MOVIES_T();
        v_ids integer_t := integer_t();
    BEGIN
        execute immediate requete BULK COLLECT into v_ids;
        if(v_ids.count > 0) then
            for i in v_ids.first .. v_ids.last
            loop
                v_movies.extend;
                v_movies(v_movies.last) := getDataWithIdCB(v_ids(i));
            end loop;
        end if;
        return v_movies;
    END;
    
    FUNCTION getDataWithIdCB(p_idMovie MOVIES.idMovie%type) return MOVIES_SPEC
    AS
        v_movie_spec MOVIES_SPEC;
        v_name MOVIES.name%type := NULL;
        affiche blob := empty_blob();
        nbCopieDispo integer := 0;
        popularite integer := 0;
        frequenceProg integer := 0;
        perenite integer := 0;
    BEGIN
        select name into v_name from MOVIES_CB where idMovie = p_idMovie;
        select count(*) into nbCopieDispo from COPIES_CB where idMovie = p_idMovie;
        
        popularite := 0;
        frequenceProg := 0;
        perenite := 0;
        
        v_movie_spec := MOVIES_SPEC(p_idMovie, v_name, affiche, nbCopieDispo, popularite, frequenceProg, perenite);
        return v_movie_spec;
    END;
    
    
    FUNCTION getByNbTickets(p_min integer, p_max integer) return integer_t
    AS
        v_retour integer_t;
    BEGIN
        select mo.idMovie bulk collect into v_retour from movies mo
        where
        (  
            select sum(ct.nbre) from commanderTicket ct, projection p
            where ct.dateHeureProjection = p.dateHeureProjection
            AND ct.numeroSalle = p.numeroSalle
            and p.idCopie in (select idCopie from copies where idMovie = mo.idMovie)
        ) 
        between p_min and p_max;
        
        return v_retour;
    END;
    
    
    FUNCTION getByNbProjection(p_min integer, p_max integer, p_dateMin date) return integer_t
    AS
        v_retour integer_t;
    BEGIN
        select idMovie bulk collect into v_retour from movies mo
        where 
        (
          select count(*) from projection p
          where p.idCopie in (select idCopie from copies where idMovie = mo.idMovie)
          and p.dateHeureProjection >= p_dateMin
          and p.dateHeureProjection <= p_dateMin + 7
        ) between p_min and p_max;
        
        return v_retour;
    END;
    
    
    FUNCTION getByNbProjection(p_min integer, p_max integer, p_dateMin date, p_movie MOVIES.idMovie%type) return integer_t
    AS
        v_retour integer_t;
    BEGIN
        select idMovie bulk collect into v_retour from movies mo
        where
        idMovie = p_movie AND
        (
          select count(*) from projection p, movies
          where p.idCopie in (select idCopie from copies where idMovie = mo.idMovie)
          and p.dateHeureProjection >= p_dateMin
          and p.dateHeureProjection <= p_dateMin + 7
        ) between p_min and p_max;
        
        return v_retour;
    END;
    
    FUNCTION getByFrequence(p_min integer, p_max integer) return integer_t
    AS
        v_listMovies integer_t;
        v_idMovie MOVIES.idMovie%type;
        v_dateMin date;
        v_dateMax date;
        v_dateCurr date;
        v_nbSemaine integer;
        v_retour integer_t := integer_t();
        v_count integer;
        v_last integer := 0;
    BEGIN
        select idMovie bulk collect into v_listMovies
        from movies
        where idMovie in(
            select idMovie from Copies where idCopie in(
                select idCopie from projection
            )
        );
        
        for i in v_listMovies.first .. v_listMovies.last
        loop
            v_idMovie := v_listMovies(i);
            
            select min(dateHeureProjection) into v_dateMin from projection p
            where p.idCopie in(
                select idCopie from copies where idMovie = v_idMovie
            );
            select max(dateHeureProjection) into v_dateMax from projection p
            where p.idCopie in(
                select idCopie from copies where idMovie = v_idMovie
            );
            
            v_nbSemaine := PACKAGE_RECHERCHE.getNbWeekBetweenTwoDates(v_dateMin, v_dateMax);
            
            v_dateCurr := v_dateMin;
            for week in 1 .. v_nbSemaine + 1
            loop
                select count(*) into v_count
                from projection p
                where p.idCopie in (select idCopie from copies where idMovie = v_idMovie)
                and p.dateHeureProjection >= v_dateCurr
                and p.dateHeureProjection <= v_dateCurr + 7;
                
                if(v_count >= p_min and v_count <= p_max) then
                    if(v_last <> v_idMovie) then
                        v_retour.extend;
                        v_retour(v_retour.last) := v_idMovie;
                        v_last := v_idMovie;
                    end if;
                end if;
                
                v_dateCurr := v_dateCurr + 7;
            end loop;
            
        end loop;
        
        return v_retour;
    END;
    
    
    FUNCTION getByWeekProjection(p_min integer, p_max integer) return integer_t
    AS
        v_retour integer_t;
    BEGIN
        select idMovie bulk collect into v_retour from movies mo
        where
        (
            select 
            to_number(to_char(max(p.dateHeureProjection), 'WW')) -
            to_number(to_char(min(p.dateHeureProjection), 'WW')) +
            52 * (to_number(to_char(max(p.dateHeureProjection), 'YYYY')) -
            to_number(to_char(min(p.dateHeureProjection), 'YYYY'))) diff_week
            from projection p
            where p.idCopie in (select idCopie from copies where idMovie = mo.idMovie)
        ) between p_min and p_max;
        
        return v_retour;
    END;
    
    FUNCTION getByCopyNotScheduled(p_min integer, p_max integer) return integer_t
    AS
        v_retour integer_t;
    BEGIN
        select idMovie bulk collect into v_retour from movies mo
        where(
        select count(*) from copies c
        where idMovie = mo.idMovie
        and idCopie not in(select idCopie from projection)
        ) between p_min and p_max;
        
        return v_retour;
    END;
    
    FUNCTION getNbWeekBetweenTwoDates(p_dateMin date, p_dateMax date) return integer
    AS
        v_retour integer;
    BEGIN
        select 
        to_number(to_char(p_dateMax, 'WW')) -
        to_number(to_char(p_dateMin, 'WW')) +
        52 * (to_number(to_char(p_dateMax, 'YYYY')) -
        to_number(to_char(p_dateMin, 'YYYY')))
        into v_retour
        from dual;
        
        return v_retour;
    END;*/
    
    
    
    
    
    
    
    /* TESTS */
    /*
	FUNCTION testCall(p_nom varchar2) return varchar2
    AS
    BEGIN
        return 'Bonjour monde: ' || p_nom;
    END;

	FUNCTION testCall2(p_nom varchar2) return MOVIES_T
    AS
        v_movies MOVIES_T := MOVIES_T();
    BEGIN
        PACKAGE_ERROR.report_and_go('On est au début.', -20001);
        for i in (select * from movies)
        loop
            v_movies.extend;
            v_movies(v_movies.last) := (MOVIES_SPEC(i.idMovie, i.imdb_id, i.name, i.overview, i.rating, i.released, i.trailer, i.translated, i.votes));
        end loop;
        
        return v_movies;
    END;*/
    
    
END PACKAGE_RECHERCHE;