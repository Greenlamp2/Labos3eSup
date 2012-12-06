set serveroutput on
declare
    jobno binary_integer;
begin
    dbms_job.submit (jobno, 'procedure_progCC2;', sysdate, 'sysdate + 1/1440');
    dbms_output.put_line ('Numero de job: ' || jobno);
    commit;
end;