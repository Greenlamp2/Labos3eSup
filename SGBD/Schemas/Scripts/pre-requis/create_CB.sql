--Suppression des tables De la base de Donnée CB.
DROP TABLE Erreur CASCADE CONSTRAINTS;
DROP TABLE Movies CASCADE CONSTRAINTS;
DROP TABLE Countries CASCADE CONSTRAINTS;
DROP TABLE Posters CASCADE CONSTRAINTS;
DROP TABLE Copies CASCADE CONSTRAINTS;
DROP TABLE Studios CASCADE CONSTRAINTS;
DROP TABLE Produire CASCADE CONSTRAINTS;
DROP TABLE Participer CASCADE CONSTRAINTS;
DROP TABLE Classifier CASCADE CONSTRAINTS;
DROP TABLE Genres CASCADE CONSTRAINTS;
DROP TABLE Appartenir CASCADE CONSTRAINTS;
DROP TABLE Languages CASCADE CONSTRAINTS;
DROP TABLE Parler CASCADE CONSTRAINTS;
DROP TABLE Complexe CASCADE CONSTRAINTS;
DROP TABLE Commander CASCADE CONSTRAINTS;
DROP TABLE Casts CASCADE CONSTRAINTS;
DROP TABLE Jouer CASCADE CONSTRAINTS;

--Création des tables De la base de Donnée CB.
create table Erreur(
	idErreur number(6)
		constraints pk_erreur primary key,
	errcode integer default 0,
	message varchar2(600),
	dateErreur date default sysdate,
	namePackage varchar2(100),
	nameMethod varchar2(100)
);

create table Movies(
	idMovie number(6)
		constraints pk_movies primary key,
	imdb_id varchar2(255),
	name varchar2(255)
		constraint NN_MOVIES_name check(name is not null),
	overview varchar2(4000),
	rating number(6,2),
	released date,
	trailer varchar2(255),
	translated number(1)
		constraint NN_MOVIES_TRANSLATED check(translated is not null)
		constraints CK_MOVIES_TRANSLATED check(translated in ('0', '1')),
	votes number(6)
);

create table Posters(
	idPoster varchar2(255),
	sizePoster varchar2(255),
	height number(6),
	width number(6),
	image_blob blob default empty_blob(),
	idMovie number(6)
		constraints fk_posters_idMovie references Movies,
		
	constraints pk_posters primary key(idPoster, sizePoster)
);

create table Copies(
	idCopie number(6)
		constraints pk_copies primary key,
	physique number(1)
		constraint nn_copies_physique check(physique is not null)
		constraints ck_copies_physique check(physique in ('0', '1')),
	idMovie number(6)
		constraints fk_copies_idMovie references Movies
);

create table Studios(
	idStudio number(6)
		constraints pk_studios primary key,
	name varchar2(255)
		constraint NN_STUDIOS_name check(name is not null),
		
	constraints u_studios_name unique(name)
);

create table Produire(
	idStudio number(6)
		constraints fk_produire_idStudio references Studios,
	idMovie number(6)
		constraints fk_produire_idMovie references Movies,
		
	constraints pk_produire primary key(idStudio, idMovie)
);

create table Countries(
	nameCountry varchar2(255)
		constraints pk_countries primary key
);

create table Participer(
	nameCountry varchar2(255)
		constraints fk_participer_nameCountry references Countries,
	idMovie number(6)
		constraints fk_participer_idMovie references Movies,
		
	constraints pk_participer primary key(nameCountry, idMovie)
);

create table Classifier(
	nameCertification varchar2(255)
		constraints ck_classifier_name check(nameCertification in ('G', 'MA', 'NC-17', 'NR', 'PG', 'PG-13', 'R', 'TV-14', 'UR', 'XXX')),
	idMovie number(6)
		constraints fk_classifier_idMovie references Movies,
		
	constraints pk_classifier primary key(nameCertification, idMovie)
);

create table Genres(
	nameGenre varchar2(255)
		constraints pk_genres primary key
);

create table Appartenir(
	nameGenre varchar2(255)
		constraints fk_appartenir_nameGenre references Genres,
	idMovie number(6)
		constraints fk_appartenir_idMovie references Movies,
		
	constraints pk_appartenir primary key(nameGenre, idMovie)
);

create table Languages(
	namelanguage varchar2(255)
		constraints pk_languages primary key
);

create table Parler(
	nameLanguage varchar2(255)
		constraints fk_parler_nameLanguage references Languages,
	idMovie number(6)
		constraints fk_parler_idMovie references Movies,
		
	constraints pk_parler primary key(nameLanguage, idMovie)
);

create table Complexe(
	idComplexe number(6)
		constraints pk_complexe primary key,
	nameComplexe varchar2(255)
		constraint NN_COMPLEXE_nc check(nameComplexe is not null)
);

create table Commander(
	idMovie number(6)
		constraints fk_commander_idMovie references Movies,
	idComplexe number(6)
		constraints fk_commander_idComplexe references Complexe,
	dateHeure date,
		
	constraints pk_commander primary key(idMovie, idComplexe, dateHeure)
);

create table Casts(
	idCast number(6)
		constraints pk_casts primary key,
	nameCast varchar2(255)
		constraint NN_CASTS_nc check(nameCast is not null),
		
	constraints u_cast_nameCast unique(nameCast)
);

create table Jouer(
	idCast number(6)
		constraints fk_jouer_idCast references Casts,
	nameJob varchar2(255)
		constraints ck_jouer_nj check(nameJob in ('actor', 'director')),
	idMovie number(6)
		constraints fk_jouer_idMovie references Movies,
	characters varchar2(255),
		
	constraints pk_jouer primary key(idCast, nameJob, idMovie)
);
