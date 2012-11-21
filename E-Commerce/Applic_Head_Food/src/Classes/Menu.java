/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import java.util.LinkedList;


public class Menu {
    private String nomMenu;
    private String vedetteJour;
    private LinkedList<Recette> listeEntrees;
    private LinkedList<Recette> listePlats;
    private LinkedList<Recette> listeDesserts;
    private float prix;
    private float supplement;

    public Menu(){
        this.nomMenu = null;
        this.vedetteJour = null;
        listeEntrees = new LinkedList<>();
        listePlats = new LinkedList<>();
        listeDesserts = new LinkedList<>();
    }

    public void validerEntree(String nomRecette){
        for(Recette recette : listeEntrees){
            if(recette.getNomRecette().equals(nomRecette)){
                recette.setValider(true);
            }
        }
    }

    public void validerPlat(String nomRecette){
        for(Recette recette : listePlats){
            if(recette.getNomRecette().equals(nomRecette)){
                recette.setValider(true);
            }
        }
    }

    public void validerDessert(String nomRecette){
        for(Recette recette : listeDesserts){
            if(recette.getNomRecette().equals(nomRecette)){
                recette.setValider(true);
            }
        }
    }

    public void addEntree(String nomRecette, boolean chaud){
        Recette recette = new Recette();
        recette.setNomRecette(nomRecette);
        recette.setChaud(chaud);
        recette.setType("entree");
        listeEntrees.add(recette);
    }

    public void addPlat(String nomRecette, boolean chaud){
        Recette recette = new Recette();
        recette.setNomRecette(nomRecette);
        recette.setChaud(chaud);
        recette.setType("plat");
        listePlats.add(recette);
    }

    public void addDessert(String nomRecette, boolean chaud){
        Recette recette = new Recette();
        recette.setNomRecette(nomRecette);
        recette.setChaud(chaud);
        recette.setType("dessert");
        listeDesserts.add(recette);
    }

    public boolean addIngredientEntree(String nomEntree, String nom, int quantite){
        boolean found = false;
        for(Recette entree : listeEntrees){
            if(entree.getNomRecette().equals(nomEntree)){
                if(!entree.isValider()){
                    entree.addIngredient(nom, quantite, null);
                }
                found = true;
                break;
            }
        }
        return found;
    }

    public boolean addIngredientPlat(String nomPlat, String nom, int quantite){
        boolean found = false;
        for(Recette plat : listePlats){
            if(plat.getNomRecette().equals(nomPlat)){
                if(!plat.isValider()){
                    plat.addIngredient(nom, quantite, null);
                }
                found = true;
                break;
            }
        }
        return found;
    }

    public boolean addIngredientDessert(String nomDessert, String nom, int quantite){
        boolean found = false;
        for(Recette dessert : listeDesserts){
            if(dessert.getNomRecette().equals(nomDessert)){
                if(!dessert.isValider()){
                    dessert.addIngredient(nom, quantite, null);
                }
                found = true;
                break;
            }
        }
        return found;
    }

    public void delIngredientEntree(String nomEntree, String nom){
        for(Recette entree : listeEntrees){
            if(entree.getNomRecette().equals(nomEntree)){
                if(!entree.isValider()){
                    entree.delIngredient(nom);
                }
            }
        }
    }

    public void delIngredientPlat(String nomPlat, String nom){
        for(Recette plat : listePlats){
            if(plat.getNomRecette().equals(nomPlat)){
                if(!plat.isValider()){
                    plat.delIngredient(nom);
                }
            }
        }
    }

    public void delIngredientDessert(String nomDessert, String nom){
        for(Recette dessert : listeDesserts){
            if(dessert.getNomRecette().equals(nomDessert)){
                if(!dessert.isValider()){
                    dessert.delIngredient(nom);
                }
            }
        }
    }

    public String getNomMenu() {
        return nomMenu;
    }

    public void setNomMenu(String nomMenu) {
        this.nomMenu = nomMenu;
    }

    public String getVedetteJour() {
        return vedetteJour;
    }

    public void setVedetteJour(String vedetteJour) {
        this.vedetteJour = vedetteJour;
    }

    public LinkedList<Recette> getListeEntrees() {
        return listeEntrees;
    }

    public void setListeEntrees(LinkedList<Recette> listeEntrees) {
        this.listeEntrees = listeEntrees;
    }

    public LinkedList<Recette> getListePlats() {
        return listePlats;
    }

    public void setListePlats(LinkedList<Recette> listePlats) {
        this.listePlats = listePlats;
    }

    public LinkedList<Recette> getListeDesserts() {
        return listeDesserts;
    }

    public void setListeDesserts(LinkedList<Recette> listeDesserts) {
        this.listeDesserts = listeDesserts;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getSupplement() {
        return supplement;
    }

    public void setSupplement(float supplement) {
        this.supplement = supplement;
    }
}
