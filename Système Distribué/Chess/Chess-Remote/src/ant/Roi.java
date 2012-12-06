/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ant;

import java.awt.Color;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Roi extends Piece implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Roi(){}

    public Roi(int x, int y, Color color){
        super(x, y, color);
    }

    public boolean isAt(int x, int y){
        return (x == getPosX() && y == getPosX());
    }

    @Override
    public String getNom(){
        return "Roi";
    }

    @Override
    public String getFileName(){
        if(color == Color.BLACK){
            return "roiBlanc";
        }else{
            return "roiNoir";
        }
    }

    @Override
    public int hashCode(){
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Piece)){
            return false;
        }
        Roi other = (Roi)obj;
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
