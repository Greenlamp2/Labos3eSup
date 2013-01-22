/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Net;

import Constants.Infos;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;


public class NetworkClientMobile {
    private SocketConnection socket = null;
    DataInputStream input;
    DataOutputStream output;

    public NetworkClientMobile(String host, int port){
        try {
            this.setSocket((SocketConnection) Connector.open(Infos.getUrl(host, port)));
            input = new DataInputStream(this.getSocket().openDataInputStream());
            output = new DataOutputStream(this.getSocket().openDataOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean disconnect(){
        try {
            this.getSocket().close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean isConnected(){
        if(this.getSocket() == null){
            return false;
        }else{
            return true;
        }
    }

    public void send(String message){
        try {
            output.writeUTF(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String receive(){
        String messageFromServer = null;
        try {
            messageFromServer = input.readUTF();
        } catch (IOException ex) {
            return null;
        }
        return messageFromServer;
    }

    public SocketConnection getSocket() {
        return socket;
    }

    public void setSocket(SocketConnection socket) {
        this.socket = socket;
    }
}
