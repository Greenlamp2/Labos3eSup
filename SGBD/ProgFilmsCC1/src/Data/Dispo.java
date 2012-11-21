/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import java.util.LinkedList;


public class Dispo {
    private int idMovie;
    private String name;
    private LinkedList<Integer> idCopies;
    private int duree;

    public Dispo(){
        idMovie = 0;
        name = null;
        idCopies = new LinkedList<>();
        duree = 0;
    }

    public Dispo(int idMovie, String name, LinkedList<Integer> idCopies, int duree){
        this.setIdMovie(idMovie);
        this.setName(name);
        this.setIdCopies(idCopies);
        this.setDuree(duree);
    }

    public void addCopie(int idCopie){
        this.idCopies.add(idCopie);
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Integer> getIdCopies() {
        return idCopies;
    }

    public void setIdCopies(LinkedList<Integer> idCopies) {
        this.idCopies = idCopies;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
}