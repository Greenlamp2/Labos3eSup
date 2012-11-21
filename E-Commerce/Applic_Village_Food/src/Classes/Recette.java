/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import java.util.LinkedList;


public class Recette {
    private String nomRecette;
    private boolean Chaud;
    private LinkedList<Ingredient> listeIngredients;
    private String type;
    private boolean valider;

    public Recette(){
        this.nomRecette = null;
        this.Chaud = true;
        this.listeIngredients = new LinkedList<>();
        this.type = null;
        this.valider = false;
    }

    public void addIngredient(String nom, int quantite, String allergene){
        Ingredient ingredient = new Ingredient(nom, quantite, allergene);
        listeIngredients.add(ingredient);
    }

    public void delIngredient(String nom){
        int cpt = 0;
        for(Ingredient ingredient : listeIngredients){
            if(ingredient.getNomIngredient().equals(nom)){
                listeIngredients.remove(cpt);
                break;
            }
            cpt++;
        }
    }

    public String getNomRecette() {
        return nomRecette;
    }

    public void setNomRecette(String nomRecette) {
        this.nomRecette = nomRecette;
    }

    public boolean isChaud() {
        return Chaud;
    }

    public void setChaud(boolean Chaud) {
        this.Chaud = Chaud;
    }

    public LinkedList<Ingredient> getListeIngredients() {
        return listeIngredients;
    }

    public void setListeIngredients(LinkedList<Ingredient> listeIngredients) {
        this.listeIngredients = listeIngredients;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isValider() {
        return valider;
    }

    public void setValider(boolean valider) {
        this.valider = valider;
    }
}
