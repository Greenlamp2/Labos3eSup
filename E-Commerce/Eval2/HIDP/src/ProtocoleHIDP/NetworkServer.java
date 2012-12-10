/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ProtocoleHIDP;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NetworkServer {
    private ServerSocket socketServeur;
    private Socket socketClient;
    HIDP protocole;
    boolean connected;

    public NetworkServer(int port){
        protocole = new HIDP();
        try {
            socketServeur = new ServerSocket(port);
            connected = true;
            System.out.println("Socket créer");
        } catch (IOException ex) {
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            connected = false;
        }
    }

    public NetworkServer(Socket socket){
        protocole = new HIDP();
        this.socketClient = socket;
        this.connected = true;
    }

    public boolean accept(){
        try {
            System.out.println("En attente d'un client");
            this.setSocketClient(socketServeur.accept());
            System.out.println("client connecté");
            return true;
        } catch (IOException ex) {
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void disconnect(){
        if(!socketServeur.isClosed()){
            try {
                socketServeur.close();
                System.out.println("Déconnection réussie");
            } catch (IOException ex) {
                Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void disconnectClient(){
        if(!socketClient.isClosed()){
            try {
                getSocketClient().close();
                System.out.println("Déconnection client réussie");
            } catch (IOException ex) {
                Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isConnected(){
        return connected;
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
            PacketCom MessageToClient = protocole.messageFromClient(objet);
            return MessageToClient;
        }catch(Exception ex){
            this.disconnectClient();
            throw (Exception)ex;
        }
    }

    public Socket getSocketClient() {
        return socketClient;
    }

    public void setSocketClient(Socket socketClient) {
        this.socketClient = socketClient;
    }

}
