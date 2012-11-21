/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;


public class BeanCaddy implements Serializable{
    private LinkedList<BeanReservation> listeReservation;

    public BeanCaddy(){
        listeReservation = new LinkedList<BeanReservation>();
    }

    public void addReservation(String idReservation, int numeroChambre, int prixHtva, String date, String heure){
        BeanReservation beanReservation = new BeanReservation(idReservation, numeroChambre, date, heure);
        beanReservation.setPrixHtva(prixHtva);
        listeReservation.add(beanReservation);
    }

    public void removeReservation(String idReservation){
        int cpt = 0;
        for(BeanReservation beanReservation: listeReservation){
            if(beanReservation.getIdReservation().equals(idReservation)){
                listeReservation.remove(cpt);
            }
            cpt++;
        }
    }

    public void viderCaddy(){
        int taille = listeReservation.size();
        for(int i=0; i<taille; i++){
            listeReservation.remove(0);
        }
    }

    public void removeReservation(int numeroChambre){
        int cpt = 0;
        for(BeanReservation beanReservation: listeReservation){
            if(beanReservation.getNumeroChambre() == numeroChambre){
                listeReservation.remove(cpt);
            }
            cpt++;
        }
    }

    public LinkedList<BeanReservation> getListeReservation() {
        return listeReservation;
    }

    public void setListeReservation(LinkedList<BeanReservation> listeReservation) {
        this.listeReservation = listeReservation;
    }

    public int getPrixTotal(){
        int total = 0;
        for(BeanReservation beanReservation: listeReservation){
            total += beanReservation.getPrixHtva();
        }
        return total;
    }
}
