create or replace trigger retourCopie
after insert
on copies
declare
    type copies_t is table of copies%rowtype;
    v_copies copies_t;
    v_ids integer_t;
    v_copie copies%rowtype;
    v_temp integer := 0;
begin
    --On récupere toutes les copies dont la date des projections sont inférieures à aujourd'hui
    --Et donc la copie n'est plus projetée plus tard et qui n'a pas déja été supprimé.
    select * bulk collect into v_copies from copies
    where idCopie in(
    select distinct idCopie from projection
    where dateHeureProjection < (select sysdate from dual)
    and idCopie not in(
    select idCopie from copies where deleted = '1')
    and idCopie not in(
    select idCopie from projection
    where dateHeureProjection >= (select sysdate from dual))
    );  
    --package_error.report_and_go('v_copies.count: ' || v_copies.count, -20001);
    --Si on à des copies qui correspondent
    if(v_copies.count > 0) then
        for i in v_copies.first .. v_copies.last
        loop
          --package_error.report_and_go('i: ' || i, -20001);
          --On récupere la copie correspondante à l'id
          select * into v_copie from copies where idCopie = v_copies(i).idCopie;
          --package_error.report_and_go('v_copie.idCopie: ' || v_copie.idCopie, -20001);
            --on transmet à cb
            insert into COPIES_CB(idCopie, physique, idMovie)
            values(v_copie.idCopie, v_copie.physique, v_copie.idMovie);
            package_error.report_and_go('insert ok', -20001);
            --On fait la suppression logique.
            update copies set deleted='1' where idCopie = v_copies(i).idCopie;
            package_error.report_and_go('update ok', -20001);
        end loop;
    end if;
exception
    when others then raise;
end;