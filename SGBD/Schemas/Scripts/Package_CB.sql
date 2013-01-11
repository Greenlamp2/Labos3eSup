--*****************************************************************
--			INTERFACE
--*****************************************************************
create or replace
PACKAGE PACKAGE_CB AS 
	type tab_idCopie is table of copies.idMovie%type;
	
	PROCEDURE insertMovie(
		p_idMovie MOVIES.idMovie%type := NULL,
		p_imdb_id MOVIES.imdb_id%type := NULL,
		p_name MOVIES.name%type := NULL,
		p_overview MOVIES.overview%type := NULL,
		p_rating MOVIES.rating%type := NULL,
		p_released MOVIES.released%type := NULL,
		p_trailer MOVIES.trailer%type := NULL,
		p_translated MOVIES.translated%type := NULL,
		p_votes MOVIES.votes%type := NULL,
		p_nomActors varchar2_t := NULL,
		p_characters varchar2_t := NULL,
		p_nomDirectors varchar2_t := NULL,
		p_nomCountries varchar2_t := NULL,
		p_physique integer := null,
		p_digitale integer := null,
		p_nomStudios varchar2_t := NULL,
		p_nomGenres varchar2_t := NULL,
		p_certification CLASSIFIER.nameCertification%type := NULL,
		p_nomLangues varchar2_t := NULL,
        p_blobs blob_t := NULL,
        p_runtime MOVIES.runtime%type
	);
	PROCEDURE insertCast(p_idMovie MOVIES.idMovie%type := NULL, p_nomCast VARCHAR2 := NULL, p_nomCharacter VARCHAR2 := NULL, p_job VARCHAR2 := NULL);
	PROCEDURE insertCountry(p_idMovie MOVIES.idMovie%type := NULL, p_country VARCHAR2 := NULL);
	PROCEDURE insertCopie(p_idMovie MOVIES.idMovie%type := NULL, p_physique COPIES.physique%type := NULL);
	PROCEDURE insertStudio(p_idMovie MOVIES.idMovie%type := NULL, p_studio VARCHAR2 := NULL);
	PROCEDURE insertGenre(p_idMovie MOVIES.idMovie%type := NULL, p_genre VARCHAR2 := NULL);
	PROCEDURE insertCertification(p_idMovie MOVIES.idMovie%type := NULL, p_certification CLASSIFIER.nameCertification%type := NULL);
	PROCEDURE insertLangue(p_idMovie MOVIES.idMovie%type := NULL, p_langue VARCHAR2 := NULL);
    PROCEDURE insertIntoCopie(p_idMovie MOVIES.idMovie%type, p_physique integer, p_digitale integer);
    PROCEDURE insertBlob(p_idMovie MOVIES.idMovie%type, p_blob blob := NULL);
END PACKAGE_CB;

/
--*****************************************************************
--			BODY
--*****************************************************************

create or replace
PACKAGE BODY PACKAGE_CB AS 
	PROCEDURE insertMovie(
		p_idMovie MOVIES.idMovie%type := NULL,
		p_imdb_id MOVIES.imdb_id%type := NULL,
		p_name MOVIES.name%type := NULL,
		p_overview MOVIES.overview%type := NULL,
		p_rating MOVIES.rating%type := NULL,
		p_released MOVIES.released%type := NULL,
		p_trailer MOVIES.trailer%type := NULL,
		p_translated MOVIES.translated%type := NULL,
		p_votes MOVIES.votes%type := NULL,
		p_nomActors varchar2_t := NULL,
		p_characters varchar2_t := NULL,
		p_nomDirectors varchar2_t := NULL,
		p_nomCountries varchar2_t := NULL,
		p_physique integer := null,
		p_digitale integer := null,
		p_nomStudios varchar2_t := NULL,
		p_nomGenres varchar2_t := NULL,
		p_certification CLASSIFIER.nameCertification%type := NULL,
		p_nomLangues varchar2_t := NULL,
        p_blobs blob_t := NULL,
        p_runtime MOVIES.runtime%type
	)
	AS
	BEGIN
    
        --On insere le film dans la table film, si il est déja présent, on ne traite pas l'exception et on passe à la suite.
        BEGIN
            insert into MOVIES values(p_idMovie, p_imdb_id, p_name,p_overview, p_rating, p_released, p_trailer, p_translated,p_votes, p_runtime, 0);
        EXCEPTION
            WHEN DUP_VAL_ON_INDEX THEN NULL;
        END;
		
        /*merge into Movies
		using dual on (dual.dummy is not null and Movies.idMovie = p_idMovie)
		when not matched then 
			insert values(p_idMovie, p_imdb_id, p_name,p_overview, p_rating, p_released, p_trailer, p_translated,p_votes);*/
			
		--insert into replic_copie(idMovie, nbCopie, nbCopier) values(p_idMovie, p_physique + p_digitale, 0);
		
			
		
		--Si la table contenant les acteurs est null, on sait qu'il n'y a pas d'acteur ni de character à insérer.
		if(p_nomActors is not null) THEN
			for i in 1 .. p_nomActors.count
			LOOP
				--Si la table characters est vide mais qu'on a quand même des acteurs.
				if(p_characters is null) THEN
					insertCast(p_idMovie, p_nomActors(i), null, 'actor');
				ELSE
				--Si la table characters n'est vide et qu'on a des acteurs.
					insertCast(p_idMovie, p_nomActors(i), p_characters(i), 'actor');
				END IF;
			END LOOP;
		END IF;
		
		--Si la table contenant les réalisateurs est null, on sait qu'il n'y a pas de réalisateur à insérer.
		if(p_nomDirectors is not null) THEN
			for i in 1 .. p_nomDirectors.count
			LOOP
				insertCast(p_idMovie, p_nomDirectors(i), null, 'director');
			END LOOP;
		END IF;
		
		--Si la table contenant les pays est null, on sait qu'on a pas de pays à insérer.
		IF(p_nomCountries is not null) THEN
			for i in 1 .. p_nomCountries.count
			LOOP
				insertCountry(p_idMovie, p_nomCountries(i));
			END LOOP;
		END IF;
		
		--Si la table contenant les studio est null, on sait qu'on a pas de studios à insérer.
		if(p_nomStudios is not null) THEN
			for i in 1 .. p_nomStudios.count
			LOOP
				insertStudio(p_idMovie, p_nomStudios(i));
			END LOOP;
		END IF;
		
		--Si la table contenant les genres est null, on sait qu'on a pas de genres à insérer.
		if(p_nomGenres is not null) THEN
			for i in 1 .. p_nomGenres.count
			LOOP
				insertGenre(p_idMovie, p_nomGenres(i));
			END LOOP;
		END IF;
		
		--Si la table contenant les certifications est null, on sait qu'on a pas de certifications à insérer.
		if(p_certification is not null) THEN
			insertCertification(p_idMovie, p_certification);
		END IF;
		
		--Si la table contenant les langues est null, on sait qu'on a pas de langues à insérer.
		if(p_nomLangues is not null) THEN
			for i in 1 .. p_nomLangues.count
			LOOP
				insertLangue(p_idMovie, p_nomLangues(i));
			END LOOP;
		END IF;
        
        if(p_blobs is not null) THEN
            for i in 1 .. p_blobs.count
            loop
                insertBlob(p_idMovie, p_blobs(i));
            end loop;
        end if;
        	
        insertIntoCopie(p_idMovie, p_physique, p_digitale);
		--PACKAGE_CB_EXCEPTIONS.Declencher( PACKAGE_CB_EXCEPTIONS.ConstPersonneExist,NULL,NULL,'PACKAGE_CB','insertMovie');
	EXCEPTION
		WHEN others THEN raise;
	END;
	
	
	
	
	
	PROCEDURE insertCast(p_idMovie MOVIES.idMovie%type := NULL, p_nomCast VARCHAR2 := NULL, p_nomCharacter VARCHAR2 := NULL, p_job VARCHAR2 := NULL)
	AS
		idCastSeq Casts.idCast%type;
		idCastTemp Casts.idCast%type;
		
		flag_continue Boolean := true;
		
		Exc_Sequence Exception;
		PRAGMA EXCEPTION_INIT(Exc_Sequence, -08002);
		idReal Casts.idCast%type;
	BEGIN
		while(flag_continue = true)
		LOOP
			BEGIN
				select SEQ_ID_CAST.currval into idCastSeq from dual;
				flag_continue := false;
				
				DECLARE
				BEGIN
					insert into CASTS(idCast, nameCast) values(idCastSeq, p_nomCast);
				EXCEPTION
					WHEN DUP_VAL_ON_INDEX THEN NULL;	
					WHEN OTHERS THEN RAISE;
				END;
				
				DECLARE
				BEGIN
					select idCast into idReal from casts where nameCast = p_nomCast;
					insert into JOUER(idCast, nameJob, idMovie, Characters) values(idReal, p_job, p_idMovie, p_nomCharacter);
				EXCEPTION
					WHEN DUP_VAL_ON_INDEX THEN NULL;	
					WHEN OTHERS THEN RAISE;
				END;
				
				select SEQ_ID_CAST.nextval into idCastTemp from dual;
			EXCEPTION 	
				WHEN Exc_Sequence THEN
					flag_continue := true;
					select SEQ_ID_CAST.nextval into idCastTemp from dual;
				WHEN DUP_VAL_ON_INDEX THEN
					IF(INSTR( UPPER(SQLERRM) , UPPER('U_CAST_NAMECAST') ) >= 1) THEN NULL;
					ELSE RAISE;
					END IF;
				WHEN OTHERS THEN RAISE;
			END;
		END LOOP;
	END;
	
	
	
	
	
	PROCEDURE insertCountry(p_idMovie MOVIES.idMovie%type := NULL, p_country VARCHAR2 := NULL)
	AS
	BEGIN
		DECLARE
		BEGIN
			insert into COUNTRIES(nameCountry) values(p_country);
		EXCEPTION
			WHEN DUP_VAL_ON_INDEX THEN NULL;	
			WHEN OTHERS THEN RAISE;
		END;
		
		DECLARE
		BEGIN
			insert into PARTICIPER(nameCountry, idMovie) values(p_country, p_idMovie);
		EXCEPTION
			WHEN DUP_VAL_ON_INDEX THEN NULL;
			WHEN OTHERS THEN RAISE;			
		END;
	EXCEPTION
		WHEN DUP_VAL_ON_INDEX THEN NULL;
		WHEN OTHERS THEN RAISE;
	END;
	
	
	
	
	
	PROCEDURE insertCopie(p_idMovie MOVIES.idMovie%type := NULL, p_physique COPIES.physique%type := NULL)
	AS
		idCopie Copies.idCopie%type;
		idCopieTemp Copies.idCopie%type;
		
		flag_continue Boolean := true;
		
		Exc_Sequence Exception;
		PRAGMA EXCEPTION_INIT(Exc_Sequence, -08002);
	BEGIN
		while(flag_continue = true)
		LOOP
			BEGIN
				select SEQ_ID_CAST.currval into idCopie from dual;
				flag_continue := false;
				
				insert into COPIES(idCopie, physique, idMovie) values(idCopie, p_physique, p_idMovie);
				select SEQ_ID_CAST.nextval into idCopieTemp from dual;
			EXCEPTION 	
				WHEN Exc_Sequence THEN
					flag_continue := true;
					select SEQ_ID_CAST.nextval into idCopieTemp from dual;
				WHEN OTHERS THEN RAISE;
			END;
		END LOOP;
	END;
	
	
	
	
	
	PROCEDURE insertStudio(p_idMovie MOVIES.idMovie%type := NULL, p_studio VARCHAR2 := NULL)
	AS
		idStudio STUDIOS.idStudio%type;
		idStudioTemp STUDIOS.idStudio%type;
		
		flag_continue Boolean := true;
		
		Exc_Sequence Exception;
		PRAGMA EXCEPTION_INIT(Exc_Sequence, -08002);
		idReal STUDIOS.idStudio%type;
	BEGIN
		while(flag_continue = true)
		LOOP
			BEGIN
				select SEQ_ID_STUDIO.currval into idStudio from dual;
				flag_continue := false;
				DECLARE
				BEGIN
					insert into STUDIOS(idStudio, name) VALUES(idStudio, p_studio);
				EXCEPTION
					WHEN DUP_VAL_ON_INDEX THEN NULL;	
				END;
				
				DECLARE
				BEGIN
					select idStudio into idReal from STUDIOS where name = p_studio;
					insert into PRODUIRE(idStudio, idMovie) VALUES(idReal, p_idMovie);
				EXCEPTION
					WHEN DUP_VAL_ON_INDEX THEN NULL;
					WHEN OTHERS THEN RAISE;
				END;
				
				select SEQ_ID_STUDIO.nextval into idStudioTemp from dual;
			EXCEPTION 	
				WHEN Exc_Sequence THEN
					flag_continue := true;
					select SEQ_ID_STUDIO.nextval into idStudioTemp from dual;
				WHEN OTHERS THEN RAISE;
			END;
		END LOOP;
	END;
	
	
	
	
	
	PROCEDURE insertGenre(p_idMovie MOVIES.idMovie%type := NULL, p_genre VARCHAR2 := NULL)
	AS
	BEGIN
		DECLARE
		BEGIN
			insert into GENRES(nameGenre) VALUES(p_genre);
		EXCEPTION
			WHEN DUP_VAL_ON_INDEX THEN NULL;
			WHEN OTHERS THEN RAISE;			
		END;
		
		DECLARE
		BEGIN
			insert into APPARTENIR(nameGenre, idMovie) VALUES(p_genre, p_idMovie);
		EXCEPTION
			WHEN DUP_VAL_ON_INDEX THEN NULL;
			WHEN OTHERS THEN RAISE;
		END;
	EXCEPTION
		WHEN OTHERS THEN RAISE;
	END;
	
	
	
	
	
	PROCEDURE insertCertification(p_idMovie MOVIES.idMovie%type := NULL, p_certification CLASSIFIER.nameCertification%type := NULL)
	AS
	BEGIN
		insert into CLASSIFIER(nameCertification, idMovie) VALUES(p_certification, p_idMovie);
	EXCEPTION
		WHEN DUP_VAL_ON_INDEX THEN NULL;	
		WHEN OTHERS THEN RAISE;
	END;
	
	
	
	
	
	PROCEDURE insertLangue(p_idMovie MOVIES.idMovie%type := NULL, p_langue VARCHAR2 := NULL)
	AS
	BEGIN
		DECLARE
		BEGIN
			insert into LANGUAGES(nameLanguage) VALUES(p_langue);
		EXCEPTION
			WHEN DUP_VAL_ON_INDEX THEN NULL;
			WHEN OTHERS THEN RAISE;
		END;
		
		DECLARE
		BEGIN
			insert into PARLER(nameLanguage, idMovie) VALUES(p_langue, p_idMovie);
		EXCEPTION
			WHEN DUP_VAL_ON_INDEX THEN NULL;	
			WHEN OTHERS THEN RAISE;
		END;
	EXCEPTION
		WHEN OTHERS THEN RAISE;
	END;
    
    PROCEDURE insertIntoCopie(p_idMovie MOVIES.idMovie%type, p_physique integer, p_digitale integer)
    AS
		Exc_Sequence Exception;
        Exc_temporaryTable Exception;
		PRAGMA EXCEPTION_INIT(Exc_Sequence, -08002);
		PRAGMA EXCEPTION_INIT(Exc_temporaryTable, -00955);
        idCopie copies.idCopie%type;
        idCopieTemp copies.idCopie%type;
        v_request varchar2(255);
    BEGIN
        BEGIN
        execute immediate'
            create global temporary table copie_temp(
            idCopie number(6),
            physique number(1),
            idMovie number(6)
            ) on commit delete rows
        ';
        EXCEPTION
            WHEN Exc_temporaryTable THEN
                execute immediate'truncate table copie_temp drop storage';
        END;
        
        BEGIN
            select SEQ_ID_COPIE.currval into idCopie from dual;
        EXCEPTION
            WHEN Exc_Sequence then
                select SEQ_ID_COPIE.nextval into idCopieTemp from dual;
            WHEN OTHERS THEN raise;
        END;
        
        v_request := 'insert into copie_temp values(:1, :2, :3)';
        for i in 1 .. p_physique
        loop
            execute immediate v_request using SEQ_ID_COPIE.nextval, 1, p_idMovie;
        end loop;
        
        for i in 1 .. p_digitale
        loop
            execute immediate v_request using SEQ_ID_COPIE.nextval, 0, p_idMovie;
        end loop;
        
        
        execute immediate'insert into copies
            (select idCopie, physique, idMovie from copie_temp)';
        
        
    EXCEPTION
        WHEN OTHERS THEN RAISE;
    END;
    
    PROCEDURE insertBlob(p_idMovie MOVIES.idMovie%type, p_blob blob := NULL)
    AS
    begin
        insert into POSTERS(idPoster, image_blob, idMovie, sizePoster, height, width) values(p_idMovie, p_blob, p_idMovie, 'cover', 185, 282);
    EXCEPTION
        when dup_val_on_index then null;
        WHEN OTHERS THEN RAISE;
    END;
	
END PACKAGE_CB;