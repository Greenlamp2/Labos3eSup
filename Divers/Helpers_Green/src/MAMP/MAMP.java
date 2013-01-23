/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MAMP;

import Bean.Jdbc_MySQL;
import Commun.MyCertificateSSL;
import Commun.PacketComSSL;
import java.beans.Beans;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MAMP {
    public static String TRANSFER_POGN = "TRANSFER_POGN";
    public static String VERIF_INT_SUCCESSFULL = "VERIF_INT_SUCCESSFULL";
    public static String VERIF_INT_FAILED = "VERIF_INT_FAILED";
    public static String ERROR = "ERROR";

    MyCertificateSSL myCertificate_no_ssl_client;
    MyCertificateSSL myCertificate_ssl_client;

    MyCertificateSSL myCertificate_no_ssl_serveur;
    MyCertificateSSL myCertificate_ssl_serveur;

    String orga;

    public MAMP(MyCertificateSSL myCertificate_no_ssl_client, MyCertificateSSL myCertificate_ssl_client){
        this.myCertificate_no_ssl_client = myCertificate_no_ssl_client;
        this.myCertificate_ssl_client = myCertificate_ssl_client;
    }

    public MAMP(MyCertificateSSL myCertificate_no_ssl_serveur, MyCertificateSSL myCertificate_ssl_serveur, int bidon, String orga){
        this.orga = orga;
        this.myCertificate_no_ssl_serveur = myCertificate_no_ssl_serveur;
        this.myCertificate_ssl_serveur = myCertificate_ssl_serveur;
    }

    PacketComSSL messageFromClient(Object objet) {
        PacketComSSL packet = (PacketComSSL) objet;
        PacketComSSL messageToClient = traiterPacketServerSide(packet);
        return messageToClient;
    }

    PacketComSSL messageFromServer(Object objet) {
        PacketComSSL packet = (PacketComSSL) objet;
        PacketComSSL message = traiterPacketClientSide(packet);
        return message;
    }

    private PacketComSSL traiterPacketServerSide(PacketComSSL packet) {
        String type = packet.getType();
        System.out.println("type reçu: " + type);
        Object contenu = packet.getObjet();
        System.out.println("Reçu: " + type);
        if (type.equals(MAMP.TRANSFER_POGN)) {
            Object[] infos = (Object[]) contenu;
            int somme = (int)infos[0];
            String nomClient = (String)infos[1];
            int idReservation = (int)infos[2];
            String numCompteInpresHollidays = (String)infos[3];
            return effectuerVirement(somme, nomClient, idReservation, numCompteInpresHollidays);
        }else{
            return new PacketComSSL(MAMP.ERROR, "ERROR");
        }
    }

    private PacketComSSL traiterPacketClientSide(PacketComSSL packet) {
        String type = packet.getType();
        Object contenu = packet.getObjet();
        System.out.println("Reçu: " + type);
        if (type.equals(MAMP.VERIF_INT_SUCCESSFULL)) {
            return packet;
        }else if (type.equals(MAMP.VERIF_INT_FAILED)) {
            return packet;
        }else {
            PacketComSSL packetReponse = new PacketComSSL(MAMP.ERROR, "ERROR");
            return packetReponse;
        }
    }

    private PacketComSSL effectuerVirement(int somme, String nomClient, int idReservation, String numCompteInpresHollidays) {
        boolean payementAutorise = true;
        if(payementAutorise){
            return ajouterTransaction(somme, nomClient, idReservation, numCompteInpresHollidays);
        }else{
            return new PacketComSSL(MAMP.VERIF_INT_FAILED, "Payement non autorisé");
        }
    }

    private PacketComSSL ajouterTransaction(int somme, String nomClient, int idReservation, String numCompteInpresHollidays) {
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "INSERT INTO transaction_vilvisa(somme, nom_client, num_reservation, num_compte) VALUES('"+somme+"', '"+nomClient+"', '"+idReservation+"', '"+numCompteInpresHollidays+"')";
            dbsql.update(request);
            dbsql.Disconnect();
            return new PacketComSSL(MAMP.VERIF_INT_SUCCESSFULL, "");
        } catch (Exception ex) {
            Logger.getLogger(MAMP.class.getName()).log(Level.SEVERE, null, ex);
            return new PacketComSSL(MAMP.VERIF_INT_FAILED, "Payement échoué");
        }
    }
}
