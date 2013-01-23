/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Serveur;

import Commun.MyCertificateSSL;
import GIMP.GIMPNetworkServerSSL;
import GIMP.TraitementPacketSSL;




public class ServeurPool implements Runnable{
    GIMPNetworkServerSSL reseau;
    int port;
    PoolThread poolThread;
    MyCertificateSSL myCertificate_no_ssl;
    MyCertificateSSL myCertificate_ssl;

    ServeurPool(int port, MyCertificateSSL myCertificate_no_ssl, MyCertificateSSL myCertificate_ssl, String orga) {
        System.out.println("Mise à l'écoute sur le port: " + port);
        this.myCertificate_no_ssl = myCertificate_no_ssl;
        this.myCertificate_ssl = myCertificate_ssl;
        reseau = new GIMPNetworkServerSSL(port, this.myCertificate_no_ssl, this.myCertificate_ssl, orga);
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
                TraitementPacketSSL traitement = new TraitementPacketSSL(new GIMPNetworkServerSSL(reseau.getSocketClient(), this.myCertificate_no_ssl, this.myCertificate_ssl, reseau.getOrga()));
                poolThread.assign(traitement);
                nbClient++;
            }
            reseau.disconnect();
        }else{
            System.out.println("Réseau non démarré");
        }
    }

}
