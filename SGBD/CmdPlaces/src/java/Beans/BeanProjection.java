/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BeanProjection implements Serializable{
private Date dateHeureProjection;
    private int numeroSalle;
    private int idCopie;
    private String dateHeureString;
    private int idMovie;
    private String nameMovie;
    private int duree;
    private BeanMovie beanMovie;

    public BeanProjection(){
        dateHeureProjection = null;
        numeroSalle = 0;
        idCopie = 0;
        dateHeureString = null;
        idMovie = 0;
        nameMovie = null;
        duree = 0;
    }

    public BeanProjection(Date dateHeureProjection, int numeroSalle, int idCopie, int idMovie, String nameMovie, int duree){
        this.dateHeureString = null;
        this.setDateHeureProjection(dateHeureProjection);
        this.setNumeroSalle(numeroSalle);
        this.setIdCopie(idCopie);
        this.setIdMovie(idMovie);
        this.setNameMovie(nameMovie);
        this.setDuree(duree);
    }

    public Date getDateHeureProjection() {
        return dateHeureProjection;
    }

    public void setDateHeureProjection(Date dateHeureProjection) {
        this.dateHeureProjection = dateHeureProjection;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        setDateHeureString(sdf.format(dateHeureProjection));
    }

    public String getMonthYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        return sdf.format(dateHeureProjection);
    }

    public String getDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(dateHeureProjection);
    }

    public int getNumeroSalle() {
        return numeroSalle;
    }

    public void setNumeroSalle(int numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    public int getIdCopie() {
        return idCopie;
    }

    public void setIdCopie(int idCopie) {
        this.idCopie = idCopie;
    }

    public String getDateHeureString() {
        return dateHeureString;
    }

    public void setDateHeureString(String dateHeureString) {
        this.dateHeureString = dateHeureString;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public BeanMovie getBeanMovie() {
        return beanMovie;
    }

    public void setBeanMovie(BeanMovie beanMovie) {
        this.beanMovie = beanMovie;
    }
}
