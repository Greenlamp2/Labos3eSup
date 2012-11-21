/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import Beans.BeanCmdTicket;
import Beans.BeanMovie;
import Beans.BeanProjections;
import Helpers.EasyFile;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.sql.*;


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

    public BeanProjections doGetAllProjection(String qte){
        BeanProjections projections = null;
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
                projections = new BeanProjections();
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

    public BeanMovie doGetDetail(int idMovie) {
        BeanMovie beanMovie = null;
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
                beanMovie = new BeanMovie();
            }
            while(rs.next()){
                STRUCT value = (STRUCT)rs.getObject(2);

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
                LinkedList<String> listeActeurs = new LinkedList<String>();
                while(resultSetActors.next()){
                    String valueResult = resultSetActors.getString(2);
                    listeActeurs.add(valueResult);
                }

                java.sql.Array listDirectors = (java.sql.Array) attributes[11];
                ResultSet resultSetDirectors = listDirectors.getResultSet();
                LinkedList<String> listeRealisateurs = new LinkedList<String>();
                while(resultSetDirectors.next()){
                    String valueResult = resultSetDirectors.getString(2);
                    listeRealisateurs.add(valueResult);
                }

                java.sql.Array listStudios = (java.sql.Array) attributes[12];
                ResultSet resultSetStudios = listStudios.getResultSet();
                LinkedList<String> listeStudios = new LinkedList<String>();
                while(resultSetStudios.next()){
                    String valueResult = resultSetStudios.getString(2);
                    listeStudios.add(valueResult);
                }

                java.sql.Array listLangues = (java.sql.Array) attributes[13];
                ResultSet resultSetLangues = listLangues.getResultSet();
                LinkedList<String> listeLangues = new LinkedList<String>();
                while(resultSetLangues.next()){
                    String valueResult = resultSetLangues.getString(2);
                    listeLangues.add(valueResult);
                }

                java.sql.Array listGenres = (java.sql.Array) attributes[14];
                ResultSet resultSetGenres = listGenres.getResultSet();
                LinkedList<String> listeGenres = new LinkedList<String>();
                while(resultSetGenres.next()){
                    String valueResult = resultSetGenres.getString(2);
                    listeGenres.add(valueResult);
                }

                java.sql.Array listCertifications = (java.sql.Array) attributes[15];
                ResultSet resultSetCertifications = listCertifications.getResultSet();
                LinkedList<String> listeCertifications = new LinkedList<String>();
                while(resultSetCertifications.next()){
                    String valueResult = resultSetCertifications.getString(2);
                    listeCertifications.add(valueResult);
                }

                java.sql.Array listCopies = (java.sql.Array) attributes[16];
                ResultSet resultSetCopies = listCopies.getResultSet();
                LinkedList<Integer> listeCopies = new LinkedList<Integer>();
                while(resultSetCopies.next()){
                    int valueResult = Integer.parseInt(resultSetCopies.getString(2));
                    listeCopies.add(valueResult);
                }

                java.sql.Array listProjections = (java.sql.Array) attributes[17];
                ResultSet resultSetProjections = listProjections.getResultSet();
                LinkedList<String> listeProjections = new LinkedList<String>();
                while(resultSetProjections.next()){
                    String valueResult = resultSetProjections.getString(2);
                    listeProjections.add(valueResult);
                }

                int runtime = ((java.math.BigDecimal)attributes[18]).intValue();

                beanMovie.setActeurs(listeActeurs);
                beanMovie.setAffiche(affiche);
                beanMovie.setCertifications(listeCertifications);
                beanMovie.setCopies(listeCopies);
                beanMovie.setDateSortie(released);
                beanMovie.setGenres(listeGenres);
                beanMovie.setIdMovie(idMovie);
                beanMovie.setImdbId(imdbid);
                beanMovie.setLangues(listeLangues);
                beanMovie.setName(name);
                beanMovie.setProjections(listeProjections);
                beanMovie.setRating(rating);
                beanMovie.setRealisateurs(listeRealisateurs);
                beanMovie.setResume(overview);
                beanMovie.setStudios(listeStudios);
                if(translated == 1){
                    beanMovie.setTraduit(true);
                }else{
                    beanMovie.setTraduit(false);
                }
                beanMovie.setTrailer(trailer);
                beanMovie.setVotes(votes);
                beanMovie.setRuntime(runtime);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return beanMovie;
    }

    public BeanCmdTicket doGetCmdDetails(String dateProjection, int idMovie) {
        BeanCmdTicket beanCmdTicket = null;
        try {
            csmt.registerOutParameter(1, Types.ARRAY, "CMD_T");
            csmt.setString(2, dateProjection);
            csmt.setInt(3, idMovie);
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
                beanCmdTicket = new BeanCmdTicket();
            }
            while(rs.next()){
                STRUCT value = (STRUCT)rs.getObject(2);
                Object[] attributes = value.getAttributes();
                int idCopie = ((java.math.BigDecimal)attributes[0]).intValue();
                int numeroSalle = ((java.math.BigDecimal)attributes[1]).intValue();
                int nbPlacesDispo = ((java.math.BigDecimal)attributes[2]).intValue();
                beanCmdTicket.setIdCopie(idCopie);
                beanCmdTicket.setNbPlacesDispo(nbPlacesDispo);
                beanCmdTicket.setNumeroSalle(numeroSalle);
                beanCmdTicket.setIdMovie(idMovie);
                java.util.Date date = null;
                try {
                    date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dateProjection);
                } catch (ParseException ex) {
                    Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
                }
                String test = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
                beanCmdTicket.setDate(date);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return beanCmdTicket;
    }

    public String doAcheterTicket(String date, int numeroSalle, int nbTicket) {
        String messageErreur = null;
        try {
            csmt.setString(1, date);
            csmt.setInt(2, numeroSalle);
            csmt.setInt(3, nbTicket);
        } catch (SQLException ex) {
            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            csmt.executeUpdate();
            finish("commit");
        } catch (SQLException ex) {
            finish("rollback");
            if(ex.getErrorCode() == 20006){
                messageErreur = "Il n'y a pas assez de places dans la salle.";
            }else if(ex.getErrorCode() == 20005){
                messageErreur = "La salle est complète.";
            }else{
                messageErreur = ex.getMessage();
            }

            Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                csmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(OracleAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return messageErreur;
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

}

