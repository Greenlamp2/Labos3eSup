/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GIMP;

import Bean.Jdbc_MySQL;
import Commun.MyCertificateSSL;
import Commun.PacketComSSL;
import java.beans.Beans;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GIMP {
    public static String PAY_FOR_CLIENT = "PAY_FOR_CLIENT";
    public static String VERIF_CARD_SUCCESSFULL = "VERIF_CARD_SUCCESSFULL";
    public static String VERIF_CARD_FAILED = "VERIF_CARD_FAILED";
    public static String ERROR = "ERROR";

    MyCertificateSSL myCertificate_no_ssl_client;
    MyCertificateSSL myCertificate_ssl_client;

    MyCertificateSSL myCertificate_no_ssl_serveur;
    MyCertificateSSL myCertificate_ssl_serveur;

    String orga;

    public GIMP(MyCertificateSSL myCertificate_no_ssl_client, MyCertificateSSL myCertificate_ssl_client){
        this.myCertificate_no_ssl_client = myCertificate_no_ssl_client;
        this.myCertificate_ssl_client = myCertificate_ssl_client;
    }

    public GIMP(MyCertificateSSL myCertificate_no_ssl_serveur, MyCertificateSSL myCertificate_ssl_serveur, int bidon, String orga){
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
        System.out.println("retour: " + message.getType());
        return message;
    }

    private PacketComSSL traiterPacketServerSide(PacketComSSL packet) {
        String type = packet.getType();
        System.out.println("type reçu: " + type);
        Object contenu = packet.getObjet();
        System.out.println("Reçu: " + type);
        if (type.equals(GIMP.PAY_FOR_CLIENT)) {
            System.out.println("1");
            Object[] infos = (Object[]) contenu;
            System.out.println("2");
            String nomClient = (String)infos[0];
            System.out.println("3");
            String numCarteCredit = (String)infos[1];
            System.out.println("4");
            int idReservation = (int)infos[2];
            System.out.println("5");
            return verificationCarte(nomClient, numCarteCredit, idReservation);
        }else{
            return new PacketComSSL(GIMP.ERROR, "ERROR");
        }
    }

    private PacketComSSL traiterPacketClientSide(PacketComSSL packet) {
        String type = packet.getType();
        Object contenu = packet.getObjet();
        System.out.println("Reçu: " + type);
        if (type.equals(GIMP.VERIF_CARD_SUCCESSFULL)) {
            return packet;
        }else if (type.equals(GIMP.VERIF_CARD_FAILED)) {
            return packet;
        }else {
            PacketComSSL packetReponse = new PacketComSSL(GIMP.ERROR, "ERROR");
            return packetReponse;
        }
    }

    private PacketComSSL verificationCarte(String nomClient, String numCarteCredit, int idReservation) {
        System.out.println("orga: " + this.orga);
        if(this.orga.equals("vil")){
            if(isInDbVilVisa(numCarteCredit)){
                return new PacketComSSL(GIMP.VERIF_CARD_SUCCESSFULL, "");
            }else{
                return new PacketComSSL(GIMP.VERIF_CARD_FAILED, "");
            }
        }else{
            if(isInDbVMasterKuty(numCarteCredit)){
                return new PacketComSSL(GIMP.VERIF_CARD_SUCCESSFULL, "");
            }else{
                return new PacketComSSL(GIMP.VERIF_CARD_FAILED, "");
            }
        }
    }

    private boolean isInDbVilVisa(String numCarteCredit) {
        String numCarte = null;
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT numCarte from vilvisa where numCarte = '"+numCarteCredit+"'";
            Object tuples = dbsql.select(request);
            numCarte = dbsql.extract(tuples, 1, "numCarte");
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (IOException ex) {
            Logger.getLogger(GIMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GIMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GIMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(numCarte == null){
            return false;
        }else{
            return true;
        }
    }

    private boolean isInDbVMasterKuty(String numCarteCredit) {
        String numCarte = null;
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT numCarte from masterkuty where numCarte = '"+numCarteCredit+"'";
            Object tuples = dbsql.select(request);
            numCarte = dbsql.extract(tuples, 1, "numCarte");
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (IOException ex) {
            Logger.getLogger(GIMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GIMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GIMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(numCarte == null){
            return false;
        }else{
            return true;
        }
    }
}
