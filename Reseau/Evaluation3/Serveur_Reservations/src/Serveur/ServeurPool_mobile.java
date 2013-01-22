/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Serveur;

import Protocole.NetworkServerMobile;
import Protocole.TraitementPacketMobile;
import Securite.MyCertificate;


public class ServeurPool_mobile implements Runnable{
    NetworkServerMobile reseau;
    int port;
    PoolThread poolThread;
    MyCertificate myCertificate;

    /*ServeurPool() {
        port = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Reservations", "PORT_VOYAGEURS"));
        System.out.println("Mise à l'écoute sur le port: " + port);
        reseau = new NetworkServer(port);
        poolThread = new PoolThread(3);
    }*/

    ServeurPool_mobile(int port, MyCertificate myCertificate) {
        System.out.println("Mise à l'écoute sur le port: " + port);
        this.myCertificate = myCertificate;
        reseau = new NetworkServerMobile(port, myCertificate);
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
                TraitementPacketMobile traitement = new TraitementPacketMobile(new NetworkServerMobile(reseau.getSocketClient(), myCertificate));
                poolThread.assign(traitement);
                nbClient++;
            }
            reseau.disconnect();
        }else{
            System.out.println("Réseau non démarré");
        }
    }

}
