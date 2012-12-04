--Imbriquer dans 3 boucles les insert avec une boucle pour la copie aléatoire, une boucle pour générer un nombre aléatoire et une boucle pour chaque heure dans un vecteur

create or replace
procedure procedure_progCC2
as
    v_idSalle integer_t;
    v_nbJours integer;
    v_occuped integer := 0;
    v_idCopie integer;
    type v_copies_t is table of Copies%rowtype;
    v_copies v_copies_t;
    
begin
    v_nbJours := round(dbms_random.value(1, 21));
    
    select numeroSalle bulk collect into v_idSalle from salle;
    
    --Matin
    --On vérifie si la salle est déja occupé le matin
    v_occuped := 0;
    select count(*) into v_occuped
    from salle
    where numeroSalle in(
        select numeroSalle
        from projection
        where TO_CHAR(dateHeureProjection, 'HH24:MI') = '08:00'
    );
    package_error.report_and_go('v_occuped: ' || v_occuped, -20001);
    if(v_occuped = 0) then
        --On récupere n'importe quelle copie (ici la 1ere)
        select * bulk collect into v_copies
        from Copies;
        
        select idCopie into v_idCopie
        from Copies
        where idCopie not in(
            select idCopie
            from projection
            where TO_CHAR(dateHeureProjection, 'DD/MM/YYYY') <> TO_CHAR(sysdate, 'DD/MM/YYYY')
        )
        and rownum <= 1;
        
        package_error.report_and_go('v_nbJours: ' || v_nbJours, -20001);
        package_error.report_and_go('v_copies(1).idCopie: ' || v_copies(1).idCopie, -20001);
        package_error.report_and_go('v_copies(2).idCopie: ' || v_copies(2).idCopie, -20001);
        package_error.report_and_go('v_copies(3).idCopie: ' || v_copies(3).idCopie, -20001);
        for i in 0 .. v_nbJours-1
        loop
            begin
                insert into projection(dateHeureProjection, numeroSalle, idCopie) values(
                    TO_DATE(TO_CHAR(sysdate+i, 'DD/MM/YYYY') || ' 08:00', 'DD/MM/YYYY HH24:MI'),
                    v_idSalle(1),
                    v_copies(1).idCopie);
                insert into projection(dateHeureProjection, numeroSalle, idCopie) values(
                    TO_DATE(TO_CHAR(sysdate+i, 'DD/MM/YYYY') || ' 08:00', 'DD/MM/YYYY HH24:MI'),
                    v_idSalle(2),
                    v_copies(2).idCopie);
                insert into projection(dateHeureProjection, numeroSalle, idCopie) values(
                    TO_DATE(TO_CHAR(sysdate+i, 'DD/MM/YYYY') || ' 08:00', 'DD/MM/YYYY HH24:MI'),
                    v_idSalle(3),
                    v_copies(3).idCopie);
                Exception
                    when DUP_VAL_ON_INDEX then null;
            end;
        end loop;
    end if;
    
    
    --Apres-midi
    v_occuped := 0;
    select count(*) into v_occuped
    from salle
    where numeroSalle in(
        select numeroSalle
        from projection
        where TO_CHAR(dateHeureProjection, 'HH24:MI') = '14:00'
    );
    
    if(v_occuped = 0) then
        --On récupere n'importe quelle copie (ici la 1ere)
        select idCopie into v_idCopie
        from Copies
        where idCopie not in(
            select idCopie
            from projection
            where TO_CHAR(dateHeureProjection, 'DD/MM/YYYY') <> TO_CHAR(sysdate, 'DD/MM/YYYY')
        )
        and rownum <= 1;
        
        --On génere un nombre aléatoire entre 1 et 21
        for i in 0 .. v_nbJours-1
        loop
            begin
                insert into projection(dateHeureProjection, numeroSalle, idCopie) values(
                    TO_DATE(TO_CHAR(sysdate+i, 'DD/MM/YYYY') || ' 14:00', 'DD/MM/YYYY HH24:MI'),
                    v_idSalle(1),
                    v_copies(1).idCopie);
                insert into projection(dateHeureProjection, numeroSalle, idCopie) values(
                    TO_DATE(TO_CHAR(sysdate+i, 'DD/MM/YYYY') || ' 14:00', 'DD/MM/YYYY HH24:MI'),
                    v_idSalle(2),
                    v_copies(2).idCopie);
                insert into projection(dateHeureProjection, numeroSalle, idCopie) values(
                    TO_DATE(TO_CHAR(sysdate+i, 'DD/MM/YYYY') || ' 14:00', 'DD/MM/YYYY HH24:MI'),
                    v_idSalle(3),
                    v_copies(3).idCopie);
                Exception
                    when DUP_VAL_ON_INDEX then null;
            end;
        end loop;
    end if;
    
    --Soirée X2
    v_occuped := 0;
    select count(*) into v_occuped
    from salle
    where numeroSalle in(
        select numeroSalle
        from projection
        where TO_CHAR(dateHeureProjection, 'HH24:MI') = '20:00'
    );
    
    if(v_occuped = 0) then
        --On récupere n'importe quelle copie (ici la 1ere)
        select idCopie into v_idCopie
        from Copies
        where idCopie not in(
            select idCopie
            from projection
            where TO_CHAR(dateHeureProjection, 'DD/MM/YYYY') <> TO_CHAR(sysdate, 'DD/MM/YYYY')
        )
        and rownum <= 1;
        
        for i in 0 .. v_nbJours-1
        loop
            begin
                insert into projection(dateHeureProjection, numeroSalle, idCopie) values(
                    TO_DATE(TO_CHAR(sysdate+i, 'DD/MM/YYYY') || ' 20:00', 'DD/MM/YYYY HH24:MI'),
                    v_idSalle(1),
                    v_copies(1).idCopie);
                insert into projection(dateHeureProjection, numeroSalle, idCopie) values(
                    TO_DATE(TO_CHAR(sysdate+i, 'DD/MM/YYYY') || ' 20:00', 'DD/MM/YYYY HH24:MI'),
                    v_idSalle(2),
                    v_copies(2).idCopie);
                insert into projection(dateHeureProjection, numeroSalle, idCopie) values(
                    TO_DATE(TO_CHAR(sysdate+i, 'DD/MM/YYYY') || ' 20:00', 'DD/MM/YYYY HH24:MI'),
                    v_idSalle(3),
                    v_copies(3).idCopie);
                Exception
                    when DUP_VAL_ON_INDEX then null;
            end;
        end loop;
    end if;
    
    v_occuped := 0;
    select count(*) into v_occuped
    from salle
    where numeroSalle in(
        select numeroSalle
        from projection
        where TO_CHAR(dateHeureProjection, 'HH24:MI') = '22:00'
    );
    
    if(v_occuped = 0) then
        --On récupere n'importe quelle copie (ici la 1ere)
        select idCopie into v_idCopie
        from Copies
        where idCopie not in(
            select idCopie
            from projection
            where TO_CHAR(dateHeureProjection, 'DD/MM/YYYY') <> TO_CHAR(sysdate, 'DD/MM/YYYY')
        )
        and rownum <= 1;
        
        for i in 0 .. v_nbJours-1
        loop
            begin
                insert into projection(dateHeureProjection, numeroSalle, idCopie) values(
                    TO_DATE(TO_CHAR(sysdate+i, 'DD/MM/YYYY') || ' 22:00', 'DD/MM/YYYY HH24:MI'),
                    v_idSalle(1),
                    v_copies(1).idCopie);
                insert into projection(dateHeureProjection, numeroSalle, idCopie) values(
                    TO_DATE(TO_CHAR(sysdate+i, 'DD/MM/YYYY') || ' 22:00', 'DD/MM/YYYY HH24:MI'),
                    v_idSalle(2),
                    v_copies(2).idCopie);
                insert into projection(dateHeureProjection, numeroSalle, idCopie) values(
                    TO_DATE(TO_CHAR(sysdate+i, 'DD/MM/YYYY') || ' 22:00', 'DD/MM/YYYY HH24:MI'),
                    v_idSalle(3),
                    v_copies(3).idCopie);
                Exception
                    when DUP_VAL_ON_INDEX then null;
            end;
        end loop;
    end if;
    commit;
    Exception
        when no_data_found then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstNoData,NULL,NULL,'procedure_progCC2','procedure_progCC2');
            rollback;
        when dup_val_on_index then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstDup,NULL,NULL,'procedure_progCC2','procedure_progCC2');
            rollback;
        when others then 
            rollback;
            raise;
end;