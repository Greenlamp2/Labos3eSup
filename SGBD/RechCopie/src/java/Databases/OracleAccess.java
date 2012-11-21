/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Databases;

import Beans.BeanDetails;
import Beans.BeanMovie;
import Beans.BeanResultat;
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

    public BeanResultat doGetListMovies(String requete) {
        BeanResultat beanResultat = null;
        try {
            csmt.registerOutParameter(1, Types.ARRAY, "MOVIES_T");
            csmt.setString(2, requete);
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        Array retour = null;

        try {
            csmt.executeUpdate();
            finish("commit");
            retour = csmt.getArray(1);
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
        try {
            ResultSet rs = retour.getResultSet();
            if(rs != null){
                beanResultat = new BeanResultat();
            }
            while(rs.next()){
                BeanMovie beanMovie = new BeanMovie();
                int id = rs.getInt(1);
                beanMovie.setId(id);
                oracle.sql.STRUCT value = (oracle.sql.STRUCT)rs.getObject(2);
                Object[] attributes = value.getAttributes();
                int idMovie = ((java.math.BigDecimal)attributes[0]).intValue();
                String name = attributes[1].toString();
                Blob affiche = (Blob)attributes[2];
                int nbCopieDispo = ((java.math.BigDecimal)attributes[3]).intValue();
                int popularite = 0;
                if(attributes[4] != null){
                    popularite = ((java.math.BigDecimal)attributes[4]).intValue();
                }
                int frequenceProg = 0;
                if(attributes[5] != null){
                    frequenceProg = ((java.math.BigDecimal)attributes[5]).intValue();
                }
                int perenite = 0;
                if(attributes[6] != null){
                    perenite = ((java.math.BigDecimal)attributes[6]).intValue();
                }
                beanMovie.setNom(name);
                beanMovie.setAffiche(affiche);
                beanMovie.setId(idMovie);
                beanMovie.setPopularite(popularite);
                beanMovie.setNbCopieDispo(nbCopieDispo);
                beanMovie.setFrequenceProg(frequenceProg);
                beanMovie.setPerenite(perenite);
                beanResultat.add(beanMovie);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return beanResultat;
    }

    public BeanDetails doGetDetails(int idMovie) {
        BeanDetails beanDetails = null;
        try {
            csmt.registerOutParameter(1, Types.ARRAY, "MOVIES_T_DETAILS");
            csmt.setInt(2, idMovie);
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        Array retour = null;

        try {
            csmt.executeUpdate();
            finish("commit");
            retour = csmt.getArray(1);
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
        try {
            ResultSet rs = retour.getResultSet();
            if(rs != null){
                beanDetails = new BeanDetails();
            }
            while(rs.next()){
                int id = rs.getInt(1);
                oracle.sql.STRUCT value = (oracle.sql.STRUCT)rs.getObject(2);
                Object[] attributes = value.getAttributes();
                int v_idMovie = ((java.math.BigDecimal)attributes[0]).intValue();
                java.sql.Array listActors = (java.sql.Array) attributes[1];
                ResultSet resultSetActors = listActors.getResultSet();
                LinkedList<String> listeActeurs = new LinkedList<String>();
                while(resultSetActors.next()){
                    String valueResult = resultSetActors.getString(2);
                    listeActeurs.add(valueResult);
                }
                java.sql.Array listDirectors = (java.sql.Array) attributes[2];
                ResultSet resultSetDirectors = listDirectors.getResultSet();
                LinkedList<String> listeDirecteurs = new LinkedList<String>();
                while(resultSetDirectors.next()){
                    String valueResult = resultSetDirectors.getString(2);
                    listeDirecteurs.add(valueResult);
                }
                java.util.Date released = new java.util.Date(((java.sql.Timestamp) attributes[3]).getTime());
                System.out.println("test");
                beanDetails.setId(idMovie);
                beanDetails.setListActors(listeActeurs);
                beanDetails.setListDirectors(listeDirecteurs);
                beanDetails.setReleased(released);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }


        return beanDetails;
    }

    public int doGetCount(String requeteCount) {
        int nbResultat = 0;
        try {
            csmt.registerOutParameter(1, Types.INTEGER);
            csmt.setString(2, requeteCount);
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            csmt.executeUpdate();
            finish("commit");
            nbResultat = csmt.getInt(1);
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

        return nbResultat;
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

