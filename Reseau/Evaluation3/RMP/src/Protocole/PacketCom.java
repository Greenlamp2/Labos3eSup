/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Protocole;

import java.io.Serializable;


public class PacketCom implements Serializable{
    private String type;
    private Object objet;

    public PacketCom(String type, Object objet){
        this.type = type;
        this.objet = objet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getObjet() {
        return objet;
    }

    public void setObjet(Object objet) {
        this.objet = objet;
    }
}
