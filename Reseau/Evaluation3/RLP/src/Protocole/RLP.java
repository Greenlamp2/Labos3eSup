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


public class RLP {
    public static String LOGIN = "LOGIN";
    public static String LOGIN_OUI = "LOGIN_OUI";
    public static String LOGIN_NON = "LOGIN_NON";
    public static String NONCE = "NONCE";
    public static String LOGIN_NEXT_STEP = "LOGIN_NEXT_STEP";
    public static String ERROR = "ERROR";

    MyCertificate myCertificate_no_ssl_client;
    MyCertificate myCertificate_ssl_client;

    MyCertificate myCertificate_no_ssl_serveur;
    MyCertificate myCertificate_ssl_serveur;

    int number1;

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
            number1 = new Random().nextInt(10000);
            return new PacketCom(RLP.NONCE, (Object)number1);
        }else if(type.equals(RLP.LOGIN_NEXT_STEP)){
            Object[] infos = (Object[]) contenu;
            String login = (String) infos[0];
            byte[] digest = (byte[]) infos[1];
            int number2 = (Integer) infos[2];
            String password = getPassword(login);
            if(password == null){
                return new PacketCom(RLP.LOGIN_NON, "LOGIN_NON");
            }
            byte[] digest2 = generateDigest(login, password, number1, number2);
            if(digestCorrect(digest, digest2)){
                return new PacketCom(RLP.LOGIN_OUI, "LOGIN_OUI");
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
        }else if (type.equals(RLP.NONCE)) {
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

    private String getPassword(String login) {
        String password = null;
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT password FROM gestionnaires where login = '"+ login +"'";
            Object tuples = dbsql.select(request);
            String userFound = dbsql.extract(tuples, 1, "password");
            if(userFound != null){
                password = userFound;
            }
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (IOException ex) {
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return password;
    }

    private byte[] generateDigest(String login, String password, int number1, int number2) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1", "BC");
            md.update(String.valueOf(number1).getBytes());
            md.update(login.getBytes());
            md.update(password.getBytes());
            md.update(String.valueOf(number2).getBytes());
            byte[] digest = md.digest();
            return digest;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private boolean digestCorrect(byte[] digest, byte[] digest2) {
        boolean retour = false;
        retour = MessageDigest.isEqual(digest, digest2);
        return retour;
    }
}
