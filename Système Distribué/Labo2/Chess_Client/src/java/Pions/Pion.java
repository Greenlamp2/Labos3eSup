/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Pions;

import Others.Plateau;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Pion implements Piece{
    int x;
    int y;
    Couleur couleur;
    private String nom = "Pion";
    public String getNom() {
        return nom;
    }

    public Pion(){
        this.x = 0;
        this.y = 0;
        this.couleur = Couleur.UNDEFINED;
    }

    public Pion(int x, int y, int couleur){
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

    public Pion(int x, int y, Couleur couleur){
        this.x = x;
        this.y = y;
        this.couleur = couleur;
    }

    @Override
    public String getImage() {
        if(couleur == Couleur.BLANC){
            return "pionBlanc";
        }else if(couleur == Couleur.NOIR){
            return "pionNoir";
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
        if(couleur == Couleur.BLANC){
            if(this.x == 6){
                this.addPoint(this.x-2, this.y, points, Direction.HAUT, plateau);
            }
            this.addPoint(this.x-1, this.y, points, Direction.HAUT, plateau);
        }else if(couleur == Couleur.NOIR){
            if(this.x == 1){
                this.addPoint(this.x+2, this.y, points, Direction.BAS, plateau);
            }
            this.addPoint(this.x+1, this.y, points, Direction.BAS, plateau);
        }
        Piece pionDiago = plateau[this.x-1][this.y-1].getPion();
        Piece monPion = plateau[this.x][this.y].getPion();
        if(pionDiago != null & monPion != null && pionDiago.getCouleur() != monPion.getCouleur()){
            this.addPoint(this.x-1, this.y-1, points, Direction.HAUT, plateau);
        }
        pionDiago = plateau[this.x+1][this.y+1].getPion();
        if(pionDiago != null & monPion != null && pionDiago.getCouleur() != monPion.getCouleur()){
            this.addPoint(this.x+1, this.y+1, points, Direction.HAUT, plateau);
        }

        return points;
    }

    private void addPoint(int newX, int newY, List<Point> points, Direction direction, Plateau[][] plateau) {
        boolean colision = false;
        if(direction == Direction.HAUT){
            for(int i=this.x; i>=newX; i--){
                if(i!=this.x && plateau[i][this.y].getPion() != null){
                    colision = true;
                    break;
                }
            }
        }else{
            for(int i=this.x; i<=newX; i++){
                if(i!=this.x && plateau[i][this.y].getPion() != null){
                    colision = true;
                    break;
                }
            }
        }
        if(newX >= 0 && newX <=7 && newY >=0 && newY <= 7 && !colision){
            points.add(new Point(newX, newY));
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
        Pion pion = new Pion(piece.getX(), piece.getY(), piece.getCouleur());
        return pion;
    }
}
