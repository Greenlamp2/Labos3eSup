/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole;

import Bean.Jdbc_MySQL;
import Mails.Messages;
import Mails.Middle;
import java.beans.Beans;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FOAMP {
    public static String LOGIN = "LOGIN";
    public static String LOGIN_OUI = "LOGIN_OUI";
    public static String LOGIN_NON = "LOGIN_NON";
    public static String BACTFUN = "BACTFUN";
    public static String BACTFUN_OUI = "BACTFUN_OUI";
    public static String BACTFUN_NON = "BACTFUN_NON";
    public static String ACKACTFUN = "ACKACTFUN";
    public static String ACKACTFUN_OUI = "ACKACTFUN_OUI";
    public static String ACKACTFUN_NON = "ACKACTFUN_NON";
    public static String BTREKFUN = "BTREKFUN";
    public static String BTREKFUN_OUI = "BTREKFUN_OUI";
    public static String BTREKFUN_NON = "BTREKFUN_NON";
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
        if (type.equals(FOAMP.LOGIN)) {
            String[] infos = (String[]) contenu;
            String login = infos[0];
            String password = infos[1];
            if (gestionLogin(login, password)) {
                PacketCom packetRetour = new PacketCom(FOAMP.LOGIN_OUI, "LOGIN_OUI");
                return packetRetour;
            }else{
                return new PacketCom(FOAMP.LOGIN_NON, "LOGIN_NON");
            }
        } else if (type.equals(FOAMP.BACTFUN)) {
            String[] chaine = (String[])contenu;
            String activite = chaine[0];
            String date = chaine[1];
            String client = chaine[2];
            PacketCom packetRetour = actionBactFun(activite, date, client);
            return packetRetour;
        } else if (type.equals(FOAMP.ACKACTFUN)) {
            String[] chaine = (String[])contenu;
            String activite = chaine[0];
            String date = chaine[1];
            String client = chaine[2];
            PacketCom packetRetour = actionAckActFun(activite, date, client);
            return packetRetour;
        } else if (type.equals(FOAMP.BTREKFUN)) {
            String[] chaine = (String[])contenu;
            String activite = chaine[0];
            String session = chaine[1];
            String client = chaine[2];
            PacketCom packetRetour = actionBtrekFun(activite, session, client);
            return packetRetour;
        }else{
            return new PacketCom(FOAMP.ERROR, "ERROR");
        }
    }

    private PacketCom traiterPacketClientSide(PacketCom packet) {
        String type = packet.getType();
        Object contenu = packet.getObjet();
        System.out.println("Reçu: " + type);
        if (type.equals(FOAMP.LOGIN_OUI)) {
            return packet;
        }else if (type.equals(FOAMP.LOGIN_NON)) {
            return packet;
        }else if (type.equals(FOAMP.BACTFUN_OUI)) {
            return packet;
        }else if (type.equals(FOAMP.BACTFUN_NON)) {
            return packet;
        }else if (type.equals(FOAMP.ACKACTFUN_OUI)) {
            return packet;
        }else if (type.equals(FOAMP.ACKACTFUN_NON)) {
            return packet;
        }else if (type.equals(FOAMP.BTREKFUN_OUI)) {
            return packet;
        }else if (type.equals(FOAMP.BTREKFUN_NON)) {
            return packet;
        }else {
            PacketCom packetReponse = new PacketCom(FOAMP.ERROR, "ERROR");
            return packetReponse;
        }
    }

    private boolean gestionLogin(String login, String password) {
        String loginFound = null;
        boolean found = false;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT login FROM gestionnaires where login = '" + login + "' AND password = '" + password + "'";
            Object tuples = dbsql.select(request);
            loginFound = dbsql.extract(tuples, 1, "login");
            if(loginFound != null){
                found = true;
            }
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(FOAMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return found;
    }

    private PacketCom actionBactFun(String activite, String date, String client) {
        int idActivite = getIdActivite(activite);
        if(idActivite == -1){
            return new PacketCom(FOAMP.BACTFUN_NON, "Activité inconnue");
        }
        String[] split = client.split(" ");
        String nom = split[0];
        String prenom = split[1];
        int idClient = getIdClient(nom, prenom);
        if(idClient == -1){
            return new PacketCom(FOAMP.BACTFUN_NON, "Client inconnu");
        }
        int prix = getPrix(idActivite);
        if(prix == -1){
            return new PacketCom(FOAMP.BACTFUN_NON, "Activité sans prix");
        }
        boolean ok = insertInscription(idActivite, idClient, date);
        if(ok){
            String[] chaine = {date, String.valueOf(prix)};
            return new PacketCom(FOAMP.BACTFUN_OUI, (Object)chaine);
        }else{
            return new PacketCom(FOAMP.BACTFUN_NON, "Inscription déja présente à cette date pour ce client pour cette activitée");
        }
    }

    private PacketCom actionAckActFun(String activite, String date, String client) {
        int idActivite = getIdActivite(activite);
        if(idActivite == -1){
            return new PacketCom(FOAMP.ACKACTFUN_NON, "Activité inconnue");
        }
        String[] split = client.split(" ");
        String nom = split[0];
        String prenom = split[1];
        int idClient = getIdClient(nom, prenom);
        if(idClient == -1){
            return new PacketCom(FOAMP.ACKACTFUN_NON, "Client inconnu");
        }
        int numInscription = getNumInscription(idActivite, idClient, date);
        if(numInscription == -1){
            return new PacketCom(FOAMP.ACKACTFUN_NON, "Aucunes inscriptions à cette date pour ce client pour cette activitée");
        }
        boolean ok = validerInscription(idActivite, idClient, date);
        if(ok){
            Messages msg = new Messages();
            String mail = getMail(nom, prenom);
            String sujet = "Recapitulatif";
            msg.setSujet(sujet);
            msg.setTo(mail);
            String messageTexte = "nom: " + nom + "\nprenom: " + prenom + "\nnumero inscription: " + numInscription;
            msg.setMessage(messageTexte);
            Middle.sendMessage(msg);
            return new PacketCom(FOAMP.ACKACTFUN_OUI, (Object)numInscription);
        }else{
            return new PacketCom(FOAMP.ACKACTFUN_NON, "Aucunes inscriptions à cette date pour ce client pour cette activitée");
        }
    }

    private PacketCom actionBtrekFun(String activite, String session, String client) {
        int idActivite = getIdActivite(activite);
        if(idActivite == -1){
            return new PacketCom(FOAMP.ACKACTFUN_NON, "Activité inconnue");
        }
        String[] split = client.split(" ");
        String nom = split[0];
        String prenom = split[1];
        int idClient = getIdClient(nom, prenom);
        if(idClient == -1){
            return new PacketCom(FOAMP.ACKACTFUN_NON, "Client inconnu");
        }
        String date = generateDate(session);
        boolean ok = insertInscription(idActivite, idClient, date);
        if(ok){
            int numInscription = getNumInscription(idActivite, idClient, date);
            if(numInscription == -1){
                return new PacketCom(FOAMP.ACKACTFUN_NON, "Aucunes inscriptions à cette date pour ce client pour cette activitée");
            }
            return new PacketCom(FOAMP.ACKACTFUN_OUI, (Object)numInscription);
        }else{
            return new PacketCom(FOAMP.ACKACTFUN_NON, "Aucunes inscriptions à cette date pour ce client pour cette activitée");
        }
    }

    private int getIdActivite(String activite) {
        int idActivite = -1;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT id FROM activites where type = '" + activite + "'";
            Object tuples = dbsql.select(request);
            String resultat = null;
            resultat = dbsql.extract(tuples, 1, "id");
            if(resultat != null){
                idActivite = Integer.parseInt(resultat);
            }
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(FOAMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idActivite;
    }

    private int getIdClient(String nom, String prenom) {
        int idClient = -1;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT id FROM voyageurs where nom = '" + nom + "' AND prenom = '" + prenom + "'";
            Object tuples = dbsql.select(request);
            String resultat = null;
            resultat = dbsql.extract(tuples, 1, "id");
            if(resultat != null){
                idClient = Integer.parseInt(resultat);
            }
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(FOAMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idClient;
    }

    private boolean insertInscription(int idActivite, int idClient, String date) {
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "INSERT INTO inscriptions(idActivite, idVoyageur, date, valider) VALUES('"+idActivite+"', '"+idClient+"', '"+date+"', '0')";
            dbsql.update(request);
            dbsql.Disconnect();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(FOAMP.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private int getPrix(int idActivite) {
        int prix = -1;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT prix_htva FROM activites where id = '" + idActivite + "'";
            Object tuples = dbsql.select(request);
            String resultat = null;
            resultat = dbsql.extract(tuples, 1, "prix_htva");
            if(resultat != null){
                prix = Integer.parseInt(resultat);
            }
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(FOAMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prix;
    }

    private boolean validerInscription(int idActivite, int idClient, String date) {
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "UPDATE inscriptions SET valider = 1 where idActivite = '"+idActivite+"' AND idVoyageur = '"+idClient+"' AND date = '"+date+"'";
            dbsql.update(request);
            dbsql.Disconnect();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(FOAMP.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private int getNumInscription(int idActivite, int idClient, String date) {
        int numInscription = -1;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT idInscription from inscriptions where idActivite = '"+idActivite+"' AND idVoyageur = '"+idClient+"' AND date = '"+date+"'";
            Object tuples = dbsql.select(request);
            String resultat = null;
            resultat = dbsql.extract(tuples, 1, "idInscription");
            if(resultat != null){
                numInscription = Integer.parseInt(resultat);
            }
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(FOAMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numInscription;
    }

    private String generateDate(String session) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateGenerated = sdf.format(date);
        String[] split = dateGenerated.split("/");
        int a = Integer.parseInt(split[0]);
        int b = Integer.parseInt(session);
        if(b <= a){
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            gc.add(Calendar.MONTH, 1);
            date = gc.getTime();
            dateGenerated = sdf.format(date);
            split = dateGenerated.split("/");
        }
        String dateRetour = session + "/" + split[1] + "/" + split[2];
        if(Integer.parseInt(session) < 10){
            dateRetour = "0" + dateRetour;
        }
        return dateRetour;
    }

    private String getMail(String nom, String prenom) {
        String mail = null;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT email as mail from voyageurs where nom='"+nom+"' and prenom='"+prenom+"'";
            Object tuples = dbsql.select(request);
            mail = dbsql.extract(tuples, 1, "mail");
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(FOAMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mail;
    }

}
