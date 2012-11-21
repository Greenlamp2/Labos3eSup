create or replace package PACKAGE_CB_EXCEPTIONS
AS

    ConstDupVal CONSTANT NUMBER := -20002;
    ExcDupVal  EXCEPTION;
    PRAGMA EXCEPTION_INIT (ExcDupVal ,-20002);
    
    ConstNoData CONSTANT NUMBER := -20003;
    ExcNoData  EXCEPTION;
    PRAGMA EXCEPTION_INIT (ExcNoData ,-20003);
    
    ConstDup CONSTANT NUMBER := -20004;
    ExcDup  EXCEPTION;
    PRAGMA EXCEPTION_INIT (ExcDup ,-20004);
    
    ConstSalleFull CONSTANT NUMBER := -20005;
    ExcSalleFull  EXCEPTION;
    PRAGMA EXCEPTION_INIT (ExcSalleFull ,-20005);
    
    ConstNotEnoughPlace CONSTANT NUMBER := -20006;
    ExcNotEnoughPlace  EXCEPTION;
    PRAGMA EXCEPTION_INIT (ExcNotEnoughPlace ,-20006);

    PROCEDURE Declencher( NrErreur IN NUMBER,
                          Message IN erreur.errmessage%TYPE := NULL,					  
                          WhoIsTheErrSource IN erreur.username%TYPE := NULL,
                          PackageErrSource IN erreur.namePackage%TYPE := NULL,
                          MethodErrSource IN erreur.nameMethod%TYPE := NULL );
                      
END PACKAGE_CB_EXCEPTIONS;

/

create or replace package body PACKAGE_CB_EXCEPTIONS
AS
    NrErreurInvalide CONSTANT NUMBER := -20999;
    TYPE TypeTableMessErreur IS TABLE OF VARCHAR2(200) INDEX BY BINARY_INTEGER;
    TableMessErreur TypeTableMessErreur;

    PROCEDURE Declencher( NrErreur IN NUMBER,
                          Message IN erreur.errmessage%TYPE := NULL,					  
                          WhoIsTheErrSource IN erreur.username%TYPE := NULL,
                          PackageErrSource IN erreur.namePackage%TYPE := NULL,
                          MethodErrSource IN erreur.nameMethod%TYPE := NULL ) IS
        pragma autonomous_transaction;
            v_CurrentDate date;
        BEGIN
            RAISE_APPLICATION_ERROR(NrErreur, TableMessErreur(NrErreur));		
        EXCEPTION
        /* Référence de table non définie*/
        WHEN NO_DATA_FOUND	THEN  
            RAISE_APPLICATION_ERROR(NrErreur, TableMessErreur(NrErreurInvalide));
        WHEN OTHERS THEN

        SELECT CURRENT_DATE INTO v_CurrentDate 
        FROM DUAL;	
        
        Package_ERROR.report_and_go(TableMessErreur(NrErreurInvalide), NrErreur);
        
        commit;
        RAISE_APPLICATION_ERROR(NrErreur, TableMessErreur(NrErreur));		
    END Declencher;
    
    BEGIN
        TableMessErreur(NrErreurInvalide) := 'Nr erreur hors des limites';
        TableMessErreur(ConstDupVal ) := 'La donnée existe déja';
        TableMessErreur(ConstNoData ) := 'Aucunes données trouvées';
        TableMessErreur(ConstDup ) := 'Violation de clé primaire';
        TableMessErreur(ConstSalleFull ) := 'La salle est complète';
        TableMessErreur(ConstNotEnoughPlace ) := 'Il n''y a pas assez de places dans la salle';

END PACKAGE_CB_EXCEPTIONS;