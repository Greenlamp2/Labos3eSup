/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole;

public class FOAMP {
    public static String LOGIN = "LOGIN";
    public static String LOGIN_OUI = "LOGIN_OUI";
    public static String LOGIN_NON = "LOGIN_NON";
    public static String BACTFUN = "BACTFUN";
    public static String BACTFUN_OUI = "BACTFUN_OUI";
    public static String BACTFUN_NON = "BACTFUN_NON";
    public static String ACKACTFUN = "ACKACTFUN";
    public static String ACKACTFUN_OUI = "ACKACTFUN_OUI";
    public static String ACKACTFUN_NON = "ACKACTFUN_NON";
    public static String BTREKFUN = "BTREKFUN";
    public static String BTREKFUN_OUI = "BTREKFUN_OUI";
    public static String BTREKFUN_NON = "BTREKFUN_NON";
    public static String ERROR = "ERROR";

    PacketCom messageFromClient(Object objet) {
        PacketCom packet = (PacketCom) objet;
        PacketCom messageToClient = traiterPacketServerSide(packet);
        return messageToClient;
    }

    PacketCom messageFromServer(Object objet) {
        PacketCom packet = (PacketCom) objet;
        PacketCom message = traiterPacketClientSide(packet);
        return message;
    }

    private PacketCom traiterPacketServerSide(PacketCom packet) {
        String type = packet.getType();
        System.out.println("type reçu: " + type);
        Object contenu = packet.getObjet();
        System.out.println("Reçu: " + type);
        if (type.equals(FOAMP.LOGIN)) {
            String[] infos = (String[]) contenu;
            String login = infos[0];
            String password = infos[1];
            if (gestionLogin(login, password)) {
                PacketCom packetRetour = new PacketCom(FOAMP.LOGIN_OUI, "LOGIN_OUI");
                return packetRetour;
            }else{
                return new PacketCom(FOAMP.LOGIN_NON, "LOGIN_NON");
            }
        } else if (type.equals(FOAMP.BACTFUN)) {
            return new PacketCom(FOAMP.ERROR, "ERROR");
        } else if (type.equals(FOAMP.ACKACTFUN)) {
            return new PacketCom(FOAMP.ERROR, "ERROR");
        } else if (type.equals(FOAMP.BTREKFUN)) {
            return new PacketCom(FOAMP.ERROR, "ERROR");
        }else{
            return new PacketCom(FOAMP.ERROR, "ERROR");
        }
    }

    private PacketCom traiterPacketClientSide(PacketCom packet) {
        String type = packet.getType();
        Object contenu = packet.getObjet();
        System.out.println("Reçu: " + type);
        if (type.equals(FOAMP.LOGIN_OUI)) {
            return packet;
        }else if (type.equals(FOAMP.LOGIN_NON)) {
            return packet;
        }else if (type.equals(FOAMP.BACTFUN_OUI)) {
            return packet;
        }else if (type.equals(FOAMP.BACTFUN_NON)) {
            return packet;
        }else if (type.equals(FOAMP.ACKACTFUN_OUI)) {
            return packet;
        }else if (type.equals(FOAMP.ACKACTFUN_NON)) {
            return packet;
        }else if (type.equals(FOAMP.BTREKFUN_OUI)) {
            return packet;
        }else if (type.equals(FOAMP.BTREKFUN_NON)) {
            return packet;
        }else {
            PacketCom packetReponse = new PacketCom(FOAMP.ERROR, "ERROR");
            return packetReponse;
        }
    }

    private boolean gestionLogin(String login, String password) {
        if(login.equals("admin") && password.equals("admin")){
            return true;
        }else{
            return false;
        }
    }

}
