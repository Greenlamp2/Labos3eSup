/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Protocole;

import java.util.logging.Level;
import java.util.logging.Logger;


public class TraitementPacket implements Runnable{
    private NetworkServer socket;
    public TraitementPacket(NetworkServer sock){
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

    public NetworkServer getSocket() {
        return socket;
    }

    public void setSocket(NetworkServer socket) {
        this.socket = socket;
    }

}
