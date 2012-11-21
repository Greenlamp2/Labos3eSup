/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Serveur;

import Helpers.EasyFile;
import Protocole.NetworkServer;
import Protocole.PacketCom;


public class ServeurMono implements Runnable{
    NetworkServer reseau;
    int port;

    ServeurMono() {
        port = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Restaurants", "PORT_HEAD"));
        System.out.println("Mise à l'écoute sur le port: " + port);
        reseau = new NetworkServer(port);
    }

    @Override
    public void run() {
        if(reseau.isConnected()){
            boolean goOn = true;
            boolean clientConnected = true;
            while(goOn){
                goOn = reseau.accept();
                while(clientConnected){
                    try{
                        PacketCom messageToClient = reseau.receive();
                        if(messageToClient != null){
                            reseau.send(messageToClient);
                        }
                    }catch(Exception ex){
                        System.out.println("Remise à l'écoute");
                        clientConnected = false;
                    }
                }
            }
        }
    }
}
