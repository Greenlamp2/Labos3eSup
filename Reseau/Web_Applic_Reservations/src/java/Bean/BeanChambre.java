/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bean;

import java.io.Serializable;


public class BeanChambre implements Serializable{
    private int numeroChambre;
    private int nbOccupant;
    private boolean douche;
    private boolean baignoire;
    private boolean cuvette;
    private int prixHtva;

    public BeanChambre(){
        numeroChambre = -1;
        nbOccupant = -1;
        douche = false;
        baignoire = false;
        cuvette = false;
        prixHtva = -1;
    }

    public BeanChambre(int numeroChambre, int nbOccupant, int douche, int baignoire, int cuvette, int prixHtva){
        setNumeroChambre(numeroChambre);
        setNbOccupant(nbOccupant);
        setPrixHtva(prixHtva);
        setDouche(douche);
        setBaignoire(baignoire);
        setCuvette(cuvette);
    }

    public int getNumeroChambre() {
        return numeroChambre;
    }

    public void setNumeroChambre(int numeroChambre) {
        this.numeroChambre = numeroChambre;
    }

    public int getNbOccupant() {
        return nbOccupant;
    }

    public void setNbOccupant(int nbOccupant) {
        this.nbOccupant = nbOccupant;
    }

    public String isDouche() {
        if(douche == true){
            return "Oui";
        }else{
            return "Non";
        }
    }

    public void setDouche(int douche) {
        if(douche == 1) this.douche = true;
        else this.douche = false;
    }

    public String isBaignoire() {
        if(baignoire == true){
            return "Oui";
        }else{
            return "Non";
        }
    }

    public void setBaignoire(int baignoire) {
        if(baignoire == 1) this.baignoire = true;
        else this.baignoire = false;
    }

    public String isCuvette() {
        if(cuvette == true){
            return "Oui";
        }else{
            return "Non";
        }
    }

    public void setCuvette(int cuvette) {
        if(cuvette == 1) this.cuvette = true;
        else this.cuvette = false;
    }

    public int getPrixHtva() {
        return prixHtva;
    }

    public void setPrixHtva(int prixHtva) {
        this.prixHtva = prixHtva;
    }
}
