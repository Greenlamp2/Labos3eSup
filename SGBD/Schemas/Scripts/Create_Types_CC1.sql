DROP TYPE MOVIES_SPEC FORCE;
DROP TYPE MOVIES_DETAILS FORCE;
DROP TYPE MOVIES_T FORCE;
DROP TYPE MOVIES_T_DETAILS FORCE;
DROP TYPE PROJECTION_SPEC FORCE;
DROP TYPE PROJECTION_T FORCE;
DROP TYPE DETAIL_SPEC FORCE;
DROP TYPE DETAIL_T FORCE;
DROP TYPE DISPOS_SPEC FORCE;
DROP TYPE DISPOS_T FORCE;
DROP TYPE CMD_SPEC FORCE;
DROP TYPE CMD_T FORCE;
DROP TYPE integer_t FORCE;
DROP TYPE varchar2_t FORCE;
/
create or replace type integer_t AS VARRAY(120) OF INTEGER;
/
create or replace type varchar2_t AS VARRAY(120) OF VARCHAR2(255);
/
create or replace type MOVIES_SPEC IS OBJECT (
	idMovie number(6),
	name varchar2(255),
    affiche blob,
    nbCopieDispo number(6),
    popularite number(6),
    frequenceProg number(6),
    perenite number(6)
);
/
create or replace type PROJECTION_SPEC IS OBJECT (
	dateHeureProjection date,
	numeroSalle number(6),
	idCopie number(6),
    idMovie number(6),
    name varchar2(255),
    runtime number(6)
);
/
create or replace type DISPOS_SPEC IS OBJECT (
    idMovie number(6),
    name varchar2(255),
    idCopies integer_t,
    salles integer_t,
    runtime number(6)
);
/
create or replace type DETAIL_SPEC IS OBJECT (
	idMovie number(6),
	imdb_id varchar2(255),
	name varchar2(255),
	overview varchar2(1500),
	rating number(6,2),
	released date,
	trailer varchar2(255),
	translated number(1),
	votes number(6),
	image_blob blob,
    actors varchar2_t,
    directors varchar2_t,
    studios varchar2_t,
    langues varchar2_t,
    genres varchar2_t,
    certifications varchar2_t,
    copies integer_t,
    projections varchar2_t,
    runtime number(6)
);
/
create or replace type MOVIES_DETAILS IS OBJECT (
    idMovie number(6),
    actors varchar2_t,
    directors varchar2_t,
	released date
);
/
create or replace type CMD_SPEC IS OBJECT (
    idCopie number(6),
    numeroSalle number(6),
    nbPlacesDispo integer
);
/
create or replace type MOVIES_T_DETAILS is table of MOVIES_DETAILS;
/
create or replace type MOVIES_T is table of MOVIES_SPEC;
/
create or replace type PROJECTION_T is table of PROJECTION_SPEC;
/
create or replace type DETAIL_T is table of DETAIL_SPEC;
/
create or replace type DISPOS_T is table of DISPOS_SPEC;
/
create or replace type CMD_T is table of CMD_SPEC;