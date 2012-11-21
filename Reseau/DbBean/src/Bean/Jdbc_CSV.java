/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bean;

import Helpers.EasyFile;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class Jdbc_CSV extends Jdbc_dba{

    public void init() throws Exception{
        String driver = EasyFile.getConfig("Configs_dbBean", "DRIVER_CSV");
        this.setDriver(driver);
        String location = EasyFile.getConfig("Configs_dbBean", "LOCATION");
        this.setUrl(buildUrl(location));

        loadDriver();
        createConnection();

    }

    private String buildUrl(String location) {
        return "jdbc:relique:csv:" + location;
    }

    private void createConnection() throws SQLException{
        Properties prop = new Properties();
        String sep = EasyFile.getConfig("Configs_dbBean", "SEPARATOR");
        prop.put("separator", sep);
        prop.put("supressHeader", "false");
        prop.put("fileExtension", ".csv");

        this.setConnection(DriverManager.getConnection(this.getUrl(), prop));
    }

    public int count(String table) throws Exception, SQLException{
        int count = 0;
        if(this.isBusy()){
            throw(new Exception("Statement occupé"));
        }
        this.setStatement(this.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE));
        String request = "SELECT * FROM " + table;
        ResultSet resultset = this.getStatement().executeQuery(request);
        while(resultset.next()){
            count ++;
        }
        this.closeStatement();
        return count;
    }

    public void insert(String request) throws Exception, SQLException{
        String[] chaine = getChaine(request);
        String table = chaine[0];
        String data = chaine[1];
        insertIntoEOF(table, data);
    }

    private String[] getChaine(String request) {
        String[] split = request.split("WHERE");
        //String condition = split[1].trim();
        String[] split2 = split[0].trim().split("VALUES");
        String table = split2[0].trim().split("INSERT INTO")[1].trim();
        String data = split2[1].replace("("," ").replace(")", " ").trim();
        //Data => "('vael', 'warren', 0, 'SECRET')"
        String[] split3 = data.split(",");
        String nom = split3[0].replace("'", " ").trim();
        String prenom = split3[1].replace("'", " ").trim();
        String niveau = split3[2].replace("'", " ").trim();
        String pass = split3[3].replace("'", " ").trim();
        String[] chaine = {
            table,
            nom + ";" + prenom + ";" + niveau + ";" + pass
        };
        return chaine;
    }

    private void insertIntoEOF(String table, String data) throws IOException{
        String loc = EasyFile.getConfig("Configs_dbBean", "LOCATION");
        String location = loc + "/" + table + ".csv";
        FileWriter writer = null;
        writer = new FileWriter(location, true);
        writer.write("\n", 0, 1);
        writer.write(data, 0, data.length());
        if(writer != null){
            writer.close();
        }
    }

}
