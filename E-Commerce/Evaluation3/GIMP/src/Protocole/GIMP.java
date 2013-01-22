/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole;

import Bean.Jdbc_MySQL;
import Securite.MyCertificateSSL;
import java.beans.Beans;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Random;
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

    int number1;

    public GIMP(MyCertificateSSL myCertificate_no_ssl_client, MyCertificateSSL myCertificate_ssl_client){
        this.myCertificate_no_ssl_client = myCertificate_no_ssl_client;
        this.myCertificate_ssl_client = myCertificate_ssl_client;
    }

    public GIMP(MyCertificateSSL myCertificate_no_ssl_serveur, MyCertificateSSL myCertificate_ssl_serveur, int bidon){
        this.myCertificate_no_ssl_serveur = myCertificate_no_ssl_serveur;
        this.myCertificate_ssl_serveur = myCertificate_ssl_serveur;
    }

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
        if (type.equals(GIMP.PAY_FOR_CLIENT)) {
            Object[] infos = (Object[]) contenu;
            String nomClient = (String)infos[0];
            String numCarteCredit = (String)infos[1];
            int idReservation = (int)infos[2];
            return verificationCarte(nomClient, numCarteCredit, idReservation);
        }else{
            return new PacketCom(GIMP.ERROR, "ERROR");
        }
    }

    private PacketCom traiterPacketClientSide(PacketCom packet) {
        String type = packet.getType();
        Object contenu = packet.getObjet();
        System.out.println("Reçu: " + type);
        if (type.equals(GIMP.VERIF_CARD_SUCCESSFULL)) {
            return packet;
        }else if (type.equals(GIMP.VERIF_CARD_FAILED)) {
            return packet;
        }else {
            PacketCom packetReponse = new PacketCom(GIMP.ERROR, "ERROR");
            return packetReponse;
        }
    }

    private PacketCom verificationCarte(String nomClient, String numCarteCredit, int idReservation) {
        return new PacketCom(GIMP.VERIF_CARD_FAILED, "");
    }
}
