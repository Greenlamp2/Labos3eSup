/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ant;

import Others.EchiquierE;
import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Tour extends Piece implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Tour(){}

    public Tour(int x, int y, Color color){
        super(x, y, color);
    }

    @Override
    public boolean isAt(int x, int y){
        return (x == getPosX() && y == getPosY());
    }

    @Override
    public String getNom(){
        return "Tour";
    }

    @Override
    public String getFileName(){
        if(color.getRGB() == Color.WHITE.getRGB()){
            return "tourBlanc";
        }else{
            return "tourNoir";
        }
    }

    @Override
    public List<Point> getDeplacementPossible(EchiquierE[][] plateau){
        List<Point> listePoint = new ArrayList<>();
        int ligne = this.x-1;
        while(ligne >= 0){
            boolean retour = this.addPoint(ligne, this.y, listePoint, plateau);
            if(retour) break;
            ligne--;
        }
        ligne = this.x+1;
        while(ligne <= 7){
            boolean retour = this.addPoint(ligne, this.y, listePoint, plateau);
            if(retour) break;
            ligne++;
        }
        int colonne = this.y-1;
        while(colonne >= 0){
            boolean retour = this.addPoint(this.x, colonne, listePoint, plateau);
            if(retour) break;
            colonne--;
        }
        colonne = this.y+1;
        while(colonne <= 7){
            boolean retour = this.addPoint(this.x, colonne, listePoint, plateau);
            if(retour) break;
            colonne++;
        }
        return listePoint;
    }

    private boolean addPoint(int newX, int newY, List<Point> points, EchiquierE[][] plateau) {
        boolean colision = false;
        Piece pion = null;
        if(newX >= 0 && newX <=7 && newY >=0 && newY <= 7){
            pion = plateau[newX][newY].getPiece();
        }
        if(pion != null){
            if(pion.getColor().getRGB() == getColor().getRGB()){
                return true;
            }else{
                if(newX >= 0 && newX <=7 && newY >=0 && newY <= 7 && !colision){
                    points.add(new Point(newX, newY));
                }
                return true;
            }
        }else{
            if(newX >= 0 && newX <=7 && newY >=0 && newY <= 7 && !colision){
                points.add(new Point(newX, newY));
            }
            return false;
        }
    }

    @Override
    public int hashCode(){
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Tour)){
            return false;
        }
        Tour other = (Tour)obj;
        if(this.id.equals(other.id)){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return "Entity.Pion, id: " + id;
    }

}
