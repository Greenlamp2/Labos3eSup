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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;


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
                nbreInscription = getNbresInscription(annee, activite);
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
        }else if(type.equals(HIDP.GET_GR_ACTIV_COMP)){
            String[] chaine = (String[]) contenu;
            String annee = chaine[0];
            String diagramme = chaine[1];
            Object[] resultat = new Object[2];
            int nbreInscriptionCra;
            int nbreInscriptionExt;
            int nbreInscriptionOrg;
            int nbreInscriptionTotal;
            nbreInscriptionCra = getNbreInscription(annee, "cracra-hontah");
            nbreInscriptionExt = getNbreInscription(annee, "extreme trek");
            nbreInscriptionOrg = getNbreInscription(annee, "orgiac island");

            if(diagramme.equals("Sectoriel")){
                resultat[0]=(String) "Sectoriel";
                nbreInscriptionTotal = nbreInscriptionCra + nbreInscriptionOrg + nbreInscriptionExt;
                double pourcentCra = (double)nbreInscriptionCra / nbreInscriptionTotal * 100;
                double pourcentExt = (double)nbreInscriptionExt / nbreInscriptionTotal * 100;
                double pourcentOrg = (double)nbreInscriptionOrg / nbreInscriptionTotal * 100;
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue("cracra-hontah", pourcentCra);
                dpd.setValue("extreme trek", pourcentExt);
                dpd.setValue("orgiac island", pourcentOrg);
                JFreeChart jfc = ChartFactory.createPieChart ("Nombre d'inscriptions aux activités", dpd, true, true, true);
                ChartPanel cp = new ChartPanel(jfc);
                resultat[1]= cp;
            }else{
                resultat[0]=(String) "Histogramme";
                DefaultCategoryDataset dpd = new DefaultCategoryDataset();
                dpd.addValue(nbreInscriptionCra, annee, "cracra-hontah");
                dpd.addValue(nbreInscriptionExt, annee, "extreme trek");
                dpd.addValue(nbreInscriptionOrg, annee, "orgiac island");
                JFreeChart jfc = ChartFactory.createBarChart("Nombre d'inscriptions aux activités", "Année", "Nombre d'inscription", dpd, PlotOrientation.VERTICAL, true, true, true);
                ChartPanel cp = new ChartPanel(jfc);
                resultat[1]= cp;
            }
            PacketCom packetRetour = new PacketCom(HIDP.GET_GR_ACTIV_COMP_OUI, resultat);
            return packetRetour;
        }else if(type.equals(HIDP.GET_GR_ACTIV_EVOL)){
            String[] infos = (String[]) contenu;
            Object resultat=null;
            String annee = infos[0];
            String activite = infos[1];
            int[] nbreInscription = new int[12];

            nbreInscription = getNbresInscription(annee, activite);
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            String[] colonne = {"1 Mai", "15 Mai", "1 Juin", "15 Juin", "1 Juil", "15 juil", "1 Aout", "15 Aout", "1 Sept", "15 Sept", "1 Oct", "15 Oct"};
            for(int i=0; i<12; i++){
                dcd.addValue((double)nbreInscription[i], annee, colonne[i]);
            }
            JFreeChart jfc = ChartFactory.createLineChart("Nombre d'inscriptions aux activités", "Quinzaine", "Nombre d'inscription", dcd, PlotOrientation.VERTICAL, true, true, true);
            ChartPanel cp = new ChartPanel(jfc);
            resultat = cp;

            PacketCom packetRetour = new PacketCom(HIDP.GET_GR_ACTIV_EVOL_OUI, resultat);
            return packetRetour;
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
        }else if (type.equals(HIDP.GET_GR_ACTIV_COMP_OUI)) {
            return packet;
        }else if (type.equals(HIDP.GET_GR_ACTIV_EVOL_OUI)) {
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

    private int[] getNbresInscription(String annee, String activite) {
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

    private int getNbreInscription(String annee, String type) {
        int retour = 0;

        String request = null;
        request ="SELECT count(*) as cpt FROM `reservation_activites` WHERE type ='"+type+"' and date(date) >= '"+annee+"-05-01' and date(date) < '"+annee+"-11-01'";

        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            Object tuples = dbsql.select(request);
            String nb = dbsql.extract(tuples, 1, "cpt");
            if(nb != null){
                retour = Integer.parseInt(nb);
            }else{
                retour = 0;
            }
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(HIDP.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retour;
    }

}
