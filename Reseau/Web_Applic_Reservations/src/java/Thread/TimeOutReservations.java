/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Thread;

import Bean.Jdbc_MySQL;
import Servlet.Servlet_Login;
import java.beans.Beans;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TimeOutReservations implements Runnable{

    @Override
    public void run() {
        System.out.println("Thread timeOutStarted");
        while(true){
            try {
                Thread.sleep(1000 * 5); // 5 secondes
            } catch (InterruptedException ex) {
                Logger.getLogger(TimeOutReservations.class.getName()).log(Level.SEVERE, null, ex);
            }
            gestionTimeOut();
        }
    }

    private void gestionTimeOut() {
        LinkedList<String> listeReservation = getListeReservationExpiree("min", 1); // plus de 20 minutes.
        for(String idReservation : listeReservation){
            supprimerReservation(idReservation);
        }
    }

    private LinkedList<String> getListeReservationExpiree(String type, int duree) {
        LinkedList<String> listeReservation = null;
        Jdbc_MySQL dbsql;
        try {
            dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String requete = "SELECT * FROM reservations WHERE payé = '0'";
            ResultSet tuples = (ResultSet)dbsql.select(requete);
            while(tuples.next()){
                String idReservation = tuples.getString("id");
                String date = tuples.getString("date_reservation");
                String heure = tuples.getString("heure_reservation");
                String dateStringNow = getDateNow();
                String heureStringNow = getHeureNow();
                heure = ajoutTemp(heure, type, duree);
                Date dateNow = convertDate(dateStringNow, heureStringNow);
                Date dateReservation = convertDate(date, heure);
                int retour = dateNow.compareTo(dateReservation);
                if(retour == 1){
                    if(listeReservation == null){
                        listeReservation = new LinkedList<String>();
                    }
                    listeReservation.add(idReservation);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TimeOutReservations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listeReservation;
    }

    private void supprimerReservation(String idReservation) {
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "DELETE FROM reservations WHERE id = '"+idReservation+"'";
            dbsql.update(request);
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getDateNow() {
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(now);
        return date;
    }

    private String getHeureNow() {
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String heure = dateFormat.format(now);
        return heure;
    }

    private Date convertDate(String date, String heure) {
        String dateComplete = date + "-" + heure;
        String format = "dd-MM-yyyy-HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = null;
        String test = null;
        try {
            d = (Date)sdf.parse(dateComplete);
            test = sdf.format(d);
        } catch (ParseException ex) {
            Logger.getLogger(TimeOutReservations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }

    private String ajoutTemp(String heure, String type, int duree) {
        String[] split = heure.split(":");
        String h = split[0];
        String m = split[1];
        if(type.equals("min")){
            int minute = Integer.parseInt(m);
            minute += duree;
            return h + ":" + String.valueOf(minute);
        }else{
            int heu = Integer.parseInt(h);
            heu += duree;
            return String.valueOf(heu) + ":" + m;
        }
    }

}
