/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole;

import Bean.Jdbc_MySQL;
import Securite.MyCertificate;
import java.beans.Beans;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MAMP {
    public static String TRANSFER_POGN = "TRANSFER_POGN";
    public static String VERIF_INT_SUCCESSFULL = "VERIF_INT_SUCCESSFULL";
    public static String VERIF_INT_FAILED = "VERIF_INT_FAILED";
    public static String ERROR = "ERROR";

    MyCertificate myCertificate_no_ssl_client;
    MyCertificate myCertificate_ssl_client;

    MyCertificate myCertificate_no_ssl_serveur;
    MyCertificate myCertificate_ssl_serveur;

    int number1;

    public MAMP(MyCertificate myCertificate_no_ssl_client, MyCertificate myCertificate_ssl_client){
        this.myCertificate_no_ssl_client = myCertificate_no_ssl_client;
        this.myCertificate_ssl_client = myCertificate_ssl_client;
    }

    public MAMP(MyCertificate myCertificate_no_ssl_serveur, MyCertificate myCertificate_ssl_serveur, int bidon){
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
        if (type.equals(MAMP.TRANSFER_POGN)) {
            return null;
        }else{
            return new PacketCom(MAMP.ERROR, "ERROR");
        }
    }

    private PacketCom traiterPacketClientSide(PacketCom packet) {
        String type = packet.getType();
        Object contenu = packet.getObjet();
        System.out.println("Reçu: " + type);
        if (type.equals(MAMP.VERIF_INT_SUCCESSFULL)) {
            return packet;
        }else if (type.equals(MAMP.VERIF_INT_FAILED)) {
            return packet;
        }else {
            PacketCom packetReponse = new PacketCom(MAMP.ERROR, "ERROR");
            return packetReponse;
        }
    }
}
