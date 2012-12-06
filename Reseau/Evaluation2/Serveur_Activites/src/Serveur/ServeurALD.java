/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Serveur;

import Helpers.EasyFile;
import Protocole.NetworkServer;
import Protocole.TraitementPacket;


public class ServeurALD implements Runnable{
    NetworkServer reseau;
    int port;

    ServeurALD() {
        port = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Restaurants", "PORT_MENU"));
        System.out.println("Mise à l'écoute sur le port: " + port);
        reseau = new NetworkServer(port);
    }

    @Override
    public void run() {
        if(reseau.isConnected()){
            boolean goOn = true;
            int nbClient = 0;
            while(goOn){
                goOn = reseau.accept();
                System.out.println("Nouveau client !");
                TraitementPacket traitement = new TraitementPacket(new NetworkServer(reseau.getSocketClient()));
                Thread threadTraitement = new Thread(traitement);
                threadTraitement.start();
                nbClient++;
            }
            reseau.disconnect();
        }else{
            System.out.println("Réseau non démarré");
        }
    }
}
