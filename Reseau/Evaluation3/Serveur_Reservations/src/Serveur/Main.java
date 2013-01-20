/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Serveur;

import Helpers.EasyFile;
import Securite.MyCertificate;
import java.awt.Color;
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
        MyCertificate myCertificate = new MyCertificate();
        int portVoyageurs = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Reservations", "PORT_VOYAGEURS"));
        String path = EasyFile.getConfig("Configs_Serveur_Reservations", "ADRESSE_KS_SERVEUR_NOSSL");
        File fichierKeyStore = new File(path);
        try {
            ks = KeyStore.getInstance("PKCS12", "BC");
            String passKs = "lolilol";
            
            ks.load(new FileInputStream(fichierKeyStore), passKs.toCharArray());
            myCertificate.setCertificate((X509Certificate) ks.getCertificate("server"));
            myCertificate.getCertificate().checkValidity();

            String passKeyStore = "lolilol";
            myCertificate.setPrivateKey((PrivateKey) ks.getKey("server", passKeyStore.toCharArray()));

        } catch (KeyStoreException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateExpiredException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateNotYetValidException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        Thread MyThread = new Thread(new ServeurPool(portVoyageurs, myCertificate));
        MyThread.start();
    }
}
