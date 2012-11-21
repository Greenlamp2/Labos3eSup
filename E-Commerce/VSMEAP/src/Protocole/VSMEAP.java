/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocole;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VSMEAP {
    public static String LOGINHEAD = "LOGINHEAD";
    public static String DOWNPROD = "DOWNPROD";
    public static String DOWNPROD_OUI = "DOWNPROD_OUI";
    public static String UPMENU = "UPMENU";
    public static String SENDMENU = "SENDMENU";
    public static String LOGINHEAD_OK = "LOGINHEAD_OK";
    public static String UPMENU_OUI = "UPMENU_OUI";
    public static String ERROR = "ERROR";
    public static String LOGINVILLAGE = "LOGINVILLAGE";
    public static String DOWNMENU = "DOWNMENU";
    public static String DOWNMENU_OUI = "DOWNMENU_OUI";
    public static String LOGINVILLAGE_OK = "LOGINVILLAGE_OK";
    public static String WRONGMENU = "WRONGMENU";

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
        if (type.equals(VSMEAP.LOGINHEAD)) {
            String[] infos = (String[]) contenu;
            String login = infos[0];
            String password = infos[1];
            if (gestionLoginHead(login, password)) {
                PacketCom packetRetour = new PacketCom(VSMEAP.LOGINHEAD_OK, "LOGINHEAD_OK");
                return packetRetour;
            }else{
                return new PacketCom(VSMEAP.ERROR, "ERROR");
            }
        } else if (type.equals(VSMEAP.DOWNPROD)) {
            LinkedList<String> listeProduitsEntree = getListeProduitsEntree();
            LinkedList<String> listeProduitsPlat = getListeProduitsPlat();
            LinkedList<String> listeProduitsDessert = getListeProduitsDessert();
            Object[] listeProduits = {listeProduitsEntree, listeProduitsPlat, listeProduitsDessert};
            PacketCom packetRetour = new PacketCom(VSMEAP.DOWNPROD_OUI, listeProduits);
            return packetRetour;
        } else if (type.equals(VSMEAP.UPMENU)) {
            Object[] infos = (Object[]) contenu;
            byte[] xml = (byte[]) infos[0];
            String nomMenu = (String) infos[1];
            createXML(xml, nomMenu);
            PacketCom packetRetour = new PacketCom(VSMEAP.UPMENU_OUI, "UPMENU_OUI");
            return packetRetour;
        } else if (type.equals(VSMEAP.SENDMENU)) {
            return null;
        } else if (type.equals(VSMEAP.LOGINVILLAGE)) {
            String[] infos = (String[]) contenu;
            String login = infos[0];
            String password = infos[1];
            if (gestionLoginVillage(login, password)) {
                PacketCom packetRetour = new PacketCom(VSMEAP.LOGINVILLAGE_OK, "LOGINVILLAGE_OK");
                return packetRetour;
            }else{
                return new PacketCom(VSMEAP.ERROR, "ERROR");
            }
        } else if (type.equals(VSMEAP.DOWNMENU)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                FileInputStream fis = new FileInputStream(new File("menu.xml"));
                byte[] buffer = new byte[512*1024];
                int readCnt = fis.read(buffer);
                while(0 < readCnt){
                    baos.write(buffer, 0, readCnt);
                    readCnt = fis.read(buffer);
                }
                fis.close();
            } catch (Exception ex) {
                Logger.getLogger(VSMEAP.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new PacketCom(VSMEAP.DOWNMENU_OUI, (Object)baos.toByteArray());
        }else if(type.equals(VSMEAP.WRONGMENU)){
            String message = (String)contenu;
            System.out.println("Type de problème lors de la validation: " + message);
            return null;
        }else {
            return new PacketCom(VSMEAP.ERROR, "ERROR");
        }
    }

    private PacketCom traiterPacketClientSide(PacketCom packet) {
        String type = packet.getType();
        Object contenu = packet.getObjet();
        System.out.println("Reçu: " + type);
        if (type.equals(VSMEAP.LOGINHEAD_OK)) {
            return packet;
        }else if (type.equals(VSMEAP.UPMENU_OUI)) {
            return packet;
        }else if (type.equals(VSMEAP.LOGINVILLAGE_OK)) {
            return packet;
        }else if (type.equals(VSMEAP.DOWNPROD_OUI)) {
            return packet;
        }else if (type.equals(VSMEAP.DOWNMENU_OUI)) {
            return packet;
        }else {
            PacketCom packetReponse = new PacketCom(VSMEAP.ERROR, "ERROR");
            return packetReponse;
        }
    }

    private boolean gestionLoginHead(String login, String password) {
        if(login.equals("admin") && password.equals("admin")){
            return true;
        }else{
            return false;
        }
    }

    private void createXML(byte[] xml, String nomMenu) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("menu.xml");
            fos.write(xml);
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(VSMEAP.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(VSMEAP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private LinkedList<String> getListeProduitsEntree() {
        LinkedList<String> listeProduitsEntree = new LinkedList<>();
        listeProduitsEntree.add("Concombre");
        listeProduitsEntree.add("Fromage à tartiner");
        listeProduitsEntree.add("Piment d'espelette");
        listeProduitsEntree.add("Ciboulette");
        listeProduitsEntree.add("Yaourt");
        listeProduitsEntree.add("Gouse d'ail");
        listeProduitsEntree.add("Crackers");
        listeProduitsEntree.add("Saumon fumé");
        listeProduitsEntree.add("Jus de citron");
        listeProduitsEntree.add("Oeufs");
        return listeProduitsEntree;
    }

    private LinkedList<String> getListeProduitsPlat() {
        LinkedList<String> listeProduitsPlat = new LinkedList<>();
        listeProduitsPlat.add("Escalope");
        listeProduitsPlat.add("Patates");
        listeProduitsPlat.add("Crème fraîche");
        listeProduitsPlat.add("Poulet");
        listeProduitsPlat.add("Oignon");
        listeProduitsPlat.add("Courgette");
        listeProduitsPlat.add("Chou-Fleur");
        listeProduitsPlat.add("Beurre");
        listeProduitsPlat.add("Piment de Cayenne");
        listeProduitsPlat.add("Pomme");
        return listeProduitsPlat;
    }

    private LinkedList<String> getListeProduitsDessert() {
        LinkedList<String> listeProduitsDessert = new LinkedList<>();
        listeProduitsDessert.add("Chocolat noir");
        listeProduitsDessert.add("Oeuf");
        listeProduitsDessert.add("Sucre");
        listeProduitsDessert.add("Beurre");
        listeProduitsDessert.add("Farine");
        listeProduitsDessert.add("Sucre roux");
        listeProduitsDessert.add("Sucre vanillé");
        listeProduitsDessert.add("Mascarpone");
        listeProduitsDessert.add("Nutella");
        listeProduitsDessert.add("Poudre de cacao");
        return listeProduitsDessert;
    }

    private boolean gestionLoginVillage(String login, String password) {
        if(login.equals("admin") && password.equals("admin")){
            return true;
        }else{
            return false;
        }
    }
}
