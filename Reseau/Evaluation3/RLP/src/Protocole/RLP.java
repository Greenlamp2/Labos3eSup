/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole;

import Bean.Jdbc_MySQL;
import Securite.KeyExchange;
import java.beans.Beans;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Securite.MyCertificate;
import Securite.SignatureWithCertificate;
import Utils.Cryptage;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.Random;
import javax.crypto.SecretKey;


public class RLP {
    MyCertificate myCertificateClient;
    MyCertificate myCertificateServer;
    Cryptage cryptageServer;
    SecretKey cleSession;
    int number1 = 0;
    String ou = null;

    public static String LOGIN = "LOGIN";
    public static String LOGIN_OUI = "LOGIN_OUI";
    public static String LOGIN_NON = "LOGIN_NON";
    public static String NONCE = "NONCE";
    public static String LOGIN_NEXT_STEP = "LOGIN_NEXT_STEP";
    public static String KEY_EXCHANGE = "KEY_EXCHANGE";
    public static String SESSION_KEY = "SESSION_KEY";
    public static String BDROOM = "BDROOM";
    public static String BDROOM_OUI = "BDROOM_OUI";
    public static String BDROOM_NON = "BDROOM_NON";
    public static String ARROOM = "ARROOM";
    public static String ARROOM_OUI = "ARROOM_OUI";
    public static String ARROOM_NON = "ARROOM_NON";
    public static String MISROOM = "MISROOM";
    public static String MISROOM_OUI = "ARROOM_OUI";
    public static String MISROOM_NON = "MISROOM_NON";
    public static String ERROR = "ERROR";

    public RLP(){

    }

    public RLP(MyCertificate myCertificateClient){
        this.myCertificateClient = myCertificateClient;
        this.ou = "client";
    }

    public RLP(MyCertificate myCertificateServer, int bidon){
        this.myCertificateServer = myCertificateServer;
        this.cryptageServer = new Cryptage(myCertificateServer);
        this.ou = "server";
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
        }else if(type.equals(RLP.KEY_EXCHANGE)){
            SignatureWithCertificate swc = (SignatureWithCertificate) contenu;
            if(!signatureCorrect(swc)){
                return new PacketCom(RLP.ERROR, "La signature est incorecte");
            }

            myCertificateClient = new MyCertificate(swc.getCertificate());

            KeyExchange keyExchange = new KeyExchange();
            keyExchange.setCertificatServeur(myCertificateServer.getCertificate());
            keyExchange.setCertificatClient(swc.getCertificate());
            PublicKey clePublic = swc.getCertificate().getPublicKey();

            cleSession = cryptageServer.getSessionKey("DES");
            byte[] cleSessionByte = cryptageServer.cleSessionToByte(cleSession);
            byte[] messageCrypter = cryptageServer.crypt(Cryptage.RSA, cleSessionByte, clePublic);
            keyExchange.setCleSession(messageCrypter);
            return new PacketCom(RLP.SESSION_KEY, (Object)keyExchange);

        }else if(type.equals(RLP.BDROOM)){
            Object[] infos = (Object[]) contenu;
            String date = (String) infos[0];
            byte[] signature = (byte[]) infos[1];

            if(!signatureCorrect(new SignatureWithCertificate(myCertificateClient.getCertificate(), signature))){
                return new PacketCom(RLP.BDROOM_NON, "La signature du responsable est incorecte");
            }

            return listeReservations(date);

        }else if(type.equals(RLP.ARROOM)){
            Object[] infos = (Object[]) contenu;
            String numChambre = (String)infos[0];
            String nomClient = (String)infos[1];
            String date = (String)infos[2];
            byte[] signature = (byte[]) infos[3];

            if(!signatureCorrect(new SignatureWithCertificate(myCertificateClient.getCertificate(), signature))){
                return new PacketCom(RLP.ARROOM_NON, "La signature du responsable est incorecte");
            }

            return titulaireArrive(numChambre, nomClient, date);

        }else if(type.equals(RLP.MISROOM)){
            Object[] infos = (Object[]) contenu;
            String numChambre = (String)infos[0];
            String nomClient = (String)infos[1];
            String date = (String)infos[2];
            byte[] signature = (byte[]) infos[3];

            if(!signatureCorrect(new SignatureWithCertificate(myCertificateClient.getCertificate(), signature))){
                return new PacketCom(RLP.MISROOM_NON, "La signature du responsable est incorecte");
            }

            return titulaireRetard(numChambre, nomClient, date);
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
        }else if (type.equals(RLP.SESSION_KEY)) {
            return packet;
        }else if (type.equals(RLP.BDROOM_OUI)) {
            return packet;
        }else if (type.equals(RLP.BDROOM_NON)) {
            return packet;
        }else if (type.equals(RLP.ARROOM_OUI)) {
            return packet;
        }else if (type.equals(RLP.ARROOM_NON)) {
            return packet;
        }else if (type.equals(RLP.MISROOM_OUI)) {
            return packet;
        }else if (type.equals(RLP.MISROOM_NON)) {
            return packet;
        }else {
            PacketCom packetReponse = new PacketCom(RLP.ERROR, "ERROR");
            return packetReponse;
        }
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

    private boolean signatureCorrect(SignatureWithCertificate swc) {
        byte[] signatureAvant = swc.getSignature();
        boolean verif = false;
        Signature signature;
        try {
            signature = Signature.getInstance("SHA1WithRSA", "BC");
            signature.initVerify(swc.getCertificate().getPublicKey());
            verif = signature.verify(signatureAvant);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return verif;
    }

    private synchronized PacketCom listeReservations(String date) {
        LinkedList<String> listeReservation = getListeReservation(date);
        return new PacketCom(RLP.BDROOM_OUI, (Object)listeReservation);
    }

    private synchronized PacketCom titulaireArrive(String numChambre, String nomClient, String date) {
        int numeroClient = -1;
        numeroClient = getNumeroClient(nomClient);
        if(numeroClient == -1){
            return new PacketCom(RLP.ARROOM_NON, "Le client n'existe pas.");
        }

        int idReservation = -1;
        idReservation = getIdReservationWithDate(numChambre, numeroClient, date);
        if(idReservation == -1){
            return new PacketCom(RLP.ARROOM_NON, "la réservation n'existe pas.");
        }

        return new PacketCom(RLP.ARROOM_OUI, "ARROOM_OUI");
    }

    private synchronized PacketCom titulaireRetard(String numChambre, String nomClient, String date) {
        int numeroClient = -1;
        numeroClient = getNumeroClient(nomClient);
        if(numeroClient == -1){
            return new PacketCom(RLP.MISROOM_NON, "Le client n'existe pas.");
        }

        int idReservation = -1;
        idReservation = getIdReservationWithDate(numChambre, numeroClient, date);
        if(idReservation == -1){
            return new PacketCom(RLP.MISROOM_NON, "la réservation n'existe pas.");
        }

        return new PacketCom(RLP.MISROOM_OUI, "MISROOM_OUI");
    }

    private LinkedList<String> getListeReservation(String date) {
        LinkedList<String> listeReservation = new LinkedList<>();
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT numeroChambre, dateArrivee, client FROM reservationschambre where dateArrivee = '"+date+"'";
            ResultSet tuples = (ResultSet) dbsql.select(request);
            while(tuples.next()){
                int numeroChambre = tuples.getInt("numeroChambre");
                String dateArrivee = tuples.getString("dateArrivee");
                int client = tuples.getInt("client");
                String nomClient = getNomClient(client);
                String details = getDetails(numeroChambre);
                String message = "[" + details + "]" + dateArrivee + " => " + numeroChambre + " => " + nomClient;
                listeReservation.add(message);
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
        return listeReservation;
    }

    private String getNomClient(int numeroClient) {
        String nomClient = null;
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT nom from voyageurs where id = '"+numeroClient+"'";
            Object tuples = dbsql.select(request);
            nomClient = dbsql.extract(tuples, 1, "nom");
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (IOException ex) {
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nomClient;
    }

    private String getDetails(int numeroChambre) {
        String categorie = null;
        String type = null;
        String retour = null;
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT catégorie, type from detailschambre where numeroChambre = '"+numeroChambre+"'";
            Object tuples = dbsql.select(request);
            categorie = dbsql.extract(tuples, 1, "catégorie");
            type = dbsql.extract(tuples, 1, "type");
            retour = categorie + "-" + type;
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (IOException ex) {
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retour;
    }

    private int getNumeroClient(String nomClient) {
        int numeroClient = -1;
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT id FROM voyageurs where nom = '"+ nomClient +"'";
            Object tuples = dbsql.select(request);
            String userFound = dbsql.extract(tuples, 1, "id");
            if(userFound != null){
                numeroClient = Integer.parseInt(userFound);
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
        return numeroClient;
    }

    private int getIdReservationWithDate(String numChambre, int numeroClient, String dateReservaton) {
        int idReservation = -1;
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT numeroReservation FROM reservationschambre where numeroChambre = '"+ numChambre +"' and client = '"+ numeroClient +"' and dateArrivee = '"+dateReservaton+"'";
            Object tuples = dbsql.select(request);
            String reservationFound = dbsql.extract(tuples, 1, "numeroReservation");
            if(reservationFound != null){
                idReservation = Integer.parseInt(reservationFound);
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
        return idReservation;
    }


}
