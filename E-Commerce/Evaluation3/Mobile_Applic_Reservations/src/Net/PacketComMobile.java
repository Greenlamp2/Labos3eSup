/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Net;

import java.util.Vector;


public class PacketComMobile {
    private String type;
    private Object contenu;

    public PacketComMobile(String type, Object contenu){
        this.setType(type);
        this.setContenu(contenu);
    }

    public static String getMessage(PacketComMobile packet){
        String message = null;
        message += packet.getType();
        message += "#";
        if(packet.getContenu() != null){
            Object[] data = (Object[]) packet.getContenu();
            int nbElement = data.length;
            for(int i=0; i<nbElement; i++){
                String elm = (String) data[i];
                message += elm;
                message += ";";
            }
        }else{
            packet.setContenu(null);
        }
        return message;
    }

    public static PacketComMobile getPacket(String message){
        PacketComMobile packet = null;
        String[] split1 = PacketComMobile.split(message, "#");
        String type = split1[0];
        String infos = split1[1];
        String[] split2 = PacketComMobile.split(infos, ";");
        int count = split2.length;
        Object[] data =  new Object[count];
        for(int i=0; i<count; i++){
            data[i] = split2[i];
        }
        packet.setType(type);
        packet.setContenu((Object) data);
        return packet;
    }

    private static String[] split(String original, String delimiter) {
        Vector nodes = new Vector();
        String separator = delimiter;
        // Parse nodes into vector
        int index = original.indexOf(separator);
        while(index>=0) {
            nodes.addElement( original.substring(0, index) );
            original = original.substring(index+separator.length());
            index = original.indexOf(separator);
        }
        nodes.addElement( original );

        String[] result = new String[ nodes.size() ];
        if( nodes.size()>0 ) {
            for(int loop=0; loop<nodes.size(); loop++){
                result[loop] = (String)nodes.elementAt(loop);
                System.out.println(result[loop]);
            }
        }
        return result;
    }

    public Object getContenu() {
        return contenu;
    }

    public void setContenu(Object contenu) {
        this.contenu = contenu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
