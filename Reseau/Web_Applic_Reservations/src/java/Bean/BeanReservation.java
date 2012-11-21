/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bean;

import java.io.Serializable;


public class BeanReservation implements Serializable{
    private String idReservation;
    private int numeroChambre;
    private int prixHtva;
    private String date;
    private String heure;

    public BeanReservation(){
        idReservation = null;
        numeroChambre = -1;
        prixHtva = -1;
        date = null;
        heure = null;
    }

    public BeanReservation(String idReservation, int numeroChambre){
        setIdReservation(idReservation);
        setNumeroChambre(numeroChambre);
    }

    public BeanReservation(String idReservation, int numeroChambre, String date, String heure){
        setIdReservation(idReservation);
        setNumeroChambre(numeroChambre);
        setDate(date);
        setHeure(heure);
    }

    public String getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(String idReservation) {
        this.idReservation = idReservation;
    }

    public int getNumeroChambre() {
        return numeroChambre;
    }

    public void setNumeroChambre(int numeroChambre) {
        this.numeroChambre = numeroChambre;
    }

    public int getPrixHtva() {
        return prixHtva;
    }

    public void setPrixHtva(int prixHtva) {
        this.prixHtva = prixHtva;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }
}
