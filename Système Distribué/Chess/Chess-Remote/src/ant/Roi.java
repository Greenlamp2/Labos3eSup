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
public class Roi extends Piece implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Roi(){}

    public Roi(int x, int y, Color color){
        super(x, y, color);
    }

    @Override
    public boolean isAt(int x, int y){
        return (x == getPosX() && y == getPosY());
    }

    @Override
    public String getNom(){
        return "Roi";
    }

    @Override
    public String getFileName(){
        if(color.getRGB() == Color.WHITE.getRGB()){
            return "roiBlanc";
        }else{
            return "roiNoir";
        }
    }

    public boolean isOnEchec(EchiquierE[][] plateau, Color color){
        System.err.println("isOnEchec");
        boolean retour = false;
        if(!retour){
            retour = checkEchecDiagonale(plateau, color);
        }
        if(!retour){
            retour = checkEchecHorizontal(plateau, color);
        }
        if(!retour){
            retour = checkEchecVertical(plateau, color);
        }
        if(!retour){
            retour = checkEchecCavalier(plateau, color);
        }
        return retour;
    }

    public boolean isOnEchecEtMat(EchiquierE[][] plateau, Color color){
        if(checkEchecDiagonale(plateau, color) || checkEchecHorizontal(plateau, color) || checkEchecVertical(plateau, color) || checkEchecCavalier(plateau, color)){
            return true;
        }else{
            return false;
        }
    }

    private boolean checkEchecDiagonale(EchiquierE[][] plateau, Color color) {
        System.err.println("checkEchecDiagonale");
        int i = this.x;
        int j = this.y;
        while(i >= 0 && j >= 0){
            System.err.println("x: "+x+" y: "+y+" i: "+i+" j:" + j);
            Piece piece = plateau[i][j].getPiece();
            if(piece != null){
                System.err.println("piece not null");
                if(piece.getNom().equalsIgnoreCase("Fou") && piece.getColor().getRGB() != color.getRGB()){
                    System.err.println("Fou1");
                    return true;
                }else if(piece.getNom().equalsIgnoreCase("Reine") && piece.getColor().getRGB() != color.getRGB()){
                    System.err.println("Reine1");
                    return true;
                }else{
                    return false;
                }
            }
            i--;
            j--;
        }
        i = this.x;
        j = this.y;
        while(i <= 7 && j <= 7){
            System.err.println("x: "+x+" y: "+y+" i: "+i+" j:" + j);
            Piece piece = plateau[i][j].getPiece();
            if(piece != null){
                System.err.println("piece not null");
                if(piece.getNom().equalsIgnoreCase("Fou") && piece.getColor().getRGB() != color.getRGB()){
                    System.err.println("Fou2");
                    return true;
                }else if(piece.getNom().equalsIgnoreCase("Reine") && piece.getColor().getRGB() != color.getRGB()){
                    System.err.println("Reine2");
                    return true;
                }else{
                    return false;
                }
            }
            i++;
            j++;
        }
        i = this.x;
        j = this.y;
        while(i <= 7 && j >= 0){
            System.err.println("x: "+x+" y: "+y+" i: "+i+" j:" + j);
            Piece piece = plateau[i][j].getPiece();
            if(piece != null){
                System.err.println("piece not null");
                if(piece.getNom().equalsIgnoreCase("Fou") && piece.getColor().getRGB() != color.getRGB()){
                    System.err.println("Fou3");
                    return true;
                }else if(piece.getNom().equalsIgnoreCase("Reine") && piece.getColor().getRGB() != color.getRGB()){
                    System.err.println("Reine3");
                    return true;
                }else{
                    return false;
                }
            }
            i++;
            j--;
        }
        i = this.x;
        j = this.y;
        while(i >= 0 && j <= 7){
            Piece piece = plateau[i][j].getPiece();
            if(piece != null){
                System.err.println("piece not null");
                if(piece.getNom().equalsIgnoreCase("Fou") && piece.getColor().getRGB() != color.getRGB()){
                    System.err.println("Fou4");
                    return true;
                }else if(piece.getNom().equalsIgnoreCase("Reine") && piece.getColor().getRGB() != color.getRGB()){
                    System.err.println("Reine4");
                    return true;
                }else{
                    return false;
                }
            }
            i--;
            j++;
        }
        return false;
    }

    private boolean checkEchecHorizontal(EchiquierE[][] plateau, Color color) {
        System.err.println("checkEchecHorizontal");
        for(int i = this.x; i <= 7; i++){
            Piece piece = plateau[i][this.y].getPiece();
            if(piece != null){
                System.err.println("piece not null");
                if(piece.getNom().equalsIgnoreCase("Tour") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }else if(piece.getNom().equalsIgnoreCase("Reine") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }else{
                    return false;
                }
            }
        }

        for(int i = this.x; i >= 0; i--){
            Piece piece = plateau[i][this.y].getPiece();
            if(piece != null){
                System.err.println("piece not null");
                if(piece.getNom().equalsIgnoreCase("Tour") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }else if(piece.getNom().equalsIgnoreCase("Reine") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    private boolean checkEchecVertical(EchiquierE[][] plateau, Color color) {
        System.err.println("checkEchecVertical");
        for(int i = this.y; i <= 7; i++){
            Piece piece = plateau[this.x][i].getPiece();
            if(piece != null){
                System.err.println("piece not null");
                if(piece.getNom().equalsIgnoreCase("Tour") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }else if(piece.getNom().equalsIgnoreCase("Reine") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }else{
                    return false;
                }
            }
        }

        for(int i = this.y; i >= 0; i--){
            Piece piece = plateau[this.x][i].getPiece();
            if(piece != null){
                System.err.println("piece not null");
                if(piece.getNom().equalsIgnoreCase("Tour") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }else if(piece.getNom().equalsIgnoreCase("Reine") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    private boolean checkEchecCavalier(EchiquierE[][] plateau, Color color) {
        System.err.println("checkEchecCavalier");
        Piece piece = null;
        int i = this.x-2;
        int j = this.y-1;
        System.err.println("1) x: "+x+" y: "+y+" i: "+i+" j:" + j);
        if(i>=0 && i<=7 && j>=0 && j<=7){
            piece = plateau[i][j].getPiece();
            if(piece != null){
                System.err.println("piece not null 1");
                System.err.println("nom: " + piece.getNom());
                if(piece.getNom().equalsIgnoreCase("Cavalier") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }
            }
        }

        i = this.x-2;
        j = this.y+1;
        System.err.println("2) x: "+x+" y: "+y+" i: "+i+" j:" + j);
        if(i>=0 && i<=7 && j>=0 && j<=7){
            piece = plateau[i][j].getPiece();
            if(piece != null){
                System.err.println("piece not null 2");
                System.err.println("nom: " + piece.getNom());
                if(piece.getNom().equalsIgnoreCase("Cavalier") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }
            }
        }

        i = this.x+2;
        j = this.y-1;
        System.err.println("3) x: "+x+" y: "+y+" i: "+i+" j:" + j);
        if(i>=0 && i<=7 && j>=0 && j<=7){
            piece = plateau[i][j].getPiece();
            if(piece != null){
                System.err.println("piece not null 3");
                System.err.println("nom: " + piece.getNom());
                if(piece.getNom().equalsIgnoreCase("Cavalier") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }
            }
        }

        i = this.x+2;
        j = this.y+1;
        System.err.println("4) x: "+x+" y: "+y+" i: "+i+" j:" + j);
        if(i>=0 && i<=7 && j>=0 && j<=7){
            piece = plateau[i][j].getPiece();
            System.err.println("x: "+x+" y: "+y+" i: "+i+" j:" + j);
            if(piece != null){
                System.err.println("piece not null 4");
                System.err.println("nom: " + piece.getNom());
                if(piece.getNom().equalsIgnoreCase("Cavalier") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }
            }
        }

        i = this.x-1;
        j = this.y-2;
        System.err.println("5) x: "+x+" y: "+y+" i: "+i+" j:" + j);
        if(i>=0 && i<=7 && j>=0 && j<=7){
            piece = plateau[i][j].getPiece();
            if(piece != null){
                System.err.println("piece not null 5");
                System.err.println("nom: " + piece.getNom());
                if(piece.getNom().equalsIgnoreCase("Cavalier") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }
            }
        }

        i = this.x-1;
        j = this.y+2;
        System.err.println("6) x: "+x+" y: "+y+" i: "+i+" j:" + j);
        if(i>=0 && i<=7 && j>=0 && j<=7){
            piece = plateau[i][j].getPiece();
            if(piece != null){
                System.err.println("piece not null 6");
                System.err.println("nom: " + piece.getNom());
                if(piece.getNom().equalsIgnoreCase("Cavalier") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }
            }
        }

        i = this.x+1;
        j = this.y-2;
        if(i>=0 && i<=7 && j>=0 && j<=7){
            piece = plateau[i][j].getPiece();
            if(piece != null){
                System.err.println("piece not null 7");
                System.err.println("nom: " + piece.getNom());
                if(piece.getNom().equalsIgnoreCase("Cavalier") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }
            }
        }

        i = this.x+1;
        j = this.y+2;
        if(i>=0 && i<=7 && j>=0 && j<=7){
            piece = plateau[i][j].getPiece();
            if(piece != null){
                System.err.println("piece not null 8");
                System.err.println("nom: " + piece.getNom());
                if(piece.getNom().equalsIgnoreCase("Cavalier") && piece.getColor().getRGB() != color.getRGB()){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public List<Point> getDeplacementPossible(EchiquierE[][] plateau){
        List<Point> listePoint = new ArrayList<>();
        this.addPoint(this.x-1, this.y-1, listePoint, plateau);
        this.addPoint(this.x-1, this.y+1, listePoint, plateau);
        this.addPoint(this.x+1, this.y-1, listePoint, plateau);
        this.addPoint(this.x+1, this.y+1, listePoint, plateau);
        this.addPoint(this.x-1, this.y, listePoint, plateau);
        this.addPoint(this.x+1, this.y, listePoint, plateau);
        this.addPoint(this.x, this.y-1, listePoint, plateau);
        this.addPoint(this.x, this.y+1, listePoint, plateau);
        return listePoint;
    }

    private void addPoint(int newX, int newY, List<Point> points, EchiquierE[][] plateau) {
        boolean colision = false;
        Piece pion = null;
        if(newX >= 0 && newX <=7 && newY >=0 && newY <= 7){
            pion = plateau[newX][newY].getPiece();
        }
        if(pion != null){
            if(pion.getColor().getRGB() == getColor().getRGB()){
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
    public int hashCode(){
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Roi)){
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
