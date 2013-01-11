/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Protocole;

import Securite.MyCertificate;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NetworkClient {
    private Socket socketClient;
    RMP protocole;
    MyCertificate myCertificate;

    public NetworkClient(String host, int port, MyCertificate myCertificate){
        this.myCertificate = myCertificate;
        protocole = new RMP(myCertificate);
        try {
            InetAddress ip = InetAddress.getByName(host);
            socketClient = new Socket(ip, port);
        } catch (Exception ex) {
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isConnected(){
        if(socketClient != null){
            return true;
        }else{
            return false;
        }
    }

    public void disconnect(){
        if(this.isConnected()){
            try {
                socketClient.close();
                socketClient = null;
                System.out.println("Déconnection réussie");
            } catch (IOException ex) {
                Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void send(PacketCom packet){
        if(this.isConnected()){
            try {
                OutputStream os = this.socketClient.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject((Object)packet);
            } catch (IOException ex) {
                Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("Socket non connectée");
        }
    }

    public PacketCom receive() throws Exception{
        try{
            InputStream is = this.socketClient.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            Object objet = (Object)ois.readObject();
            PacketCom MessageFromServer = protocole.messageFromServer(objet);
            return MessageFromServer;
        }catch(Exception ex){
            this.disconnect();
            throw (Exception)ex;
        }
    }

}
