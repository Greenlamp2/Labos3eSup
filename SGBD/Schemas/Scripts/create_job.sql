/*declare
begin
    DBMS_SCHEDULER.DROP_JOB('trCmdCopieJob');
    DBMS_SCHEDULER.CREATE_JOB(
        job_name => 'trCmdCopieJob',
        job_type => 'PLSQL_BLOCK',
        job_action => 'begin livraison; end;',
        start_date => systimestamp,
        --repeat_interval => 'freq=hourly; byminute=0; bysecond=0;',
        --repeat_interval => 'freq=minutely; interval=1',
        repeat_interval => 'freq=secondly; interval=10',
        end_date => null,
        enabled => false,
        comments => 'job trCmdCopie'
    );
    dbms_scheduler.run_job('trCmdCopieJob');
    commit;
end;*/

set serveroutput on
declare
    jobno binary_integer;
begin
    dbms_job.submit (jobno, 'livraison;', sysdate, 'sysdate + 1/1440');
    dbms_output.put_line ('Numero de job: ' || jobno);
    commit;
end;
