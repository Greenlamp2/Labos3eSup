DROP TYPE varchar2_t FORCE;
DROP TYPE blob_t FORCE;
DROP TYPE integer_t FORCE;
/
create or replace type varchar2_t AS VARRAY(120) OF VARCHAR2(255);
/
create or replace type blob_t AS VARRAY(4) OF blob;
/
create or replace type integer_t AS VARRAY(120) OF INTEGER;