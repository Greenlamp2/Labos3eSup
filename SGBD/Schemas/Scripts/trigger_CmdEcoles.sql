create or replace 
trigger cmdEcoles 
for insert on projection
compound trigger

type proj_t is table of projection%rowtype;
v_listProj proj_t := proj_t();

after each row is
    begin
        v_listProj.extend;
        v_listProj(v_listProj.last).idCopie := :new.idCopie;
        v_listProj(v_listProj.last).numeroSalle := :new.numeroSalle;
        v_listProj(v_listProj.last).dateHeureProjection := :new.dateHeureProjection;
end after each row;

after statement is
    v_count integer;
    v_nbLibre integer;
    v_proj projection%rowtype;
begin
    for i in v_listProj.first .. v_listProj.last
    loop
        v_proj := v_listProj(i);
        select count(*) into v_count
        from dual
        where (v_proj.idCopie, v_proj.numeroSalle, v_proj.dateHeureProjection) in(
          select idCopie, numeroSalle, dateHeureProjection
          from projection
          where idCopie in(
            select idCopie
            from copies
            where idMovie in(
              select idMovie
              from classifier
              where nameCertification = 'G'
            )
            OR idMovie in(
              select idMovie
              from appartenir
              where nameGenre = 'Animation'
              OR nameGenre = 'Kids'
              OR nameGenre = 'Family'
            )
          )
          and
          TO_DATE(TO_CHAR(dateHeureProjection + ((select runtime from Movies where idMovie = (select idMovie from Copies where idCopie = v_proj.idCopie)) /1440), 'HH24:mi'), 'HH24:mi')
              < TO_DATE('18:00', 'HH24:mi')
        );
    
        if(v_count > 0) then
            v_nbLibre := PACKAGE_CMDTICKET.nbPlaceLibre(To_char(v_proj.dateHeureProjection, 'DD/MM/YYYY HH24:MI'), v_proj.numeroSalle);
            if(v_nbLibre >= 20) then
                PACKAGE_CMDTICKET.acheterTicket(To_char(v_proj.dateHeureProjection, 'DD/MM/YYYY HH24:MI'), v_proj.numeroSalle, 20);
            end if;
        end if;
    end loop;
    Exception
        when no_data_found then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstNoData,NULL,NULL,'trigger','cmdEcoles');
        when dup_val_on_index then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstDup,NULL,NULL,'trigger','cmdEcoles');
        when others then raise;
end after statement;

end cmdEcoles;