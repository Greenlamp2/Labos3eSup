/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;


public class Ingredient {
    private String nomIngredient;
    private int quantite;
    private String allergene;

    public Ingredient(){
        nomIngredient = null;
        quantite = 0;
    }

    public Ingredient(String nomIngredient, int quantite, String allergene){
        this.nomIngredient = nomIngredient;
        this.quantite = quantite;
        this.allergene = allergene;
    }

    public String getNomIngredient() {
        return nomIngredient;
    }

    public void setNomIngredient(String nomIngredient) {
        this.nomIngredient = nomIngredient;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getAllergene() {
        return allergene;
    }

    public void setAllergene(String allergene) {
        this.allergene = allergene;
    }

}
