/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Serveur;

import Protocole.NetworkServerSSL;
import Protocole.TraitementPacket;
import Securite.MyCertificateSSL;


public class ServeurPool implements Runnable{
    NetworkServerSSL reseau;
    int port;
    PoolThread poolThread;
    MyCertificateSSL myCertificate_no_ssl;
    MyCertificateSSL myCertificate_ssl;

    ServeurPool(int port, MyCertificateSSL myCertificate_no_ssl, MyCertificateSSL myCertificate_ssl) {
        System.out.println("Mise à l'écoute sur le port: " + port);
        this.myCertificate_no_ssl = myCertificate_no_ssl;
        this.myCertificate_ssl = myCertificate_ssl;
        reseau = new NetworkServerSSL(port, this.myCertificate_no_ssl, this.myCertificate_ssl);
        poolThread = new PoolThread(3);
    }

    @Override
    public void run() {
        if(reseau.isConnected()){
            boolean goOn = true;
            int nbClient = 0;
            while(goOn){
                goOn = reseau.accept();
                System.out.println("Nouveau client !");
                TraitementPacket traitement = new TraitementPacket(new NetworkServerSSL(reseau.getSocketClient(), this.myCertificate_no_ssl, this.myCertificate_ssl));
                poolThread.assign(traitement);
                nbClient++;
            }
            reseau.disconnect();
        }else{
            System.out.println("Réseau non démarré");
        }
    }

}
