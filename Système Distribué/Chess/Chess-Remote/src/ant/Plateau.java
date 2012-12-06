/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ant;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = "Plateau.findByNom", query = "SELECT p FROM Plateau p WHERE p.nom = :nom")
})
public class Plateau implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    private String nom;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Collection<Piece> listePiece = new ArrayList<>();

    private Joueur[] listeJoueur = new Joueur[2];

    public void Plateau(){

    }

    public void init(String nom){
        setNom(nom);
        setJoueur1(null);
        setJoueur2(null);

        listePiece.add(new Tour(0, 0, Color.BLACK));
        listePiece.add(new Cavalier(1, 0, Color.BLACK));
        listePiece.add(new Fou(2, 0, Color.BLACK));
        listePiece.add(new Roi(3, 0, Color.BLACK));
        listePiece.add(new Reine(4, 0, Color.BLACK));
        listePiece.add(new Fou(5, 0, Color.BLACK));
        listePiece.add(new Cavalier(6, 0, Color.BLACK));
        listePiece.add(new Tour(7, 0, Color.BLACK));

        listePiece.add(new Pion(0, 1, Color.BLACK));
        listePiece.add(new Pion(1, 1, Color.BLACK));
        listePiece.add(new Pion(2, 1, Color.BLACK));
        listePiece.add(new Pion(3, 1, Color.BLACK));
        listePiece.add(new Pion(4, 1, Color.BLACK));
        listePiece.add(new Pion(5, 1, Color.BLACK));
        listePiece.add(new Pion(6, 1, Color.BLACK));
        listePiece.add(new Pion(7, 1, Color.BLACK));

        listePiece.add(new Tour(0, 7, Color.WHITE));
        listePiece.add(new Cavalier(1, 7, Color.WHITE));
        listePiece.add(new Fou(2, 7, Color.WHITE));
        listePiece.add(new Roi(3, 7, Color.WHITE));
        listePiece.add(new Reine(4, 7, Color.WHITE));
        listePiece.add(new Fou(5, 7, Color.WHITE));
        listePiece.add(new Cavalier(6, 7, Color.WHITE));
        listePiece.add(new Tour(7, 7, Color.WHITE));

        listePiece.add(new Pion(0, 6, Color.WHITE));
        listePiece.add(new Pion(1, 6, Color.WHITE));
        listePiece.add(new Pion(2, 6, Color.WHITE));
        listePiece.add(new Pion(3, 6, Color.WHITE));
        listePiece.add(new Pion(4, 6, Color.WHITE));
        listePiece.add(new Pion(5, 6, Color.WHITE));
        listePiece.add(new Pion(6, 6, Color.WHITE));
        listePiece.add(new Pion(7, 6, Color.WHITE));
    }

    public Collection<Piece> getListePiece(){
        return listePiece;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setJoueur1(Joueur joueur) {
        listeJoueur[0] = joueur;
    }

    public void setJoueur2(Joueur joueur) {
        listeJoueur[1] = joueur;
    }

    public Joueur getJoueur1(){
        return listeJoueur[0];
    }

    public Joueur getJoueur2(){
        return listeJoueur[1];
    }

    public String getNom(){
        return nom;
    }

    public int getNombreDeJoueurs(){
        int cpt = 0;
        if(getJoueur1() != null){
            cpt++;
        }
        if(getJoueur2() != null){
            cpt++;
        }
        return cpt;
    }

}
