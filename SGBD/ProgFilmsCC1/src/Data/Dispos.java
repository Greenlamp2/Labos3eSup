/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import java.util.LinkedList;


public class Dispos {
    private LinkedList<Dispo> listeDispo;
    private LinkedList<Integer> sallesDispo;

    public Dispos(){
        listeDispo = new LinkedList<>();
        sallesDispo = new LinkedList<>();
    }

    public void addDispo(int idMovie, String name, LinkedList<Integer> idCopies, int duree){
        Dispo dispo = new Dispo(idMovie, name, idCopies, duree);
        this.getListeDispo().add(dispo);
    }

    public void addSalle(int numeroSalle){
        this.sallesDispo.add(numeroSalle);
    }

    public LinkedList<Integer> getSallesDispo() {
        return sallesDispo;
    }

    public void setSallesDispo(LinkedList<Integer> sallesDispo) {
        this.sallesDispo = sallesDispo;
    }

    public LinkedList<Dispo> getListeDispo() {
        return listeDispo;
    }

    public void setListeDispo(LinkedList<Dispo> listeDispo) {
        this.listeDispo = listeDispo;
    }
}