/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole;

import Bean.Jdbc_MySQL;
import Commun.MyCertificateSSL;
import Commun.PacketComSSL;
import GIMP.GIMP;
import GIMP.GIMPNetworkClientSSL;
import Helpers.EasyFile;
import MAMP.MAMP;
import MAMP.MAMPNetworkClientSSL;
import Securite.KeyExchange;
import Securite.MyCertificate;
import Securite.MyKeys;
import Securite.SignatureWithCertificate;
import Utils.Cryptage;
import java.beans.Beans;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;



public class RMP {
    private MyCertificate myCertificateClient;
    MyCertificate myCertificateServer;
    Cryptage cryptageServer;
    SecretKey cleSession;
    int number1 = 0;
    String ou = null;
    boolean mobile = false;

    MAMPNetworkClientSSL socketBanqueVilVisa;
    MAMPNetworkClientSSL socketBanqueMasterKuty;
    GIMPNetworkClientSSL socketCreditVilvisa;
    GIMPNetworkClientSSL socketCreditMasterKuty;

    String hostBanque;
    String hostCredit;
    int portBanqueVilVisa;
    int portBanqueMasterKuty;
    int portCreditVilVisa;
    int portCreditMasterKuty;

    String pathKsSSL;

    MyKeys myKeysSSL;


    public static String LOGIN = "LOGIN";
    public static String LOGIN_OUI = "LOGIN_OUI";
    public static String LOGIN_NON = "LOGIN_NON";
    public static String NONCE = "NONCE";
    public static String LOGIN_NEXT_STEP = "LOGIN_NEXT_STEP";
    public static String LOGIN_VOY_NEXT_STEP = "LOGIN_VOY_NEXT_STEP";
    public static String KEY_EXCHANGE = "KEY_EXCHANGE";
    public static String SESSION_KEY = "SESSION_KEY";
    public static String GET_SESSION_KEY = "GET_SESSION_KEY";
    public static String GET_SESSION_KEY_OUI = "GET_SESSION_KEY_OUI";
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

    public static enum Orga { VILVISA, MASTERKUTY, NONE };

    public RMP(){

    }

    public RMP(MyCertificate myCertificateClient, boolean mobile){
        this.mobile = mobile;
        this.myCertificateClient = myCertificateClient;
        this.ou = "client";
    }

    public RMP(MyCertificate myCertificateServer, int bidon, boolean mobile){
        socketBanqueVilVisa = null;
        socketBanqueMasterKuty = null;
        socketCreditVilvisa = null;
        socketCreditMasterKuty = null;

        hostBanque = EasyFile.getConfig("Configs_Serveur_Reservations", "HOST_BANQUE");
        hostBanque = EasyFile.getConfig("Configs_Serveur_Reservations", "HOST_CREDIT");
        portBanqueVilVisa = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Reservations", "PORT_BANQUE_VILVISA"));
        portBanqueMasterKuty = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Reservations", "PORT_BANQUE_MASTERKUTY"));
        portCreditVilVisa = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Reservations", "PORT_CREDIT_VILVISA"));
        portCreditMasterKuty = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Reservations", "PORT_CREDIT_MASTERKUTY"));

        pathKsSSL = EasyFile.getConfig("Configs_Serveur_Reservations", "ADRESSE_KS_SSL");

        myKeysSSL = null;



        this.mobile = mobile;
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
        }else if(type.equals(RMP.LOGIN_VOY_NEXT_STEP)){
            Object[] infos = (Object[]) contenu;
            String login = (String) infos[0];
            byte[] digest = (byte[]) infos[1];
            int number2 = (Integer) infos[2];
            String password = getPasswordVoy(login);
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

            setMyCertificateClient(new MyCertificate(swc.getCertificate()));

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

        }else if(type.equals(RMP.PROOM)){
            Object[] infos = (Object[]) contenu;
            byte[] numChambreCrypted = (byte[])infos[0];
            byte[] nomClientCrypted = (byte[])infos[1];
            byte[] numCarteCreditCrypted = (byte[])infos[2];
            byte[] dateCrypted = (byte[])infos[3];
            byte[] signatureResponsable = (byte[]) infos[4];
            byte[] digest = (byte[]) infos[5];

            MessageDigest md;
            byte[] digestVerif = null;
            try {
                md = MessageDigest.getInstance("SHA1");
                md.update(numChambreCrypted);
                md.update(nomClientCrypted);
                md.update(numCarteCreditCrypted);
                md.update(dateCrypted);
                md.update(signatureResponsable);
                digestVerif = md.digest();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
            }

            boolean verification = MessageDigest.isEqual(digest, digestVerif);
            if(!verification){
                return new PacketCom(RMP.PROOM_NON, "L'intégrité des données n'est pas intacte.");
            }

            if(!signatureCorrect(new SignatureWithCertificate(myCertificateClient.getCertificate(), signatureResponsable))){
                return new PacketCom(RMP.PROOM_NON, "La signature du responsable est incorecte");
            }

            String numChambreDecrypted = new String(cryptageServer.decrypt(Cryptage.DES, numChambreCrypted, cleSession));
            String nomClientDecrypted = new String(cryptageServer.decrypt(Cryptage.DES, nomClientCrypted, cleSession));
            String numCarteCreditDecrypted = new String(cryptageServer.decrypt(Cryptage.DES, numCarteCreditCrypted, cleSession));
            String dateDecrypted = new String(cryptageServer.decrypt(Cryptage.DES, dateCrypted, cleSession));

            return payementReservation(numChambreDecrypted, nomClientDecrypted, numCarteCreditDecrypted, dateDecrypted);
        }else if(type.equals(RMP.CROOM)){
            Object[] infos = (Object[]) contenu;
            String numChambre = (String)infos[0];
            String nomClient = (String)infos[1];
            String dateReservaton = (String)infos[2];
            byte[] signatureResponsable = (byte[]) infos[3];
            byte[] digest = (byte[]) infos[4];

            MessageDigest md;
            byte[] digestVerif = null;
            try {
                md = MessageDigest.getInstance("SHA1");
                md.update(numChambre.getBytes());
                md.update(nomClient.getBytes());
                md.update(dateReservaton.getBytes());
                md.update(signatureResponsable);
                digestVerif = md.digest();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
            }

            boolean verification = MessageDigest.isEqual(digest, digestVerif);
            if(!verification){
                return new PacketCom(RMP.PROOM_NON, "L'intégrité des données n'est pas intacte.");
            }

            if(!signatureCorrect(new SignatureWithCertificate(myCertificateClient.getCertificate(), signatureResponsable))){
                return new PacketCom(RMP.CROOM_NON, "La signature du responsable est incorecte");
            }

            return supprimerReservation(numChambre, nomClient, dateReservaton);
        }else if(type.equals(RMP.LROOM)){
            //Récupérer les réservations
            LinkedList<String> listeReservation = getAllReservations();
            //Crypter les tuples
            LinkedList<byte[]> listeReservationCrypted = cryptListeReservation(listeReservation);
            //envoyer tuple
            return new PacketCom(RMP.LROOM_OUI, (Object)listeReservationCrypted);

        }else if(type.equals(RMP.GET_SESSION_KEY)){
            cleSession = cryptageServer.getSessionKey("DES");
            byte[] cleSessionByte = cryptageServer.cleSessionToByte(cleSession);
            String message = Base64.encode(cleSessionByte);
            return new PacketCom(RMP.GET_SESSION_KEY_OUI, (Object)message);
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
        }else if (type.equals(RMP.PROOM_OUI)) {
            return packet;
        }else if (type.equals(RMP.PROOM_NON)) {
            return packet;
        }else if (type.equals(RMP.CROOM_OUI)) {
            return packet;
        }else if (type.equals(RMP.CROOM_NON)) {
            return packet;
        }else if (type.equals(RMP.LROOM_OUI)) {
            return packet;
        }else if (type.equals(RMP.LROOM_NON)) {
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

    private String getPasswordVoy(String login) {
        String password = null;
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT password FROM voyageurs where nom = '"+ login +"'";
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

    private synchronized PacketCom reservationChambre(String categorie, String type, String date, String nbNuit, String nomClient) {
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

    private synchronized PacketCom payementReservation(String numChambre, String nomClient, String numCarteCredit, String date) {
        /**************************************************************************************************************/
        //Verification de l'existence du client
        /**************************************************************************************************************/
        int numeroClient = -1;
        numeroClient = getNumeroClient(nomClient);
        if(numeroClient == -1){
            return new PacketCom(RMP.PROOM_NON, "Le client n'existe pas.");
        }
        /**************************************************************************************************************/
        //Verifier si la reservation existe
        /**************************************************************************************************************/
        int idReservation = -1;
        idReservation = getIdReservationWithDate(numChambre, numeroClient, date);
        if(idReservation == -1){
            return new PacketCom(RMP.PROOM_NON, "la réservation n'existe pas.");
        }

        /**************************************************************************************************************/
        //Verifier si la reservation a pas déja été payé
        /**************************************************************************************************************/
        if(reservationDejaPayee(idReservation)){
            return new PacketCom(RMP.PROOM_NON, "la réservation a déja été payée.");
        }

        Orga orga = verificationCarte(nomClient, numCarteCredit, idReservation);
        if(orga == RMP.Orga.NONE){
            return new PacketCom(RMP.PROOM_NON, "La carte de crédit n'appartientà aucuns organisme");
        }
        String numCompteInpresHollidays = EasyFile.getConfig("Configs_Serveur_Reservations", "NUM_COMPTE_INPRES_HOLLIDAYS");
        int prix = getPrixChambre(Integer.parseInt(numChambre));
        int nbNuit = getNbNuit(idReservation);
        int somme = prix * nbNuit;
        PacketComSSL retour = effectuerPayement(orga, somme, nomClient, idReservation,numCompteInpresHollidays);
        if(retour.getType().equals(MAMP.VERIF_INT_FAILED)){
            String message = (String)retour.getObjet();
            return new PacketCom(RMP.PROOM_NON, (Object)message);
        }



        /**************************************************************************************************************/
        //Enregistrer numero carte credit
        /**************************************************************************************************************/
        if(!enregistrerNumeroCarte(numeroClient, numCarteCredit)){
            return new PacketCom(RMP.PROOM_NON, "Erreur au niveau de la carte de crédit");
        }

        /**************************************************************************************************************/
        //Mettre le flag payé a 1
        /**************************************************************************************************************/
        if(!payerReservation(idReservation)){
            return new PacketCom(RMP.PROOM_NON, "Une erreur s'est déroulée lors du payement de la réservation.");
        }



        return new PacketCom(RMP.PROOM_OUI, "PROOM_OUI");

    }

    private synchronized PacketCom supprimerReservation(String numChambre, String nomClient, String dateReservaton) {
        /**************************************************************************************************************/
        //Verification de l'existence du client
        /**************************************************************************************************************/
        int numeroClient = -1;
        numeroClient = getNumeroClient(nomClient);
        if(numeroClient == -1){
            return new PacketCom(RMP.CROOM_NON, "Le client n'existe pas.");
        }
        /**************************************************************************************************************/
        //Verifier si la reservation existe
        /**************************************************************************************************************/
        int idReservation = -1;
        idReservation = getIdReservationWithDate(numChambre, numeroClient, dateReservaton);
        if(idReservation == -1){
            return new PacketCom(RMP.CROOM_NON, "la réservation n'existe pas.");
        }

        /**************************************************************************************************************/
        //Verifier si la reservation a pas déja été payé
        /**************************************************************************************************************/
        if(reservationDejaPayee(idReservation)){
            return new PacketCom(RMP.CROOM_NON, "Vous ne pouvez annuler une réservation déja payée");
        }

        /**************************************************************************************************************/
        //Verifier si la date est dépassée
        /**************************************************************************************************************/
        if(dateDepassee(idReservation)){
            return new PacketCom(RMP.CROOM_NON, "date dépassée.");
        }


        /**************************************************************************************************************/
        //Supprimer réservation
        /**************************************************************************************************************/
        if(!deleteReservation(idReservation)){
            return new PacketCom(RMP.CROOM_NON, "Une erreur s'est déroulée lors de la suppression de la réservation.");
        }

        return new PacketCom(RMP.CROOM_OUI, "PROOM_OUI");
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
            String request = "SELECT dateArrivee, nbNuit FROM reservationschambre where numeroChambre = '"+numeroChambre+"' and dateArrivee = '"+date+"'";
            Object tuples = dbsql.select(request);
            String dateFound = dbsql.extract(tuples, 1, "dateArrivee");
            String nbFound = dbsql.extract(tuples, 1, "nbNuit");
            if(dateFound != null && nbFound != null){
                String dateFinCalculated = getDateFin(dateFound, nbFound);
                Date dateFinDb = new SimpleDateFormat("dd/MM/yyyy").parse(dateFinCalculated);
                Date dateFinChambre = new SimpleDateFormat("dd/MM/yyyy").parse(dateFin);
                if(!date.equals(dateFound)){
                    if(dateFinDb.after(dateFinChambre) || dateFinDb.before(dateFinChambre)){
                        ok = true;
                    }
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
            String request = "INSERT INTO reservationschambre(numeroChambre, dateArrivee, nbNuit, client, paye) VALUES('"+numeroChambre+"', '"+date+"', '"+nbNuit+"', '"+numeroClient+"', '0')";
            dbsql.update(request);
            dbsql.Disconnect();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
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
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idReservation;
    }

    private int getIdReservation(String numChambre, int numeroClient) {
        int idReservation = -1;
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT numeroReservation FROM reservationschambre where numeroChambre = '"+ numChambre +"' and client = '"+ numeroClient +"'";
            Object tuples = dbsql.select(request);
            String reservationFound = dbsql.extract(tuples, 1, "numeroReservation");
            if(reservationFound != null){
                idReservation = Integer.parseInt(reservationFound);
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
        return idReservation;
    }

    private boolean reservationDejaPayee(int idReservation) {
        boolean retour = false;
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT numeroReservation FROM reservationschambre where numeroReservation = '"+idReservation+"' and paye = '1'";
            Object tuples = dbsql.select(request);
            String reservationFound = dbsql.extract(tuples, 1, "numeroReservation");
            if(reservationFound != null){
                retour = true;
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
        return retour;
    }

    private boolean enregistrerNumeroCarte(int numeroClient, String numCarteCredit) {
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "UPDATE voyageurs SET carteCredit = '"+numCarteCredit+"' WHERE id = '"+numeroClient+"'";
            dbsql.update(request);
            dbsql.Disconnect();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private boolean payerReservation(int idReservation) {
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "UPDATE reservationsChambre SET paye = '1' WHERE numeroReservation = '"+idReservation+"'";
            dbsql.update(request);
            dbsql.Disconnect();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private boolean dateDepassee(int idReservation) {
        boolean retour = false;
        String dateArrivee = null;
        String nbNuit = null;
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT dateArrivee, nbNuit FROM reservationschambre where numeroReservation = '"+ idReservation +"'";
            Object tuples = dbsql.select(request);
            dateArrivee = dbsql.extract(tuples, 1, "dateArrivee");
            nbNuit = dbsql.extract(tuples, 1, "nbNuit");
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (IOException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        String dateFin = getDateFin(dateArrivee, nbNuit);

        if(dateInferieurToday(dateFin)){
            return true;
        }else{
            return false;
        }
    }

    private boolean dateInferieurToday(String dateFin) {
        try {
            Date today = new Date();
            Date dateReservation = new SimpleDateFormat("dd/MM/yyyy").parse(dateFin);
            if(dateReservation.before(today)){
                return true;
            }else{
                return false;
            }
        } catch (ParseException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private boolean deleteReservation(int idReservation) {
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "DELETE FROM reservationschambre WHERE numeroReservation = '"+idReservation+"'";
            dbsql.update(request);
            dbsql.Disconnect();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private LinkedList<String> getAllReservations() {
        LinkedList<String> listeReservation = new LinkedList<>();
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT numeroChambre, dateArrivee, client FROM reservationschambre";
            ResultSet tuples = (ResultSet) dbsql.select(request);
            while(tuples.next()){
                int numeroChambre = tuples.getInt("numeroChambre");
                String dateArrivee = tuples.getString("dateArrivee");
                int client = tuples.getInt("client");
                String nomClient = getNomClient(client);
                String message = dateArrivee + " => " + numeroChambre + " => " + nomClient;
                listeReservation.add(message);
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
        return listeReservation;
    }

    private LinkedList<byte[]> cryptListeReservation(LinkedList<String> listeReservation) {
        LinkedList<byte[]> listeReservationCrypted = new LinkedList<>();
        for(String message: listeReservation){
            byte[] messageCrypted = cryptageServer.crypt(Cryptage.DES, message.getBytes(), cleSession);
            listeReservationCrypted.add(messageCrypted);
        }
        return listeReservationCrypted;
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
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nomClient;
    }

    public MyCertificate getMyCertificateClient() {
        return myCertificateClient;
    }

    public void setMyCertificateClient(MyCertificate myCertificateClient) {
        this.myCertificateClient = myCertificateClient;
    }

    private Orga verificationCarte(String nomClient, String numCarteCredit, int idReservation) {
        /********************************************************************/
        //Instanciation des sockets SSL si pas déja fait.
        /********************************************************************/
        initSocketSSL();
        PacketComSSL packetVerifCarte = null;
        PacketComSSL packetReponseVerif = null;

        Object[] infos = {nomClient, numCarteCredit, idReservation};

        /********************************************************************/
        //Verification chez VilVisa
        /********************************************************************/
        packetVerifCarte = new PacketComSSL(GIMP.PAY_FOR_CLIENT, (Object)infos);
        this.socketCreditVilvisa.send(packetVerifCarte);
        try {
            packetReponseVerif = this.socketCreditVilvisa.receive();
            if(packetReponseVerif.getType().equals(GIMP.VERIF_CARD_SUCCESSFULL)){
                return RMP.Orga.VILVISA;
            }
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        }

        /********************************************************************/
        //Verification chez MasterKuty
        /********************************************************************/
        packetVerifCarte = new PacketComSSL(GIMP.PAY_FOR_CLIENT, (Object)infos);
        this.socketCreditMasterKuty.send(packetVerifCarte);
        try {
            packetReponseVerif = this.socketCreditMasterKuty.receive();
            if(packetReponseVerif.getType().equals(GIMP.VERIF_CARD_SUCCESSFULL)){
                return RMP.Orga.MASTERKUTY;
            }
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        }

        return RMP.Orga.NONE;
    }

    private void initSocketSSL() {
        File fichierKsSSL = new File(this.pathKsSSL);
        String password = "lolilol";
        KeyStore keystoreSSL = getKeystore(fichierKsSSL, password);
        initMyKeysSSL(keystoreSSL, password);
        Commun.MyCertificateSSL myCertificateSSL = new MyCertificateSSL(myKeysSSL.getCertificate());
        myCertificateSSL.setPassword(password);
        myCertificateSSL.setKeystore(keystoreSSL);
        myCertificateSSL.setPrivateKey(myKeysSSL.getClePrivee());

        MyCertificateSSL myCertif = new MyCertificateSSL(myCertificateClient.getCertificate());
        myCertif.setPrivateKey(myCertificateClient.getPrivateKey());

        /********************************************************************/
        //Credit VilVisa
        /********************************************************************/
        if(this.socketCreditVilvisa == null){
            this.socketCreditVilvisa = new GIMPNetworkClientSSL(this.hostCredit, this.portCreditVilVisa, myCertif, myCertificateSSL);
        }

        /********************************************************************/
        //Credit MasterKuty
        /********************************************************************/
        if(this.socketCreditMasterKuty == null){
            this.socketCreditMasterKuty = new GIMPNetworkClientSSL(this.hostCredit, this.portCreditMasterKuty, myCertif, myCertificateSSL);
        }


        /********************************************************************/
        //Banque VilVisa
        /********************************************************************/
        /*if(this.socketBanqueVilVisa == null){
            this.socketBanqueVilVisa = new MAMPNetworkClientSSL(this.hostBanque, this.portBanqueVilVisa, myCertif, myCertificateSSL);
        }*/


        /********************************************************************/
        //Credit MasterKuty
        /********************************************************************/
        /*if(this.socketBanqueMasterKuty == null){
            this.socketBanqueMasterKuty = new MAMPNetworkClientSSL(this.hostBanque, this.portBanqueMasterKuty, myCertif, myCertificateSSL);
        }*/


    }

    private KeyStore getKeystore(File pathKs, String password) {
        KeyStore ks = null;
        try {
            ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(pathKs), password.toCharArray());
        } catch (KeyStoreException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ks;
    }

    private void initMyKeysSSL(KeyStore keystoreSSL, String password) {
        if(myKeysSSL == null){
            myKeysSSL = new MyKeys();
            try {
                myKeysSSL.setCertificate((X509Certificate)keystoreSSL.getCertificate("client"));
                myKeysSSL.setClePrivee((PrivateKey)keystoreSSL.getKey("client", password.toCharArray()));
                myKeysSSL.setClePublic(myKeysSSL.getCertificate().getPublicKey());
            } catch (KeyStoreException ex) {
                Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnrecoverableKeyException ex) {
                Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private int getNbNuit(int idReservation) {
        String nbNuit = null;
        try{
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT nbNuit from reservationschambre where numeroReservation = '"+idReservation+"'";
            Object tuples = dbsql.select(request);
            nbNuit = dbsql.extract(tuples, 1, "nbNuit");
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (IOException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(nbNuit == null){
            return 0;
        }else{
            return Integer.parseInt(nbNuit);
        }
    }

    private PacketComSSL effectuerPayement(Orga orga, int somme, String nomClient, int idReservation, String numCompteInpresHollidays) {
        PacketComSSL retour = null;
        Object[] infos = {somme, nomClient, idReservation, numCompteInpresHollidays};
        PacketComSSL packet = new PacketComSSL(MAMP.TRANSFER_POGN, (Object)infos);
        if(orga == Orga.VILVISA){
            try {
                this.socketBanqueVilVisa.send(packet);
                retour = this.socketBanqueVilVisa.receive();
            } catch (Exception ex) {
                Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                this.socketBanqueMasterKuty.send(packet);
                retour = this.socketBanqueMasterKuty.receive();
            } catch (Exception ex) {
                Logger.getLogger(RMP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return retour;
    }
}
