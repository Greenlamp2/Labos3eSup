/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;


public class BeanHoraire implements Serializable{
    private int idMovie;
    private LinkedList<String> heures;
    private Map<String, LinkedList<String>> agenda;
    private LinkedList<String> jours;

    public BeanHoraire(){
        idMovie = 0;
        heures = new LinkedList<String>();
        agenda = new TreeMap<String, LinkedList<String>>();
        jours = new LinkedList<String>();
    }

    public void addHeure(String day, String heure){
        if(agenda.containsKey(day)){
            agenda.get(day).add(heure);
        }else{
            LinkedList<String> heures = new LinkedList<String>();
            heures.add(heure);
            agenda.put(day, heures);
            jours.add(day);
            System.out.println("test");
        }
/*
        boolean found = false;
        for(String jour : jours){
            if(jour.equals(day)){
                found = true;
            }
        }
        if(!found){
            jours.add(day);
        }*/
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public LinkedList<String> getHeures() {
        return heures;
    }

    public void setHeures(LinkedList<String> heures) {
        this.heures = heures;
    }

    public Map<String, LinkedList<String>> getAgenda() {
        return agenda;
    }

    public void setAgenda(Map<String, LinkedList<String>> agenda) {
        this.agenda = agenda;
    }

    public LinkedList<String> getJours() {
        return jours;
    }

    public void setJours(LinkedList<String> jours) {
        this.jours = jours;
    }
}
