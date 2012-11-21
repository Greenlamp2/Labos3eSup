/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.io.Serializable;
import java.sql.Blob;


public class BeanMovieCaddy implements Serializable{
    private int id;
    private String nom;
    private Blob affiche;
    private int nbCopieDispo;
    private int popularite;
    private int frequenceProg;
    private int perenite;
    private int quantite;
    private int physique;


    public BeanMovieCaddy() {
        this.id = -1;
        this.nom = null;
        this.affiche = null;
        this.nbCopieDispo = -1;
        this.popularite = -1;
        this.frequenceProg = -1;
        this.perenite = -1;
        this.quantite = 0;
        physique = 1;
    }

    public BeanMovieCaddy(int id, String nom, Blob affiche, int nbCopieDispo, int popularite, int frequenceProg, int perenite, int quantite){
        setId(id);
        setNom(nom);
        setAffiche(affiche);
        setNbCopieDispo(nbCopieDispo);
        setPopularite(popularite);
        setFrequenceProg(frequenceProg);
        setPerenite(perenite);
        setQuantite(quantite);
        physique = 1;
        setPhysique(1);
    }

    public BeanMovieCaddy(BeanMovie beanMovie){
        setId(beanMovie.getId());
        setNom(beanMovie.getNom());
        setAffiche(beanMovie.getAffiche());
        setNbCopieDispo(beanMovie.getNbCopieDispo());
        setPopularite(beanMovie.getPopularite());
        setFrequenceProg(beanMovie.getFrequenceProg());
        setPerenite(beanMovie.getPerenite());
        this.quantite = 1;
        setPhysique(1);
    }

    public void addQuantite(int nb){
        this.quantite += nb;
    }

    public void removeQuantite(int nb){
        this.quantite -= nb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Blob getAffiche() {
        return affiche;
    }

    public void setAffiche(Blob affiche) {
        this.affiche = affiche;
    }

    public int getNbCopieDispo() {
        return nbCopieDispo;
    }

    public void setNbCopieDispo(int nbCopieDispo) {
        this.nbCopieDispo = nbCopieDispo;
    }

    public int getPopularite() {
        return popularite;
    }

    public void setPopularite(int popularite) {
        this.popularite = popularite;
    }

    public int getFrequenceProg() {
        return frequenceProg;
    }

    public void setFrequenceProg(int frequenceProg) {
        this.frequenceProg = frequenceProg;
    }

    public int getPerenite() {
        return perenite;
    }

    public void setPerenite(int perenite) {
        this.perenite = perenite;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getPhysique() {
        return physique;
    }

    public void setPhysique(int physique) {
        this.physique = physique;
    }

}
