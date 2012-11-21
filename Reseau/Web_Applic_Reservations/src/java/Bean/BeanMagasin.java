/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bean;

import java.io.Serializable;
import java.util.LinkedList;


public class BeanMagasin implements Serializable{
    private LinkedList<BeanChambre> listeChambre;

    public BeanMagasin(){
        listeChambre = new LinkedList<BeanChambre>();
    }

    public BeanMagasin(LinkedList<BeanChambre> listeChambre){
        setListeChambre(listeChambre);
    }

    public int getCount(){
        return listeChambre.size();
    }

    public LinkedList<BeanChambre> getListeChambre() {
        return listeChambre;
    }

    public int getPrix(int numeroChambre){
        int prix = 0;
        for(BeanChambre chambre: listeChambre){
            if(chambre.getNumeroChambre() == numeroChambre){
                prix = chambre.getPrixHtva();
            }
        }
        return prix;
    }

    public void setListeChambre(LinkedList<BeanChambre> listeChambre) {
        this.listeChambre = listeChambre;
    }

    public void addChambre(BeanChambre beanChambre){
        listeChambre.add(beanChambre);
    }
}
