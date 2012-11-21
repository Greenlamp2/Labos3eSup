/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.io.Serializable;
import java.util.LinkedList;


public class BeanHoraires implements Serializable{
    private LinkedList<BeanHoraire> listeHoraires;

    public BeanHoraires(){
        this.listeHoraires = new LinkedList<BeanHoraire>();
    }

    public void addHeure(int idMovie, String heure){
        boolean found = false;
        for(BeanHoraire horaire : listeHoraires){
            if(horaire.getIdMovie() == idMovie){
                horaire.getHeures().add(heure);
                found = true;
                break;
            }
        }
        if(!found){
            BeanHoraire horaire = new BeanHoraire();
            horaire.setIdMovie(idMovie);
            horaire.getHeures().add(heure);
            listeHoraires.add(horaire);
        }
    }

    public void addHeure(String jour, int idMovie, String heure){
        boolean found = false;
        for(BeanHoraire horaire : listeHoraires){
            if(horaire.getIdMovie() == idMovie){
                horaire.addHeure(jour, heure);
                found = true;
                break;
            }
        }
        if(!found){
            BeanHoraire horaire = new BeanHoraire();
            horaire.setIdMovie(idMovie);
            horaire.addHeure(jour, heure);
            listeHoraires.add(horaire);
        }
    }

    public LinkedList<BeanHoraire> getListeHoraires() {
        return listeHoraires;
    }

    public void setListeHoraires(LinkedList<BeanHoraire> listeHoraires) {
        this.listeHoraires = listeHoraires;
    }
}
