/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bean;

import Helpers.EasyFile;
import java.sql.*;


public class Jdbc_MySQL extends Jdbc_dba{
    private String host = null;
    private String port = null;
    private String username = null;
    private String password = null;
    private String dbName = null;

    public void init() throws Exception{

        setHost(EasyFile.getConfig("Configs_dbBean", "HOST_MYSQL"));
        setPort(EasyFile.getConfig("Configs_dbBean", "PORT_MYSQL"));
        setUsername(EasyFile.getConfig("Configs_dbBean", "USERNAME_MYSQL"));
        setPassword(EasyFile.getConfig("Configs_dbBean", "PASSWORD_MYSQL"));
        setDbName(EasyFile.getConfig("Configs_dbBean", "DATABASE_NAME"));
        this.setUrl(buildUrl(getHost(), getPort(), getDbName()));
        this.setDriver(EasyFile.getConfig("Configs_dbBean", "DRIVER_MYSQL"));

        loadDriver();
        createConnection();
    }

    private String buildUrl(String host, String port, String dbName) {
        return "jdbc:mysql://" + host + ":" + port + "/" + dbName;
    }

    private void createConnection() throws SQLException{
        this.setConnection(DriverManager.getConnection(this.getUrl(), this.getUsername(), this.getPassword()));
    }

    public int count(String table) throws Exception, SQLException{
        int count = 0;
        if(this.isBusy()){
            throw(new Exception("Statement occupé"));
        }
        this.setStatement(this.getConnection().createStatement());
        ResultSet resultset = this.getStatement().executeQuery("SELECT count(*) AS 'count' from " + table);
        resultset.next();
        count = resultset.getInt("count");
        closeStatement();
        return count;
    }

    public void update(String request) throws Exception, SQLException{
        if(this.isBusy()){
            throw(new Exception("Statement occupé"));
        }
        this.setStatement(this.getConnection().createStatement());
        this.getStatement().executeUpdate(request);
        closeStatement();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
