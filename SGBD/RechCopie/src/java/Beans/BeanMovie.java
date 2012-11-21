/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.io.Serializable;
import java.sql.Blob;


public class BeanMovie implements Serializable{
    private int id;
    private String nom;
    private Blob affiche;
    private int nbCopieDispo;
    private int popularite;
    private int frequenceProg;
    private int perenite;

    public BeanMovie() {
        this.id = -1;
        this.nom = null;
        this.affiche = null;
        this.nbCopieDispo = -1;
        this.popularite = -1;
        this.frequenceProg = -1;
        this.perenite = -1;
    }

    public BeanMovie(int id, String nom, Blob affiche, int nbCopieDispo, int popularite, int frequenceProg, int perenite){
        setId(id);
        setNom(nom);
        setAffiche(affiche);
        setNbCopieDispo(nbCopieDispo);
        setPopularite(popularite);
        setFrequenceProg(frequenceProg);
        setPerenite(perenite);
    }

    public BeanMovie(BeanMovie beanMovie){
        setId(beanMovie.getId());
        setNom(beanMovie.getNom());
        setAffiche(beanMovie.getAffiche());
        setNbCopieDispo(beanMovie.getNbCopieDispo());
        setPopularite(beanMovie.getPopularite());
        setFrequenceProg(beanMovie.getFrequenceProg());
        setPerenite(beanMovie.getPerenite());
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

}
