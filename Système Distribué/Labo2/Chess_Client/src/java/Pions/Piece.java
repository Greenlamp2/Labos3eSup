/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pions;

import Others.Plateau;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author Greenlamp
 */
public interface Piece {
    public static enum Couleur{BLANC, NOIR, UNDEFINED};
    public enum Direction{HAUT, BAS};
    public String getImage();
    public int getX();
    public int getY();
    public void setX(int x);
    public void setY(int y);
    public Couleur getCouleur();
    public List<Point> getDeplacementPossible(Plateau[][] plateau);
    public String getNom();
    public Piece getInstance(Piece piece);

}
