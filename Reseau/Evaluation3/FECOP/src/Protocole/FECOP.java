/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole;

import Bean.Jdbc_MySQL;
import Helpers.EasyFile;
import java.beans.Beans;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FECOP {
    public static String LOGIN_GROUP = "LOGIN_GROUP";
    public static String LOGIN_GROUP_OUI = "LOGIN_GROUP_OUI";
    public static String LOGIN_GROUP_NON = "LOGIN_GROUP_NON";
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
        if (type.equals(FECOP.LOGIN_GROUP)) {
            String[] infos = (String[]) contenu;
            String login = infos[0];
            String password = infos[1];
            if (gestionLogin(login, password)) {
                String ipUdp = EasyFile.getConfig("Configs_Serveur_Chat-Hollidays", "HOST_UDP");
                String portUdp = EasyFile.getConfig("Configs_Serveur_Chat-Hollidays", "PORT_UDP");
                Object[] infosRetour = {ipUdp, portUdp};
                PacketCom packetRetour = new PacketCom(FECOP.LOGIN_GROUP_OUI, (Object)infosRetour);
                return packetRetour;
            }else{
                return new PacketCom(FECOP.LOGIN_GROUP_NON, "LOGIN_NON");
            }
        }else{
            return new PacketCom(FECOP.ERROR, "ERROR");
        }
    }

    private PacketCom traiterPacketClientSide(PacketCom packet) {
        String type = packet.getType();
        Object contenu = packet.getObjet();
        System.out.println("Reçu: " + type);
        if (type.equals(FECOP.LOGIN_GROUP_OUI)) {
            return packet;
        }else if (type.equals(FECOP.LOGIN_GROUP_NON)) {
            return packet;
        }else {
            PacketCom packetReponse = new PacketCom(FECOP.ERROR, "ERROR");
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
            Logger.getLogger(FECOP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return found;
    }
}
