/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ant;

import java.awt.Color;
import java.io.Serializable;
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
    private Long id;
    private int x;
    private int y;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Plateau plateau;
    protected Color color;


    public Piece(){}

    public Piece(int x, int y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public boolean isAt(int x, int y){
        return (x == getPosX() && y == getPosX());
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
