/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Protocole;

import java.util.logging.Level;
import java.util.logging.Logger;


public class TraitementPacket implements Runnable{
    private NetworkServerSSL socket;
    public TraitementPacket(NetworkServerSSL sock){
        this.socket = sock;
        System.out.println("Démarrage du thread");
    }
    @Override
    public void run() {
        int i = 0;
        while(socket.isConnected()){
            try{
                PacketCom MessageToClient = socket.receive();
                if(MessageToClient != null){
                    socket.send(MessageToClient);
                }
            }catch(Exception ex){
                return;
            }
        }
    }

    public NetworkServerSSL getSocket() {
        return socket;
    }

    public void setSocket(NetworkServerSSL socket) {
        this.socket = socket;
    }

}
