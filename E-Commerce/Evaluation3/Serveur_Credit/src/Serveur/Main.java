/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Serveur;

import Helpers.EasyFile;
import Securite.MyCertificateSSL;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static void main(String args[]) {
        KeyStore ks = null;
        KeyStore ks_SSL = null;

        MyCertificateSSL myCertificate_no_ssl = new MyCertificateSSL();
        MyCertificateSSL myCertificate_ssl = new MyCertificateSSL();

        String pathKeystoreServeurNoSSl = EasyFile.getConfig("Configs_Serveur_Credit", "ADRESSE_KS_SERVEUR_NO_SSL");
        String pathKeystoreServeurSSl = EasyFile.getConfig("Configs_Serveur_Credit", "ADRESSE_KS_SERVEUR_SSL");

        File fichierKeyStoreNoSSL = new File(pathKeystoreServeurNoSSl);
        File fichierKeyStoreSSL = new File(pathKeystoreServeurSSl);

        try {
            //No SSL
            ks = KeyStore.getInstance("PKCS12", "BC");
            String passKs = "lolilol";
            ks.load(new FileInputStream(fichierKeyStoreNoSSL), passKs.toCharArray());
            ((X509Certificate) ks.getCertificate("server")).checkValidity();

            myCertificate_no_ssl.setCertificate((X509Certificate)ks.getCertificate("server"));
            myCertificate_no_ssl.setPrivateKey((PrivateKey)ks.getKey("server", passKs.toCharArray()));
            myCertificate_no_ssl.setPassword(passKs);
            myCertificate_no_ssl.setKeystore(ks);


            //SSL
            ks_SSL = KeyStore.getInstance("JKS");
            String passKs_SSL = "lolilol";
            ks_SSL.load(new FileInputStream(fichierKeyStoreSSL), passKs_SSL.toCharArray());
            ((X509Certificate) ks_SSL.getCertificate("serveur")).checkValidity();

            myCertificate_ssl.setCertificate((X509Certificate)ks_SSL.getCertificate("serveur"));
            myCertificate_ssl.setPrivateKey((PrivateKey)ks_SSL.getKey("serveur", passKs.toCharArray()));
            myCertificate_ssl.setPassword(passKs_SSL);
            myCertificate_ssl.setKeystore(ks_SSL);


        } catch (KeyStoreException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }catch (CertificateExpiredException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateNotYetValidException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        int portCreditVilVisa = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Credit", "PORT_CREDIT_VILVISA"));
        Thread MyThread1 = new Thread(new ServeurPool(portCreditVilVisa, myCertificate_no_ssl, myCertificate_ssl));
        MyThread1.start();

        int portCreditMasterKuty = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Credit", "PORT_CREDIT_MASTERKUTY"));
        Thread MyThread2 = new Thread(new ServeurPool(portCreditMasterKuty, myCertificate_no_ssl, myCertificate_ssl));
        MyThread2.start();
    }
}
