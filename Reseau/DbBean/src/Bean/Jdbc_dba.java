/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bean;

import java.io.Serializable;
import java.sql.*;


public class Jdbc_dba implements Serializable{
    private Connection connection = null;
    private String url = null;
    private String driver = null;
    private Statement statement = null;

    Jdbc_dba(){}

    void load(String driver, String url) throws Exception{
        if(driver != null) {
            this.setDriver(driver);
        }else {
            throw (Exception) new Exception("Le driver est null.");
        }

        if(url != null){
            this.setUrl(url);
        }else{
            throw (Exception) new Exception("L'url est null.");
        }
    }

    void loadDriver() throws ClassNotFoundException{
        Class.forName(this.driver);
    }

    public void Disconnect() throws SQLException, Exception{
        if(this.isBusy()){
            throw(new Exception("Statement occupé, déconnection impossible"));
        }else{
            this.getConnection().close();
        }
    }

    void closeStatement() throws Exception, SQLException{
        if(!this.isBusy()){
            throw(new Exception("Statement est null"));
        }else{
            this.getStatement().close();
            this.setStatement(null);
        }
    }

    public void endExtract() throws Exception, SQLException{
        closeStatement();
    }


    boolean isBusy(){
        if(this.getStatement() != null){
            return true;
        }else{
            return false;
        }
    }


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url)  throws Exception{
        if(url != null) {
            this.url = url;
        }else {
            throw (Exception) new Exception("L'url est null.");
        }
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver)  throws Exception{
        if(driver != null) {
            this.driver = driver;
        }else {
            throw (Exception) new Exception("Le driver est null.");
        }
    }

    public Object select(String request) throws Exception{
        if(this.isBusy()){
            throw(new Exception("Statement occupé"));
        }
        Object value;
        ResultSet resultset;
        try {
            this.setStatement(this.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE));
            resultset = this.getStatement().executeQuery(request);
            value = (Object) resultset;
        } catch (SQLException ex) {
            return null;
        }
        return value;
    }

    public String extract(Object tuples, int nb, String colonne){
        try {
            ResultSet resultset = (ResultSet) tuples;
            resultset.absolute(nb);
            String value = resultset.getString(colonne);
            return value;
        } catch (SQLException ex) {
            return null;
        }
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
}
