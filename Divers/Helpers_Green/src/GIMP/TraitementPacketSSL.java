/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GIMP;

import Commun.PacketComSSL;


public class TraitementPacketSSL implements Runnable{
    private GIMPNetworkServerSSL socket;
    public TraitementPacketSSL(GIMPNetworkServerSSL sock){
        this.socket = sock;
        System.out.println("Démarrage du thread");
    }
    @Override
    public void run() {
        int i = 0;
        while(socket.isConnected()){
            try{
                PacketComSSL MessageToClient = socket.receive();
                if(MessageToClient != null){
                    socket.send(MessageToClient);
                }
            }catch(Exception ex){
                return;
            }
        }
    }

    public GIMPNetworkServerSSL getSocket() {
        return socket;
    }

    public void setSocket(GIMPNetworkServerSSL socket) {
        this.socket = socket;
    }
}
