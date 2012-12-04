DROP TYPE integer_t FORCE;
DROP TYPE varchar2_t FORCE;
/
create or replace type integer_t AS VARRAY(120) OF INTEGER;
/
create or replace type varchar2_t AS VARRAY(120) OF VARCHAR2(255);