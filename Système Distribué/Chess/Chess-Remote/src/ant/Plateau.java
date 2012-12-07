/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ant;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Joueur joueur1;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Joueur joueur2;

    public void Plateau(){

    }

    public void init(String nom){
        setNom(nom);
        setJoueur1(null);
        setJoueur2(null);

        listePiece.add(new Tour(7, 0, Color.WHITE));
        listePiece.add(new Cavalier(7, 1, Color.WHITE));
        listePiece.add(new Fou(7, 2, Color.WHITE));
        listePiece.add(new Roi(7, 3, Color.WHITE));
        listePiece.add(new Reine(7, 4, Color.WHITE));
        listePiece.add(new Fou(7, 5, Color.WHITE));
        listePiece.add(new Cavalier(7, 6, Color.WHITE));
        listePiece.add(new Tour(7, 7, Color.WHITE));

        listePiece.add(new Pion(6, 0, Color.WHITE));
        listePiece.add(new Pion(6, 1, Color.WHITE));
        listePiece.add(new Pion(6, 2, Color.WHITE));
        listePiece.add(new Pion(6, 3, Color.WHITE));
        listePiece.add(new Pion(6, 4, Color.WHITE));
        listePiece.add(new Pion(6, 5, Color.WHITE));
        listePiece.add(new Pion(6, 6, Color.WHITE));
        listePiece.add(new Pion(6, 7, Color.WHITE));

        listePiece.add(new Tour(0, 0, Color.BLACK));
        listePiece.add(new Cavalier(0, 1, Color.BLACK));
        listePiece.add(new Fou(0, 2, Color.BLACK));
        listePiece.add(new Roi(0, 3, Color.BLACK));
        listePiece.add(new Reine(0, 4, Color.BLACK));
        listePiece.add(new Fou(0, 5, Color.BLACK));
        listePiece.add(new Cavalier(0, 6, Color.BLACK));
        listePiece.add(new Tour(0, 7, Color.BLACK));

        listePiece.add(new Pion(1, 0, Color.BLACK));
        listePiece.add(new Pion(1, 1, Color.BLACK));
        listePiece.add(new Pion(1, 2, Color.BLACK));
        listePiece.add(new Pion(1, 3, Color.BLACK));
        listePiece.add(new Pion(1, 4, Color.BLACK));
        listePiece.add(new Pion(1, 5, Color.BLACK));
        listePiece.add(new Pion(1, 6, Color.BLACK));
        listePiece.add(new Pion(1, 7, Color.BLACK));
    }

    public void removePiece(int x, int y){
        System.out.println("removePiece");
        for(Piece piece : listePiece){
            if(piece.isAt(x, y)){
                System.err.println("removed");
                listePiece.remove(piece);
                break;
            }
        }
    }

    public Collection<Piece> getListePiece(){
        return listePiece;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setJoueur1(Joueur joueur) {
        joueur1 = joueur;
    }

    public void setJoueur2(Joueur joueur) {
        joueur2 = joueur;
    }

    public Joueur getJoueur1(){
        return joueur1;
    }

    public Joueur getJoueur2(){
        return joueur2;
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

    public Joueur getJoueurByCouleur(Color color){
        if(getJoueur1().getColor().getRGB() == color.getRGB()){
            return getJoueur1();
        }else{
            return getJoueur2();
        }
    }

    public Joueur quitterPlateau(Color color){
        Joueur joueur = null;
        if(getJoueur1().getColor().getRGB() == color.getRGB()){
            joueur = getJoueur1();
            setJoueur1(null);
        }else{
            joueur = getJoueur2();
            setJoueur2(null);
        }
        return joueur;
    }

    public Piece getPiece(int x, int y){
        for(Piece piece : listePiece){
            if(piece.isAt(x, y)){
                return piece;
            }
        }
        return null;
    }

    public void clearPiece(){
        this.listePiece.clear();
    }

}
