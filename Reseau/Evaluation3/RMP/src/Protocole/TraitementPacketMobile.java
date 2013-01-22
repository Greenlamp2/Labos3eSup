/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Protocole;


public class TraitementPacketMobile implements Runnable{
    private NetworkServerMobile socket;
    public TraitementPacketMobile(NetworkServerMobile sock){
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

    public NetworkServerMobile getSocket() {
        return socket;
    }

    public void setSocket(NetworkServerMobile socket) {
        this.socket = socket;
    }

}
