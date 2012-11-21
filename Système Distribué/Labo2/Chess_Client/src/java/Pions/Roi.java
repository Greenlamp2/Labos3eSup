/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Pions;

import GUI.Main;
import Others.Plateau;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Roi implements Piece{
    int x;
    int y;
    Couleur couleur;
    private String nom = "Roi";
    public String getNom() {
        return nom;
    }

    public Roi(){
        this.x = 0;
        this.y = 0;
        this.couleur = Couleur.UNDEFINED;
    }

    public Roi(int x, int y, int couleur){
        this.x = x;
        this.y = y;
        if(couleur == 0){
            this.couleur = Couleur.BLANC;
        }else if(couleur == 1){
            this.couleur = Couleur.NOIR;
        }else{
            this.couleur = Couleur.UNDEFINED;
        }
    }

    public Roi(int x, int y, Couleur couleur){
        this.x = x;
        this.y = y;
        this.couleur = couleur;
    }

    @Override
    public String getImage() {
        if(couleur == Couleur.BLANC){
            return "roiBlanc";
        }else if(couleur == Couleur.NOIR){
            return "roiNoir";
        }else{
            return "undefined";
        }
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public List<Point> getDeplacementPossible(Plateau[][] plateau) {
        List<Point> points = new ArrayList<Point>();
        this.addPoint(this.x-1, this.y-1, points, Direction.HAUT, plateau, couleur);
        this.addPoint(this.x-1, this.y+1, points, Direction.HAUT, plateau, couleur);
        this.addPoint(this.x+1, this.y-1, points, Direction.HAUT, plateau, couleur);
        this.addPoint(this.x+1, this.y+1, points, Direction.HAUT, plateau, couleur);
        this.addPoint(this.x-1, this.y, points, Direction.HAUT, plateau, couleur);
        this.addPoint(this.x+1, this.y, points, Direction.HAUT, plateau, couleur);
        this.addPoint(this.x, this.y-1, points, Direction.HAUT, plateau, couleur);
        this.addPoint(this.x, this.y+1, points, Direction.HAUT, plateau, couleur);
        return points;
    }

    private void addPoint(int newX, int newY, List<Point> points, Direction direction, Plateau[][] plateau, Couleur couleur) {
        boolean colision = false;
        Piece pion = null;
        if(newX >= 0 && newX <=7 && newY >=0 && newY <= 7){
            pion = plateau[newX][newY].getPion();
        }
        if(pion != null){
            if(pion.getCouleur() == this.couleur){
                colision = true;
            }
            if(newX >= 0 && newX <=7 && newY >=0 && newY <= 7 && !colision){
                points.add(new Point(newX, newY));
            }
        }else{
            if(newX >= 0 && newX <=7 && newY >=0 && newY <= 7 && !colision){
                points.add(new Point(newX, newY));
            }
        }
    }

    @Override
    public Couleur getCouleur() {
        return this.couleur;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public Piece getInstance(Piece piece) {
        Roi roi = new Roi(piece.getX(), piece.getY(), piece.getCouleur());
        return roi;
    }
}
