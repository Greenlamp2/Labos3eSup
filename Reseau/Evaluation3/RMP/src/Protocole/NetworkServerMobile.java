/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Protocole;

import Securite.MyCertificate;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NetworkServerMobile {
    private ServerSocket socketServeur;
    private Socket socketClient;
    RMP protocole;
    boolean connected;
    MyCertificate myCertificate;

    public NetworkServerMobile(int port, MyCertificate myCertificate){
        this.myCertificate = myCertificate;
        protocole = new RMP(myCertificate, 123, true);
        try {
            socketServeur = new ServerSocket(port);
            connected = true;
            System.out.println("Socket créer");
        } catch (IOException ex) {
            Logger.getLogger(NetworkServerMobile.class.getName()).log(Level.SEVERE, null, ex);
            connected = false;
        }
    }

    public NetworkServerMobile(Socket socket, MyCertificate myCertificate){
        protocole = new RMP(myCertificate, 123, true);
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
            Logger.getLogger(NetworkServerMobile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void disconnect(){
        if(!socketServeur.isClosed()){
            try {
                socketServeur.close();
                System.out.println("Déconnection réussie");
            } catch (IOException ex) {
                Logger.getLogger(NetworkServerMobile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void disconnectClient(){
        if(!socketClient.isClosed()){
            try {
                getSocketClient().close();
                System.out.println("Déconnection client réussie");
            } catch (IOException ex) {
                Logger.getLogger(NetworkServerMobile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isConnected(){
        return connected;
    }

    public void send(PacketCom packet){
        if(this.isConnected()){
            try {
                String message = packetToString(packet);
                OutputStream os = this.socketClient.getOutputStream();
                DataOutputStream out = new DataOutputStream(os);
                out.writeUTF(message);
            } catch (IOException ex) {
                Logger.getLogger(NetworkServerMobile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("Socket non connectée");
        }
    }

    public PacketCom receive() throws Exception{
        InputStream is = null;
        try{
            is = this.socketClient.getInputStream();
            DataInputStream in = new DataInputStream(is);
            String message = in.readUTF();
            PacketCom packet = stringToPacket(message);
            PacketCom MessageToClient = protocole.messageFromClient(packet);
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

    public String packetToString(PacketCom packet){
        String type = packet.getType();
        String msg = "";
        msg += type;
        msg += "#";
        if(packet.getObjet().getClass().isInstance(Object[].class)){
            Object[] data = (Object[]) packet.getObjet();
            for(Object elm : data){
                if(elm.getClass().isInstance(int.class)){
                    int var = (int)elm;
                    String varString = String.valueOf(var);
                    msg += varString;
                }else if(elm.getClass().isInstance(String.class)){
                    String var = (String)elm;
                    msg += var;
                }
                msg += ";";
            }
        }else if(packet.getObjet().getClass().isInstance(String.class)){
            String elm = (String) packet.getObjet();
            msg += elm;
            msg += ";";
        }else if(packet.getObjet().getClass().isInstance(int.class)){
            int elm = (int) packet.getObjet();
            msg += String.valueOf(elm);
            msg += ";";
        }else if(packet.getObjet().getClass().isInstance(Integer.class)){
            int elm = (int) packet.getObjet();
            msg += String.valueOf(elm);
            msg += ";";
        }else{
            try{
                int num = (int) packet.getObjet();
                msg += String.valueOf(num);
                msg += ";";
            }catch(Exception ex){

            }
        }
        return msg;
    }

    public PacketCom stringToPacket(String message){
        String[] split1 = message.split("#");
        String type = split1[0];
        if(split1.length > 1){
            String values = split1[1];
            String[] split2 = values.split(";");
            Vector<String> vec = new Vector<String>();
            for(String elm : split2){
                vec.add(elm);
            }
            Object[] infos = vec.toArray();
            PacketCom packet = new PacketCom(type, (Object)infos);
            packet = corrigerPacket(packet);
            return packet;
        }else{
            return new PacketCom(type, null);
        }
    }

    private PacketCom corrigerPacket(PacketCom packet) {
        String type = packet.getType();
        Object contenu = (Object) packet.getObjet();
        PacketCom packetRetour = packet;
        if(type.equals("LOGIN_VOY_NEXT_STEP")){
            Object[] data = (Object[]) contenu;
            String login = (String)data[0];
            String digest = (String)data[1];
            String number = (String)data[2];
            int numberCorrect = Integer.parseInt(number);
            byte[] digestCorrect = Base64.decode(digest);
            Object[] retour = {login, digestCorrect, numberCorrect};
            packetRetour.setObjet((Object)retour);
        }/*else if(type.equals("BROOM")){
            Object[] data = (Object[]) contenu;
            String categorie = (String)data[0];
            String typeChambre = (String)data[1];
            String date = (String)data[2];
            String nbNuit = (String)data[3];

        }*/
        return packetRetour;
    }
}
