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
public class Cavalier extends Piece implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Cavalier(){}

    public Cavalier(int x, int y, Color color){
        super(x, y, color);
    }

    @Override
    public boolean isAt(int x, int y){
        return (x == getPosX() && y == getPosY());
    }

    @Override
    public String getNom(){
        return "Cavalier";
    }

    @Override
    public String getFileName(){
        if(color.getRGB() == Color.WHITE.getRGB()){
            return "cavalierBlanc";
        }else{
            return "cavalierNoir";
        }
    }

    @Override
    public List<Point> getDeplacementPossible(EchiquierE[][] plateau){
        List<Point> listePoint = new ArrayList<>();
        this.addPoint(this.x-2, this.y-1, listePoint, plateau);
        this.addPoint(this.x-2, this.y+1, listePoint, plateau);
        this.addPoint(this.x+2, this.y-1, listePoint, plateau);
        this.addPoint(this.x+2, this.y+1, listePoint, plateau);

        this.addPoint(this.x-1, this.y-2, listePoint, plateau);
        this.addPoint(this.x-1, this.y+2, listePoint, plateau);
        this.addPoint(this.x+1, this.y-2, listePoint, plateau);
        this.addPoint(this.x+1, this.y+2, listePoint, plateau);
        return listePoint;
    }

    private void addPoint(int newX, int newY, List<Point> points, EchiquierE[][] plateau) {
        boolean colision = false;
        if(newX >= 0 && newX <=7 && newY >=0 && newY <= 7){
            if(plateau[newX][newY].getPiece() != null){
                if(plateau[newX][newY].getPiece().getColor().getRGB() == getColor().getRGB()){
                    colision = true;
                }
            }
        }

        if(newX >= 0 && newX <=7 && newY >=0 && newY <= 7 && !colision){
            points.add(new Point(newX, newY));
        }
    }

    @Override
    public int hashCode(){
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Cavalier)){
            return false;
        }
        Cavalier other = (Cavalier)obj;
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
