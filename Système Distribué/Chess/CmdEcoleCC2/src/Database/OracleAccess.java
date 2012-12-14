/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import Helpers.EasyFile;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OracleAccess {
    private Connection con = null;
    private CallableStatement csmt;
    private Statement smt;

    public OracleAccess(String db){
        String driver = null;
        String host = null;
        String port = null;
        String user = null;
        String pass = null;
        String sid = null;
        String url = null;
        if(db.equals("CB")){
            driver = EasyFile.getConfig("Configs", "oracle_driver");
            host = EasyFile.getConfig("Configs", EasyFile.getConfig("Configs", "oracle_host_cb"));
            port = EasyFile.getConfig("Configs", "oracle_port");
            user = EasyFile.getConfig("Configs", EasyFile.getConfig("Configs", "oracle_user_cb"));
            pass = EasyFile.getConfig("Configs", EasyFile.getConfig("Configs", "oracle_pass_cb"));
            sid = EasyFile.getConfig("Configs", EasyFile.getConfig("Configs", "oracle_sid_cb"));
        }else if(db.equals("CC1")){
            driver = EasyFile.getConfig("Configs", "oracle_driver");
            host = EasyFile.getConfig("Configs", EasyFile.getConfig("Configs", "oracle_host_cc1"));
            port = EasyFile.getConfig("Configs", "oracle_port");
            user = EasyFile.getConfig("Configs", EasyFile.getConfig("Configs", "oracle_user_cc1"));
            pass = EasyFile.getConfig("Configs", EasyFile.getConfig("Configs", "oracle_pass_cc1"));
            sid = EasyFile.getConfig("Configs", EasyFile.getConfig("Configs", "oracle_sid_cc1"));
        }
        url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pass);
            con.setAutoCommit(false);
        } catch (Exception ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initCallProc(String namePackage, String nameProcedure, int nbParam){
        String request = "{ call " + namePackage + "." + nameProcedure + "(";
        for(int i=0; i<nbParam; i++){
            request += (i<(nbParam-1) ? "?," : "?)}");
        }
        try {
            csmt = con.prepareCall(request);
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initCallFunc(String namePackage, String nameProcedure, int nbParam){
        String request = "{ ? = call " + namePackage + "." + nameProcedure + "(";
        for(int i=0; i<nbParam; i++){
            request += (i<(nbParam-1) ? "?," : "?)}");
        }
        try {
            csmt = con.prepareCall(request);
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String quotes(String message){
        String temp = message;
        temp = temp.replace("'", "''");
        return temp;
    }


    private void finish(String type) {
        try {
            this.smt = this.con.createStatement();
            this.smt.executeQuery(type);
            this.smt.close();
        } catch (Exception ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String[] getListCriteres(LinkedList<String> criteres) {
        String[] listCriteres = null;
        if(criteres.size() > 0){
            listCriteres = new String[criteres.size()];
            int cpt = 0;
            for(String critere : criteres){
                listCriteres[cpt] = critere;
                cpt++;
            }
        }
        return listCriteres;
    }

    public boolean doInsertCommande(int id, int quantite, int physique) {
        boolean ok = false;
        try {
            csmt.setInt(1, id);
            csmt.setInt(2, quantite);
            csmt.setInt(3, physique);
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            csmt.executeUpdate();
            finish("commit");
            ok = true;
        } catch (SQLException ex) {
            finish("rollback");
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                csmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ok;
    }

}

