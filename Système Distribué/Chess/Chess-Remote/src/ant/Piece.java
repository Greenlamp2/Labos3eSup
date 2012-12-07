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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Piece implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected int x;
    protected int y;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    protected Plateau plateau;
    protected Color color;
    public enum Direction{HAUT, BAS, DIAGO};


    public Piece(){}

    public Piece(int x, int y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public boolean isAt(int x, int y){
        System.out.println("x: " + x + " y: " + y);
        System.out.println("is at: piece " + getPosX() + " / " + getPosY());
        return (x == getPosX() && y == getPosX());
    }

    public List<Point> getDeplacementPossible(EchiquierE[][] plateau){
        List<Point> listePoint = new ArrayList<>();
        return listePoint;
    }

    public Long getId(){
        return id;
    }

    public String getNom(){
        return "";
    }

    public String getFileName(){
        return "";
    }

    public int getPosX(){
        return x;
    }

    public void setPosX(int posX){
        this.x = posX;
    }

    public int getPosY(){
        return y;
    }

    public void setPosY(int posY){
        this.y = posY;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Color getColor(){
        return this.color;
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
        Piece other = (Piece)obj;
        if(this.id.equals(other.id)){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return "Entity.Piece, id: " + id;
    }
}
