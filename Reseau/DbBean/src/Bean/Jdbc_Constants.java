package Bean;

public interface Jdbc_Constants {
    //Oracle
    final String DRIVER_ORACLE = "oracle.jdbc.OracleDriver";
    final String USERNAME_ORACLE = "CC1";
    final String PASSWORD_ORACLE = "oracle";
    final String HOST_ORACLE = "127.0.0.1";
    final String PORT_ORACLE = "1521";
    final String SID = "orcl";


    //MySQL
    final String DRIVER_MYSQL = "com.mysql.jdbc.Driver";

    final String USERNAME_MYSQL = "root";
    final String PASSWORD_MYSQL = "";
    final String HOST_MYSQL = "127.0.0.1";
    final String PORT_MYSQL = "3306";
    final String DATABASE_NAME = "BD_HOTELS";

    //CSV
    final String DRIVER_CSV = "org.relique.jdbc.csv.CsvDriver";

    final String SEPARATOR = ";";
    final String LOCATION = "C:\\Users\\Greenlamp\\Documents\\GitHub\\Labos3eSup\\Reseau\\DbBean";
}
