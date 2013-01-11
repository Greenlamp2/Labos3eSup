--*****************************************************************
--			INTERFACE
--*****************************************************************
create or replace
PACKAGE PACKAGE_CMDTICKET AS
    FUNCTION nbPlaceLibre(p_date varchar2, p_salle Salle.numeroSalle%type) return integer;
    PROCEDURE acheterTicket(p_date varchar2, p_salle Salle.numeroSalle%type, p_nbPlace integer);
    FUNCTION getCmdDetails(p_date varchar2, p_idMovie Movies.idMovie%type) return CMD_T;
END PACKAGE_CMDTICKET;

/
--*****************************************************************
--			BODY
--*****************************************************************

create or replace
PACKAGE BODY PACKAGE_CMDTICKET 
AS 
    FUNCTION nbPlaceLibre(p_date varchar2, p_salle Salle.numeroSalle%type) return integer
    AS
        v_retour integer;
        v_count integer;
    BEGIN
        --On compte le nombre de place dans la salle X a une date/heure précise
        select count(*) into v_count from CommanderTicket
        where numeroSalle = p_salle
        and dateHeureProjection = to_date(p_date, 'DD/MM/YYYY HH24:MI');
        
        if(v_count = 0) then
            select capacite into v_retour
            from salle where numeroSalle = p_salle;
        else
            --On calcule le nombre de place libre en fonction des ticket déja commandé
            select capacite - (
                select sum(nbre) from CommanderTicket
                where dateHeureProjection = to_date(p_date, 'DD/MM/YYYY HH24:MI')
                and numeroSalle = p_salle
            ) into v_retour
            from salle where numeroSalle = p_salle;
        end if;
        
        return v_retour;
    Exception
        when no_data_found then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstNoData,NULL,NULL,'PACKAGE_CMDTICKET','nbPlaceLibre');
        when dup_val_on_index then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstDup,NULL,NULL,'PACKAGE_CMDTICKET','nbPlaceLibre');
        when others then raise;
    END;
    
    PROCEDURE acheterTicket(p_date varchar2, p_salle Salle.numeroSalle%type, p_nbPlace integer)
    AS
        v_date date;
        Exc_Sequence Exception;
        PRAGMA EXCEPTION_INIT(Exc_Sequence, -08002);
        v_temp integer;
        v_nbLibre integer;
    BEGIN
    
        v_date := TO_DATE(p_date, 'DD/MM/YYYY HH24:MI');
        --On initialise la séquence
        BEGIN
            select SEQ_ID_CMDTICKET.currval into v_temp from dual;
        Exception
            WHEN Exc_Sequence then null;
            WHEN others then null;
        END;
        
        --On récupere le nombre de place libre
        v_nbLibre := nbPlaceLibre(p_date, p_salle);
        PACKAGE_ERROR.report_and_go('v_nbLibre: ' || v_nbLibre, -20001);
        PACKAGE_ERROR.report_and_go('p_nbPlace: ' || p_nbPlace, -20001);
        if(v_nbLibre = 0) then
            --Si la salle est complete
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstSalleFull,NULL,NULL,'PACKAGE_CMDTICKET','acheterTicket');
        end if;
        
        if(v_nbLibre >= p_nbPlace) then
            --Si il y a assez de place
            insert into CommanderTicket(idCommandeTicket, dateHeureProjection, numeroSalle, nbre) VALUES(SEQ_ID_CMDTICKET.nextval, v_date, p_salle, p_nbPlace);
        else
            --Si il n'y a pas assez de places
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstNotEnoughPlace,NULL,NULL,'PACKAGE_CMDTICKET','acheterTicket');
        end if;
    Exception
        when no_data_found then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstNoData,NULL,NULL,'PACKAGE_CMDTICKET','acheterTicket');
        when dup_val_on_index then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstDup,NULL,NULL,'PACKAGE_CMDTICKET','acheterTicket');
        when others then raise;
    END;
    
    FUNCTION getCmdDetails(p_date varchar2, p_idMovie Movies.idMovie%type) return CMD_T
    AS
        v_retour CMD_T := CMD_T();
        v_idCopie Copies.idCopie%type;
        v_numeroSalle Salle.numeroSalle%type;
        v_nbPlacesDispo integer;
    BEGIN
        select idCopie, numeroSalle into v_idCopie, v_numeroSalle
        from projection
        where To_char(dateHeureProjection, 'DD/MM/YYYY HH24:MI') = p_date
        and idCopie in(
            select idCopie
            from copies
            where idMovie = p_idMovie
        );
        v_nbPlacesDispo := nbPlaceLibre(p_date, v_numeroSalle);
        v_retour.extend;
        v_retour(v_retour.last) := CMD_SPEC(v_idCopie, v_numeroSalle, v_nbPlacesDispo);
        return v_retour;
    Exception
        when no_data_found then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstNoData,NULL,NULL,'PACKAGE_CMDTICKET','getCmdDetails');
        when dup_val_on_index then
            PACKAGE_CB_EXCEPTIONS.Declencher(PACKAGE_CB_EXCEPTIONS.ConstDup,NULL,NULL,'PACKAGE_CMDTICKET','getCmdDetails');
        when others then raise;
    END;
END PACKAGE_CMDTICKET;