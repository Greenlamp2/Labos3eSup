/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import Data.Detail;
import Data.Dispos;
import Data.Projections;
import Helpers.EasyFile;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.org.mozilla.javascript.internal.JavaAdapter;


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

    public void disconnect(){
        try {
            con.close();
        } catch (SQLException ex) {
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

    public Projections doGetAllProjection(String qte){
        Projections projections = null;
        try {
            csmt.registerOutParameter(1, Types.ARRAY, "PROJECTION_T");
            csmt.setString(2, qte);
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
                projections = new Projections();
            }
            while(rs.next()){
                oracle.sql.STRUCT value = (oracle.sql.STRUCT)rs.getObject(2);
                Object[] attributes = value.getAttributes();
                java.util.Date dateHeureProjection = new java.util.Date(((java.sql.Timestamp) attributes[0]).getTime());
                int numeroSalle = ((java.math.BigDecimal)attributes[1]).intValue();
                int idCopie = ((java.math.BigDecimal)attributes[2]).intValue();
                int idMovie = ((java.math.BigDecimal)attributes[3]).intValue();
                String nameMovie = attributes[4].toString();
                int duree = ((java.math.BigDecimal)attributes[5]).intValue();
                projections.addProjection(dateHeureProjection, numeroSalle, idCopie, idMovie, nameMovie, duree);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return projections;
    }

    public Detail doGetDetail(int idMovie) {
        Detail detail = null;
        try {
            csmt.registerOutParameter(1, Types.ARRAY, "DETAIL_T");
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
                detail = new Detail();
            }
            while(rs.next()){
                oracle.sql.STRUCT value = (oracle.sql.STRUCT)rs.getObject(2);

                Object[] attributes = value.getAttributes();

                String imdbid = null;
                if(attributes[1] != null){
                    imdbid = attributes[1].toString();
                }

                String name = null;
                if(attributes[2] != null){
                    name = attributes[2].toString();
                }

                String overview = null;
                if(attributes[3] != null){
                    overview = attributes[3].toString();
                }

                float rating = ((java.math.BigDecimal)attributes[4]).floatValue();

                java.util.Date released = new java.util.Date(((java.sql.Timestamp) attributes[5]).getTime());

                String trailer = null;
                if(attributes[6] != null){
                    trailer = attributes[6].toString();
                }

                int translated = ((java.math.BigDecimal)attributes[7]).intValue();

                int votes = ((java.math.BigDecimal)attributes[8]).intValue();

                Blob affiche = (Blob)attributes[9];

                java.sql.Array listActors = (java.sql.Array) attributes[10];
                ResultSet resultSetActors = listActors.getResultSet();
                LinkedList<String> listeActeurs = new LinkedList<>();
                while(resultSetActors.next()){
                    String valueResult = resultSetActors.getString(2);
                    listeActeurs.add(valueResult);
                }

                java.sql.Array listDirectors = (java.sql.Array) attributes[11];
                ResultSet resultSetDirectors = listDirectors.getResultSet();
                LinkedList<String> listeRealisateurs = new LinkedList<>();
                while(resultSetDirectors.next()){
                    String valueResult = resultSetDirectors.getString(2);
                    listeRealisateurs.add(valueResult);
                }

                java.sql.Array listStudios = (java.sql.Array) attributes[12];
                ResultSet resultSetStudios = listStudios.getResultSet();
                LinkedList<String> listeStudios = new LinkedList<>();
                while(resultSetStudios.next()){
                    String valueResult = resultSetStudios.getString(2);
                    listeStudios.add(valueResult);
                }

                java.sql.Array listLangues = (java.sql.Array) attributes[13];
                ResultSet resultSetLangues = listLangues.getResultSet();
                LinkedList<String> listeLangues = new LinkedList<>();
                while(resultSetLangues.next()){
                    String valueResult = resultSetLangues.getString(2);
                    listeLangues.add(valueResult);
                }

                java.sql.Array listGenres = (java.sql.Array) attributes[14];
                ResultSet resultSetGenres = listGenres.getResultSet();
                LinkedList<String> listeGenres = new LinkedList<>();
                while(resultSetGenres.next()){
                    String valueResult = resultSetGenres.getString(2);
                    listeGenres.add(valueResult);
                }

                java.sql.Array listCertifications = (java.sql.Array) attributes[15];
                ResultSet resultSetCertifications = listCertifications.getResultSet();
                LinkedList<String> listeCertifications = new LinkedList<>();
                while(resultSetCertifications.next()){
                    String valueResult = resultSetCertifications.getString(2);
                    listeCertifications.add(valueResult);
                }

                java.sql.Array listCopies = (java.sql.Array) attributes[16];
                ResultSet resultSetCopies = listCopies.getResultSet();
                LinkedList<Integer> listeCopies = new LinkedList<>();
                while(resultSetCopies.next()){
                    int valueResult = Integer.parseInt(resultSetCopies.getString(2));
                    listeCopies.add(valueResult);
                }

                java.sql.Array listProjections = (java.sql.Array) attributes[17];
                ResultSet resultSetProjections = listProjections.getResultSet();
                LinkedList<String> listeProjections = new LinkedList<>();
                while(resultSetProjections.next()){
                    String valueResult = resultSetProjections.getString(2);
                    listeProjections.add(valueResult);
                }

                int runtime = ((java.math.BigDecimal)attributes[18]).intValue();

                detail.setActeurs(listeActeurs);
                detail.setAffiche(affiche);
                detail.setCertifications(listeCertifications);
                detail.setCopies(listeCopies);
                detail.setDateSortie(released);
                detail.setGenres(listeGenres);
                detail.setIdMovie(idMovie);
                detail.setImdbId(imdbid);
                detail.setLangues(listeLangues);
                detail.setName(name);
                detail.setProjections(listeProjections);
                detail.setRating(rating);
                detail.setRealisateurs(listeRealisateurs);
                detail.setResume(overview);
                detail.setStudios(listeStudios);
                if(translated == 1){
                    detail.setTraduit(true);
                }else{
                    detail.setTraduit(false);
                }
                detail.setTrailer(trailer);
                detail.setVotes(votes);
                detail.setRuntime(runtime);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detail;
    }

    public Dispos doGetDispos(String type) {
        Dispos dispos = null;
        try {
            csmt.registerOutParameter(1, Types.ARRAY, "DISPOS_T");
            csmt.setString(2, type);
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
                dispos = new Dispos();
            }
            while(rs.next()){
                oracle.sql.STRUCT value = (oracle.sql.STRUCT)rs.getObject(2);
                Object[] attributes = value.getAttributes();
                int idMovie = ((java.math.BigDecimal)attributes[0]).intValue();
                String name = attributes[1].toString();

                java.sql.Array listCopies = (java.sql.Array) attributes[2];
                ResultSet resultSetCopies = listCopies.getResultSet();
                LinkedList<Integer> listeCopies = new LinkedList<>();
                while(resultSetCopies.next()){
                    int valueResult = Integer.parseInt(resultSetCopies.getString(2));
                    listeCopies.add(valueResult);
                }
                if(dispos.getSallesDispo().size() == 0){
                    java.sql.Array listSalles = (java.sql.Array) attributes[3];
                    ResultSet resultSetSalles = listSalles.getResultSet();
                    LinkedList<Integer> listeSalles = new LinkedList<>();
                    while(resultSetSalles.next()){
                        int valueResult = Integer.parseInt(resultSetSalles.getString(2));
                        listeSalles.add(valueResult);
                    }
                    dispos.setSallesDispo(listeSalles);
                }
                int duree = ((java.math.BigDecimal)attributes[4]).intValue();
                dispos.addDispo(idMovie, name, listeCopies, duree);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dispos;
    }

    public String doVerifProjection(int idMovie, int idCopie, java.util.Date date, int numeroSalle) {
        String messageRetour = null;
        try {
            csmt.registerOutParameter(1, Types.INTEGER);
            csmt.setInt(2, idMovie);
            csmt.setInt(3, idCopie);
            csmt.setString(4, new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date));
            csmt.setInt(5, numeroSalle);
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        int retour = 0;
        try {
            csmt.executeUpdate();
            finish("commit");
            retour = csmt.getInt(1);
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
        if(retour == -1){
            messageRetour = "La copie est déja utilisée par une salle à cette heure là.";
        }else if(retour == -2){
            messageRetour = "La salle est déja en train de jouer une copie à cette heure là.";
        }else if(retour == 1){
            messageRetour = "OK";
        }
        return messageRetour;
    }

    public boolean doInsertProjection(int idCopie, java.util.Date date, int salle) {
        boolean correct = false;
        String dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
        try {
            csmt.setString(1, dateFormat);
            csmt.setInt(2, salle);
            csmt.setInt(3, idCopie);
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            csmt.executeUpdate();
            finish("commit");
            correct = true;
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
        return correct;
    }

    public boolean doProlongerProjection(String date, int salle, int idCopie, int duree) {
        boolean correct = false;
        try {
            csmt.setString(1, date);
            csmt.setInt(2, salle);
            csmt.setInt(3, idCopie);
            csmt.setInt(4, duree);
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            csmt.executeUpdate();
            finish("commit");
            correct = true;
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
        return correct;
    }

    /*
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
    }*/

}

