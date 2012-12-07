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
public class Pion extends Piece implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Pion(){}

    public Pion(int x, int y, Color color){
        super(x, y, color);
    }

    @Override
    public boolean isAt(int x, int y){
        return (x == getPosX() && y == getPosY());
    }

    @Override
    public String getNom(){
        return "Pion";
    }

    @Override
    public String getFileName(){
        if(color.getRGB() == Color.WHITE.getRGB()){
            return "pionBlanc";
        }else{
            return "pionNoir";
        }
    }

    @Override
    public List<Point> getDeplacementPossible(EchiquierE[][] plateau){
        List<Point> listePoint = new ArrayList<>();
        if(color.getRGB() == Color.WHITE.getRGB()){
            if(this.getPosX() == 6){
                addPoint(this.x-2, this.y, listePoint, Direction.HAUT, plateau);
            }
            addPoint(this.x-1, this.y, listePoint, Direction.HAUT, plateau);
            //Diagonale capture
            addPoint(this.x-1, this.y-1, listePoint, Direction.DIAGO, plateau);
            addPoint(this.x-1, this.y+1, listePoint, Direction.DIAGO, plateau);

        }else if(color.getRGB() == Color.BLACK.getRGB()){
            if(this.getPosX() == 1){
                addPoint(this.x+2, this.y, listePoint, Direction.HAUT, plateau);
            }
            addPoint(this.x+1, this.y, listePoint, Direction.HAUT, plateau);
            //Diagonale capture
            addPoint(this.x+1, this.y-1, listePoint, Direction.DIAGO, plateau);
            addPoint(this.x+1, this.y+1, listePoint, Direction.DIAGO, plateau);
        }

        return listePoint;
    }

    private void addPoint(int newX, int newY, List<Point> listePoint, Direction direction, EchiquierE[][] plateau) {
        boolean colision = false;
        if(newX < 0 || newX > 7 || newY < 0 || newY > 7){
            return;
        }
        if(direction == Direction.HAUT){
            for(int i=this.x; i>=newX; i--){
                System.err.println("this.x: " + this.x + " newX: " + newX);
                if(i != this.x && plateau[i][this.y].getPiece() != null){
                    colision = true;
                    break;
                }
            }
        }else if(direction == Direction.BAS){
            for(int i=this.x; i<=newX; i++){
                if(i!=this.x && plateau[i][this.y].getPiece() != null){
                    colision = true;
                    break;
                }
            }
        }else if(direction == Direction.DIAGO){
            Piece piece = plateau[newX][newY].getPiece();
            if(piece == null || piece.getColor().getRGB() == getColor().getRGB()){
                colision = true;
            }
        }

        if(newX >= 0 && newX <= 7 && newY >=0 && newY <= 7 && !colision){
            listePoint.add(new Point(newX, newY));
        }
    }

    @Override
    public int hashCode(){
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Pion)){
            return false;
        }
        Pion other = (Pion)obj;
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
