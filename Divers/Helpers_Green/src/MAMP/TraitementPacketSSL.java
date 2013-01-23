/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MAMP;

import Commun.PacketComSSL;


public class TraitementPacketSSL implements Runnable{
    private MAMPNetworkServerSSL socket;
    public TraitementPacketSSL(MAMPNetworkServerSSL sock){
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

    public MAMPNetworkServerSSL getSocket() {
        return socket;
    }

    public void setSocket(MAMPNetworkServerSSL socket) {
        this.socket = socket;
    }
}
