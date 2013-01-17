/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole;

import Bean.Jdbc_MySQL;
import Securite.MyCertificate;
import java.beans.Beans;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RLP {
    public static String LOGIN = "LOGIN";
    public static String LOGIN_OUI = "LOGIN_OUI";
    public static String LOGIN_NON = "LOGIN_NON";
    public static String ERROR = "ERROR";

    MyCertificate myCertificate_no_ssl_client;
    MyCertificate myCertificate_ssl_client;

    MyCertificate myCertificate_no_ssl_serveur;
    MyCertificate myCertificate_ssl_serveur;

    public RLP(MyCertificate myCertificate_no_ssl_client, MyCertificate myCertificate_ssl_client){
        this.myCertificate_no_ssl_client = myCertificate_no_ssl_client;
        this.myCertificate_ssl_client = myCertificate_ssl_client;
    }

    public RLP(MyCertificate myCertificate_no_ssl_serveur, MyCertificate myCertificate_ssl_serveur, int bidon){
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
        if (type.equals(RLP.LOGIN)) {
            String[] infos = (String[]) contenu;
            String login = infos[0];
            String password = infos[1];
            if (gestionLogin(login, password)) {
                PacketCom packetRetour = new PacketCom(RLP.LOGIN_OUI, "LOGIN_OUI");
                return packetRetour;
            }else{
                return new PacketCom(RLP.LOGIN_NON, "LOGIN_NON");
            }
        }else{
            return new PacketCom(RLP.ERROR, "ERROR");
        }
    }

    private PacketCom traiterPacketClientSide(PacketCom packet) {
        String type = packet.getType();
        Object contenu = packet.getObjet();
        System.out.println("Reçu: " + type);
        if (type.equals(RLP.LOGIN_OUI)) {
            return packet;
        }else if (type.equals(RLP.LOGIN_NON)) {
            return packet;
        }else {
            PacketCom packetReponse = new PacketCom(RLP.ERROR, "ERROR");
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
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return found;
    }
}
