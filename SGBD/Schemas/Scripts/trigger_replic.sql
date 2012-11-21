--Gérer les commandes en attente dans la génération de nombre aléatoire
--Comment discerner les copies de CB de celles de CC1, et CC2
--Modifier pour faire une table temporaire pour insert le tout avec le package
--faire la réplication du blob

create or replace
trigger trigger_replic
for insert on COPIES
compound trigger

	v_idMovie MOVIES.idMovie%type;
	v_idCopie COPIES.idCopie%type;
	v_first boolean := true;

before statement is
	v_dp integer := 0;
	v_tabCopie integer := 0;
	begin
		PACKAGE_ERROR.report_and_go('==BEFORE STATEMENT==', -20001);
		
		--On compte le nombre de tuple dans replic_copie, si il y en a plus que 1, c'est que ce n'est pas la 1ere insertion de pack de copies.
		select count(*) into v_tabCopie from replic_copie;
		if(v_tabCopie > 1) then
			v_first := false;
		end if;
		
end before statement;

after each row is
	begin
		PACKAGE_ERROR.report_and_go('==AFTER EACH ROW==', -20001);
		
		--On récupère l'id du film et l'id de la copie de l'insert courante.
		v_idMovie := :new.idMovie;
		v_idCopie := :new.idCopie;
end after each row;

after statement is
	v_nbCopie integer := 0;
	v_nbCopier integer := 0;
	v_nbCopieTemp integer := 0;
	v_numAleatoire integer := 0;
	begin
		PACKAGE_ERROR.report_and_go('==AFTER STATEMENT==', -20001);
		
		--Si c'est la premiere insertion de pack de copie:
		if(v_first = true) then
			--On récupère dans la table le nombre de copie éffectué, le nombre de replication déja fait et le numéro aléatoire
			select nbCopie into v_nbCopie from replic_copie where idMovie = v_idMovie;
			select nbCopier into v_nbCopier from replic_copie where idMovie = v_idMovie;
			select numAleatoire into v_numAleatoire from replic_copie where idMovie = v_idMovie;
			
			PACKAGE_ERROR.report_and_go('v_nbCopie: ' || v_nbCopie, -20001);
			PACKAGE_ERROR.report_and_go('v_nbCopier: ' || v_nbCopier, -20001);
			
			--si on a des réplication à faire:
			if(v_nbCopie > 0) then
				PACKAGE_ERROR.report_and_go('v_numAleatoire: ' || v_numAleatoire, -20001);
				
				--Si le numéro aléatoire est null ou 0, on le génère entre 0 et la moitié du nombre de copie.
				if(v_numAleatoire is null OR v_numAleatoire = 0) then
					v_numAleatoire := round(dbms_random.value(0,(v_nbCopie)/2));
					PACKAGE_ERROR.report_and_go('v_numAleatoire généré: ' || v_numAleatoire, -20001);
					
					--On met a jour das la table le numero aleatoire.
					update replic_copie set numAleatoire = v_numAleatoire where idMovie = v_idMovie;
				end if;
				
				--Si le nombre de replication faites est inférieur au numéro aléatoire, on réplique.
				if(v_nbCopier < v_numAleatoire) then
				
					--On fait tous les insert dans la base CC1.
					BEGIN
						insert into MOVIES_CC1 
							select * from movies where movies.idMovie = v_idMovie;
					EXCEPTION
						WHEN DUP_VAL_ON_INDEX THEN NULL;
					END;
					
					insert into COPIES_CC1
						select * from copies where copies.idCopie = v_idCopie;
					
					delete copies where idCopie = v_idCopie;
					
					insert into CASTS_CC1(idCast, nameCast)
						(select Casts.idCast, Casts.nameCast from Casts, jouer
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
						(select Countries.nameCountry from Countries, Participer
						where Participer.idMovie = v_idMovie and Countries.nameCountry = Participer.nameCountry
						and not exists(select * from COUNTRIES_CC1 where nameCountry = Countries.nameCountry));
						
					insert into PARTICIPER_CC1
						(select * from participer where idMovie = v_idMovie
						and not exists(
							select * from PARTICIPER_CC1
							where nameCountry = participer.nameCountry
							AND idMovie = participer.idMovie));
					
					insert into STUDIOS_CC1(idStudio, name)
						(select Studios.idStudio, Studios.name from Studios, Produire
						where Produire.idMovie = v_idMovie and Studios.idStudio = Produire.idStudio
						and not exists(select * from STUDIOS_CC1 where idStudio = Studios.idStudio));
						
					insert into PRODUIRE_CC1
						(select * from produire where idMovie = v_idMovie
						and not exists(
							select * from PRODUIRE_CC1
							where idStudio = produire.idStudio
							and idMovie = produire.idMovie));
					
					insert into GENRES_CC1(nameGenre)
						(select Genres.nameGenre from Genres, Appartenir
						where Appartenir.idMovie = v_idMovie and Genres.nameGenre = Appartenir.nameGenre
						and not exists(select * from GENRES_CC1 where nameGenre = Genres.nameGenre));
						
					insert into APPARTENIR_CC1
						(select * from Appartenir where idMovie = v_idMovie
						and not exists(
							select * from APPARTENIR_CC1
							where nameGenre = appartenir.nameGenre
							and idMovie = appartenir.idMovie));
					
					insert into CLASSIFIER_CC1(nameCertification, idMovie)
						(select Classifier.nameCertification, Classifier.idMovie from Classifier
						where not exists(select * from CLASSIFIER_CC1 where nameCertification = Classifier.nameCertification));
					
					insert into LANGUAGES_CC1(namelanguage)
						(select Languages.namelanguage from Languages, Parler
						where Parler.idMovie = v_idMovie and Languages.namelanguage = Parler.namelanguage
						and not exists(select * from LANGUAGES_CC1 where namelanguage = Languages.namelanguage));
						
					insert into PARLER_CC1
						select * from parler where idMovie = v_idMovie
						and not exists(
							(select * from PARLER_CC1
							where nameLanguage = parler.nameLanguage
							and idMovie = parler.idMovie));

					--On incrémente le nombre de réplication
					v_nbCopier := v_nbCopier + 1;
					
					--On met à jour dans la table le nombre de réplication
					update replic_copie set nbCopier = v_nbCopier where idMovie = v_idMovie;
				end if;
				
			end if;
		end if;
		
		
end after statement;

end trigger_replic;