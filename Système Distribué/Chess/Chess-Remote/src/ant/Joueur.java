/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ant;

import java.awt.Color;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Joueur {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Color color;

    public Joueur(){}

    public Joueur(Color color){
        this.color = color;
    }

    public Long getId(){
        return id;
    }
    public Color getColor(){
        return color;
    }

    public void setId(Long id){
        this.id = id;
    }
    public void setColor(Color color){
        this.color = color;
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
        Joueur other = (Joueur)obj;
        if(this.id.equals(other.id)){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return "Entity.Joueur, id: " + id;
    }

}
