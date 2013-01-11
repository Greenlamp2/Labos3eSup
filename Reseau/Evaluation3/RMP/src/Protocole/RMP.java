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
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Random;
import javax.crypto.SecretKey;


public class RMP {
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
    public static String BROOM = "BROOM";
    public static String BROOM_OUI = "BROOM_OUI";
    public static String BROOM_NON = "BROOM_NON";
    public static String PROOM = "PROOM";
    public static String PROOM_OUI = "PROOM_OUI";
    public static String PROOM_NON = "PROOM_NON";
    public static String CROOM = "CROOM";
    public static String CROOM_OUI = "CROOM_OUI";
    public static String CROOM_NON = "CROOM_NON";
    public static String LROOM = "LROOM";
    public static String LROOM_OUI = "LROOM_OUI";
    public static String LROOM_NON = "LROOM_NON";
    public static String ERROR = "ERROR";

    public RMP(){

    }

    public RMP(MyCertificate myCertificateClient){
        this.myCertificateClient = myCertificateClient;
        this.ou = "client";
    }

    public RMP(MyCertificate myCertificateServer, int bidon){
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
        if (type.equals(RMP.LOGIN)) {
            number1 = new Random().nextInt(10000);
            return new PacketCom(RMP.NONCE, (Object)number1);
        }else if(type.equals(RMP.LOGIN_NEXT_STEP)){
            Object[] infos = (Object[]) contenu;
            String login = (String) infos[0];
            byte[] digest = (byte[]) infos[1];
            int number2 = (Integer) infos[2];
            String password = getPassword(login);
            if(password == null){
                return new PacketCom(RMP.LOGIN_NON, "LOGIN_NON");
            }
            byte[] digest2 = generateDigest(login, password, number1, number2);
            if(digestCorrect(digest, digest2)){
                return new PacketCom(RMP.LOGIN_OUI, "LOGIN_OUI");
            }else{
                return new PacketCom(RMP.LOGIN_NON, "LOGIN_NON");
            }
        }else if(type.equals(RMP.KEY_EXCHANGE)){
            SignatureWithCertificate swc = (SignatureWithCertificate) contenu;
            if(!signatureCorrect(swc)){
                return new PacketCom(RMP.ERROR, "La signature est incorecte");
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
            return new PacketCom(RMP.SESSION_KEY, (Object)keyExchange);

        }else if(type.equals(RMP.BROOM)){
            Object[] infos = (Object[]) contenu;
            byte[] categorieCrypted = (byte[]) infos[0];
            byte[] typeCrypted = (byte[]) infos[1];
            byte[] dateCrypted = (byte[]) infos[2];
            byte[] nbNuitCrypted = (byte[]) infos[3];
            byte[] nomClientCrypted = (byte[]) infos[4];
            byte[] signatureResponsable = (byte[]) infos[5];
            byte[] digest = (byte[]) infos[6];

            MessageDigest md;
            byte[] digestVerif = null;
            try {
                md = MessageDigest.getInstance("SHA1");
                md.update(categorieCrypted);
                md.update(typeCrypted);
                md.update(dateCrypted);
                md.update(nbNuitCrypted);
                md.update(nomClientCrypted);
                md.update(signatureResponsable);
                digestVerif = md.digest();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
            }

            boolean verification = MessageDigest.isEqual(digest, digestVerif);
            if(!verification){
                return new PacketCom(RMP.BROOM_NON, "L'intégrité des données n'est pas intacte.");
            }

            if(!signatureCorrect(new SignatureWithCertificate(myCertificateClient.getCertificate(), signatureResponsable))){
                return new PacketCom(RMP.BROOM_NON, "La signature du responsable est incorecte");
            }

            String categorieDecrypted = new String(cryptageServer.decrypt(Cryptage.DES, categorieCrypted, cleSession));
            String typeDecrypted = new String(cryptageServer.decrypt(Cryptage.DES, typeCrypted, cleSession));
            String dateDecrypted = new String(cryptageServer.decrypt(Cryptage.DES, dateCrypted, cleSession));
            String nbNuitDecrypted = new String(cryptageServer.decrypt(Cryptage.DES, nbNuitCrypted, cleSession));
            String nomClientDecrypted = new String(cryptageServer.decrypt(Cryptage.DES, nomClientCrypted, cleSession));

            return reservationChambre(categorieDecrypted, typeDecrypted, dateDecrypted, nbNuitDecrypted, nomClientDecrypted);

        }else{
            return new PacketCom(RMP.ERROR, "ERROR");
        }
    }

    private PacketCom traiterPacketClientSide(PacketCom packet) {
        String type = packet.getType();
        Object contenu = packet.getObjet();
        System.out.println("Reçu: " + type);
        if (type.equals(RMP.LOGIN_OUI)) {
            return packet;
        }else if (type.equals(RMP.LOGIN_NON)) {
            return packet;
        }else if (type.equals(RMP.NONCE)) {
            return packet;
        }else if (type.equals(RMP.SESSION_KEY)) {
            return packet;
        }else if (type.equals(RMP.BROOM_OUI)) {
            return packet;
        }else if (type.equals(RMP.BROOM_NON)) {
            return packet;
        }else {
            PacketCom packetReponse = new PacketCom(RMP.ERROR, "ERROR");
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
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return verif;
    }

    private PacketCom reservationChambre(String categorie, String type, String date, String nbNuit, String nomClient) {
        /**************************************************************************************************************/
        //Verification de l'existence du client
        /**************************************************************************************************************/
        int numeroClient = -1;
        numeroClient = getNumeroClient(nomClient);
        if(numeroClient == -1){
            return new PacketCom(RMP.BROOM_NON, "Le client n'existe pas.");
        }

        /**************************************************************************************************************/
        //Verification si chambre correspondant aux criteres
        /**************************************************************************************************************/
        LinkedList<Integer> listeChambres = new LinkedList<>();
        listeChambres = getChambres(categorie, type, date, nbNuit);
        if(listeChambres.isEmpty()){
            return new PacketCom(RMP.BROOM_NON, "Aucunes chambres ne correspondent à vos critères.");
        }

        /**************************************************************************************************************/
        //Calculer l'intervalle de date ex: 01/01/2013 pendant 3 nuit => 01/01/2013 jusqu'au 04/01/2013
        /**************************************************************************************************************/
        String dateFin = getDateFin(date, nbNuit);

        /**************************************************************************************************************/
        //Verifier les reservations
        /**************************************************************************************************************/
        LinkedList<Integer> listeChambresDispo = new LinkedList<>();
        listeChambresDispo = verifReservations(listeChambres, date, dateFin);
        if(listeChambresDispo.isEmpty()){
            return new PacketCom(RMP.BROOM_NON, "Aucunes chambres disponible à cette date.");
        }

        int numeroChambre = listeChambresDispo.getFirst();
        int prixChambre = getPrixChambre(numeroChambre);

        /**************************************************************************************************************/
        //Faire la reservation
        /**************************************************************************************************************/
        if(!reserverChambre(numeroChambre, numeroClient, date, nbNuit)){
            return new PacketCom(RMP.BROOM_NON, "Une erreur s'est déroulée lors de la réservation.");
        }

        Object[] infos = {numeroChambre, prixChambre};
        return new PacketCom(RMP.BROOM_OUI, (Object)infos);
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
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numeroClient;
    }

    private LinkedList<Integer> getChambres(String categorie, String type, String date, String nbNuit) {
        LinkedList<Integer> listeChambre = new LinkedList<>();
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT numeroChambre FROM detailschambre where catégorie = '"+ categorie +"' and type = '"+type+"'";
            ResultSet tuples = (ResultSet) dbsql.select(request);
            while(tuples.next()){
                int numeroChambre = tuples.getInt("numeroChambre");
                listeChambre.add(numeroChambre);
            }
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (IOException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listeChambre;
    }

    private String getDateFin(String date, String nbNuit) {
        String retour = null;
        try {
            Date dateConverted = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(dateConverted);
            gc.add(GregorianCalendar.HOUR, 24*(Integer.parseInt(nbNuit)));
            Date dateFin = gc.getTime();
            retour = new SimpleDateFormat("dd/MM/yyyy").format(dateFin);
        } catch (ParseException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retour;
    }

    private LinkedList<Integer> verifReservations(LinkedList<Integer> listeChambres, String date, String dateFin) {
        LinkedList<Integer> listeChambresDispo = new LinkedList<>();
        for(int numeroChambre : listeChambres){
            if(dateOk(numeroChambre, date, dateFin)){
                listeChambresDispo.add(numeroChambre);
            }
        }
        return listeChambresDispo;
    }

    private boolean dateOk(int numeroChambre, String date, String dateFin) {
        boolean ok = false;
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT dateArrivee, nbNuit FROM reservationschambre where numeroChambre = '"+numeroChambre+"'";
            Object tuples = dbsql.select(request);
            String dateFound = dbsql.extract(tuples, 1, "dateArrivee");
            String nbFound = dbsql.extract(tuples, 1, "nbNuit");
            if(dateFound != null && nbFound != null){
                String dateFinCalculated = getDateFin(dateFound, nbFound);
                Date dateFinDb = new SimpleDateFormat("dd/MM/yyyy").parse(dateFinCalculated);
                Date dateFinChambre = new SimpleDateFormat("dd/MM/yyyy").parse(dateFin);
                if(dateFinDb.after(dateFinChambre) || dateFinDb.before(dateFinChambre)){
                    ok = true;
                }
            }else{
                ok = true;
            }
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (IOException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ok;
    }

    private int getPrixChambre(int numeroChambre) {
        int prix = -1;
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT prix FROM detailschambre where numeroChambre = '"+ numeroChambre +"'";
            Object tuples = dbsql.select(request);
            String roomFound = dbsql.extract(tuples, 1, "prix");
            if(roomFound != null){
                prix = Integer.parseInt(roomFound);
            }
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (IOException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prix;
    }

    private boolean reserverChambre(int numeroChambre, int numeroClient, String date, String nbNuit) {
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "INSERT INTO reservationschambre(numeroChambre, dateArrivee, nbNuit, client) VALUES('"+numeroChambre+"', '"+date+"', '"+nbNuit+"', '"+numeroClient+"')";
            dbsql.update(request);
            dbsql.Disconnect();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
