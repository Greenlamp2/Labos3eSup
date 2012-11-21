/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.io.Serializable;
import java.util.Date;


public class BeanCmdTicket implements Serializable{
    private int idMovie;
    private Date date;
    private int idCopie;
    private int numeroSalle;
    private int nbPlacesDispo;

    public BeanCmdTicket(){
        idCopie = 0;
        numeroSalle = 0;
        nbPlacesDispo = 0;
        idMovie = 0;
        date = null;
    }

    public BeanCmdTicket(int idCopie, int numeroSalle, int nbPlacesDispo, int idMovie, Date date){
        setIdCopie(idCopie);
        setNumeroSalle(numeroSalle);
        setNbPlacesDispo(nbPlacesDispo);
        setIdMovie(idMovie);
        setDate(date);
    }

    public int getIdCopie() {
        return idCopie;
    }

    public void setIdCopie(int idCopie) {
        this.idCopie = idCopie;
    }

    public int getNumeroSalle() {
        return numeroSalle;
    }

    public void setNumeroSalle(int numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    public int getNbPlacesDispo() {
        return nbPlacesDispo;
    }

    public void setNbPlacesDispo(int nbPlacesDispo) {
        this.nbPlacesDispo = nbPlacesDispo;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
