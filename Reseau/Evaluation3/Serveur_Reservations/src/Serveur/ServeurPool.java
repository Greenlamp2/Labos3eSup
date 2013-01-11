/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Serveur;

import Helpers.EasyFile;
import Protocole.NetworkServer;
import Protocole.TraitementPacket;
import Securite.MyCertificate;
import java.security.KeyStore;


public class ServeurPool implements Runnable{
    NetworkServer reseau;
    int port;
    PoolThread poolThread;
    MyCertificate myCertificate;

    /*ServeurPool() {
        port = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Reservations", "PORT_VOYAGEURS"));
        System.out.println("Mise à l'écoute sur le port: " + port);
        reseau = new NetworkServer(port);
        poolThread = new PoolThread(3);
    }*/

    ServeurPool(int port, MyCertificate myCertificate) {
        System.out.println("Mise à l'écoute sur le port: " + port);
        this.myCertificate = myCertificate;
        reseau = new NetworkServer(port, myCertificate);
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
                TraitementPacket traitement = new TraitementPacket(new NetworkServer(reseau.getSocketClient(), myCertificate));
                poolThread.assign(traitement);
                nbClient++;
            }
            reseau.disconnect();
        }else{
            System.out.println("Réseau non démarré");
        }
    }

}
