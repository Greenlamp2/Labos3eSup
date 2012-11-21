/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bean;

import java.io.Serializable;


public class BeanUser implements Serializable{
    private int id;
    private String nom;
    private String prenom;

    public BeanUser(){
        nom = null;
        prenom = null;
    }

    public BeanUser(String nom, String prenom, int id){
        setNom(nom);
        setPrenom(prenom);
        setId(id);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
