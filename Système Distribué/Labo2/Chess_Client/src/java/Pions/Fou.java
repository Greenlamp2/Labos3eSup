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


public class Fou implements Piece{
    int x;
    int y;
    Couleur couleur;
    private String nom = "Fou";
    public String getNom() {
        return nom;
    }

    public Fou(){
        this.x = 0;
        this.y = 0;
        this.couleur = Couleur.UNDEFINED;
    }

    public Fou(int x, int y, int couleur){
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

    public Fou(int x, int y, Couleur couleur){
        this.x = x;
        this.y = y;
        this.couleur = couleur;
    }

    @Override
    public String getImage() {
        if(couleur == Couleur.BLANC){
            return "fouBlanc";
        }else if(couleur == Couleur.NOIR){
            return "fouNoir";
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
        //this.addPoint(ligne, colonne, points, Direction.HAUT, plateau, couleur);
        int ligne = this.x-1;
        int colonne = this.y-1;
        while(ligne >= 0 && colonne >= 0){
            boolean retour = this.addPoint(ligne, colonne, points, Direction.HAUT, plateau, couleur);
            if(retour) break;
            ligne--;
            colonne--;
        }

        ligne = this.x-1;
        colonne = this.y+1;
        while(ligne >= 0 && colonne <= 7){
            boolean retour = this.addPoint(ligne, colonne, points, Direction.HAUT, plateau, couleur);
            if(retour) break;
            ligne--;
            colonne++;
        }

        ligne = this.x+1;
        colonne = this.y-1;
        while(ligne <=7 && colonne >= 0){
            boolean retour = this.addPoint(ligne, colonne, points, Direction.HAUT, plateau, couleur);
            if(retour) break;
            ligne++;
            colonne--;
        }

        ligne = this.x+1;
        colonne = this.y+1;
        while(ligne <= 7 && colonne <= 7){
            boolean retour = this.addPoint(ligne, colonne, points, Direction.HAUT, plateau, couleur);
            if(retour) break;
            ligne++;
            colonne++;
        }
        return points;
    }

    private boolean addPoint(int newX, int newY, List<Point> points, Direction direction, Plateau[][] plateau, Couleur couleur) {
        boolean colision = false;
        Piece pion = null;
        if(newX >= 0 && newX <=7 && newY >=0 && newY <= 7){
            pion = plateau[newX][newY].getPion();
        }
        if(pion != null){
            if(pion.getCouleur() == this.couleur){
                return true;
            }else{
                if(newX >= 0 && newX <=7 && newY >=0 && newY <= 7 && !colision){
                    points.add(new Point(newX, newY));
                }
                return false;
            }
        }else{
            if(newX >= 0 && newX <=7 && newY >=0 && newY <= 7 && !colision){
                points.add(new Point(newX, newY));
            }
            return false;
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
        Fou fou = new Fou(piece.getX(), piece.getY(), piece.getCouleur());
        return fou;
    }
}
