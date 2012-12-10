/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocoleHIDP;

import Bean.Jdbc_MySQL;
import java.beans.Beans;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math.stat.descriptive.moment.StandardDeviation;


public class HIDP {
    public static String LOGIN = "LOGIN";
    public static String LOGIN_OUI = "LOGIN_OUI";
    public static String LOGIN_NON = "LOGIN_NON";
    public static String GET_STAT_DESCR_ACTIV = "GET_STAT_DESCR_ACTIV";
    public static String GET_STAT_DESCR_ACTIV_OUI = "GET_STAT_DESCR_ACTIV_OUI";
    public static String GET_GR_ACTIV_COMP = "GET_GR_ACTIV_COMP";
    public static String GET_GR_ACTIV_COMP_OUI = "GET_GR_ACTIV_COMP_OUI";
    public static String GET_GR_ACTIV_EVOL = "GET_GR_ACTIV_EVOL";
    public static String GET_GR_ACTIV_EVOL_OUI = "GET_GR_ACTIV_EVOL_OUI";
    public static String GET_STAT_INFER_ACTIV = "GET_STAT_INFER_ACTIV";
    public static String GET_STAT_INFER_ACTIV_OUI = "GET_STAT_INFER_ACTIV_OUI";
    public static String GET_GR_2D_DEPENSES = "GET_GR_2D_DEPENSES";
    public static String GET_GR_2D_DEPENSES_OUI = "GET_GR_2D_DEPENSES_OUI";
    public static String TEST_COMP = "TEST_COMP";
    public static String TEST_COMP_OUI = "TEST_COMP_OUI";
    public static String ERROR = "ERROR";

    PacketCom messageFromClient(Object objet) {
        PacketCom packet = (PacketCom) objet;
        PacketCom messageToClient = traiterPacketServerSide(packet);
        return messageToClient;
    }

    PacketCom messageFromServer(Object objet) {
        PacketCom packet = (PacketCom) objet;
        PacketCom message = traiterPacketClientSide(packet);
        return message;
    }

    private PacketCom traiterPacketServerSide(PacketCom packet) {
        String type = packet.getType();
        System.out.println("type reçu: " + type);
        Object contenu = packet.getObjet();
        System.out.println("Reçu: " + type);
        if (type.equals(HIDP.LOGIN)) {
            String[] infos = (String[]) contenu;
            String login = infos[0];
            String password = infos[1];
            if (gestionLogin(login, password)) {
                PacketCom packetRetour = new PacketCom(HIDP.LOGIN_OUI, "LOGIN_OUI");
                return packetRetour;
            }else{
                return new PacketCom(HIDP.LOGIN_NON, "LOGIN_NON");
            }
        }else if(type.equals(HIDP.GET_STAT_DESCR_ACTIV)){
            int[] nbreInscription = new int[12];
            String[] chaine = (String[]) contenu;
            String activite = chaine[0];
            String annee = chaine[1];
            double moyenne;
            double ecartType;

            if(activiteExiste(activite, annee)){
                Object[] infos = new Object[15];
                nbreInscription = getNbreInscription(annee, activite);
                infos[0] = (String) "Oui";
                for(int i=0; i<12; i++){
                    infos[i+1] = nbreInscription[i];
                }
                moyenne = getMoyenne(nbreInscription);
                infos[13] = moyenne;
                ecartType = getEcartType(nbreInscription);
                infos[14] = ecartType;
                PacketCom packetRetour = new PacketCom(HIDP.GET_STAT_DESCR_ACTIV_OUI, infos);
                return packetRetour;
            }
            return new PacketCom(HIDP.ERROR, "ERROR");
        }else{
            return new PacketCom(HIDP.ERROR, "ERROR");
        }
    }

    private PacketCom traiterPacketClientSide(PacketCom packet) {
        String type = packet.getType();
        Object contenu = packet.getObjet();
        System.out.println("Reçu: " + type);
        if (type.equals(HIDP.LOGIN_OUI)) {
            return packet;
        }else if (type.equals(HIDP.LOGIN_NON)) {
            return packet;
        }else if (type.equals(HIDP.GET_STAT_DESCR_ACTIV_OUI)) {
            return packet;
        }else {
            PacketCom packetReponse = new PacketCom(HIDP.ERROR, "ERROR");
            return packetReponse;
        }
    }

    private boolean gestionLogin(String login, String password) {
        String loginFound = null;
        boolean found = false;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT user FROM adminactivite where user = '" + login + "' AND pass = '" + password + "'";
            Object tuples = dbsql.select(request);
            loginFound = dbsql.extract(tuples, 1, "user");
            if(loginFound != null){
                found = true;
            }
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(HIDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return found;
    }

    private boolean activiteExiste(String activite, String annee) {
        boolean found = false;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request ="select count(*) as cpt from activites where type='"+activite+"' AND date(date) >= '"+ annee +"-05-01' and date(date) < '"+ annee +"-11-01'";
            Object tuples = dbsql.select(request);
            String activiteFound = dbsql.extract(tuples, 1, "cpt");
            if(activiteFound != null){
                found = true;
            }
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(HIDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return found;
    }

    private int[] getNbreInscription(String annee, String activite) {
        boolean found = false;
        int[] nbreInscription = new int[12];
        int mois = 0;
        int mois2 = 0;
        String request = null;
        for(int i=0; i<12; i++){
            mois = 5+(i/2);
            mois2 = mois + 1;
            if(mois%2 == 1){
                request ="SELECT count(*) as cpt FROM `reservation_activites` WHERE type ='"+activite+"' and date(date) >= '"+annee+"-"+ mois +"-15' and date(date) < '"+annee+"-"+ mois2 + "-01'";
            }else{
                request ="SELECT count(*) as cpt FROM `reservation_activites` WHERE type ='"+activite+"' and date(date) >= '"+annee+"-"+ mois +"-01' and date(date) < '"+annee+"-"+ mois +"-15'";
            }
            try {
                Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
                dbsql.init();
                Object tuples = dbsql.select(request);
                String nb = dbsql.extract(tuples, 1, "cpt");
                if(nb != null){
                    nbreInscription[i] = Integer.parseInt(nb);
                }else{
                    nbreInscription[i] = 0;
                }
                dbsql.endExtract();
                dbsql.Disconnect();
            } catch (Exception ex) {
                Logger.getLogger(HIDP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return nbreInscription;
    }

    private double getMoyenne(int[] nbreInscription) {
        double resultat = 0;
        for(int nbre : nbreInscription){
            resultat += nbre;
        }

        resultat /= nbreInscription.length;
        return resultat;
    }

    private double getEcartType(int[] nbreInscription) {
        double resultat = 0;
        int cpt = 0;
        double[] liste = new double[12];
        for(int nbre : nbreInscription){
            liste[cpt] = (double)nbre;
            cpt++;
        }

        StandardDeviation sd = new StandardDeviation();
        resultat = sd.evaluate(liste);
        return resultat;
    }

}
