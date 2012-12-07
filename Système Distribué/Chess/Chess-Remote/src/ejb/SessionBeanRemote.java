/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import ant.Joueur;
import ant.Piece;
import ant.Plateau;
import java.awt.Color;
import java.awt.Point;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Greenlamp
 */
@Remote
public interface SessionBeanRemote {
    /**********************************************************************/
    /*Plateau*/
    /**********************************************************************/
    List<Plateau> getPlateaux() throws Exception;
    public void createPlateau(String nom) throws Exception;
    Plateau getPlateauByNom(String nom) throws Exception;

    Joueur rejoindrePartie(String nomPlateau) throws Exception;
    List<Piece> getPieceByPlateau(String nomPlateau) throws Exception;
    void deletePlateau(String nomPlateau) throws Exception;


    /**********************************************************************/
    /*Piece*/
    /**********************************************************************/
    Piece getPieceAt(String nomPlateau, int posX, int posY) throws Exception;
    List<Point> getDeplacementPossible(String nomPlateau, int x, int y) throws Exception;
    boolean bougerPion(String nomPlateau, int oldX, int oldY, int newX, int newY) throws Exception;


    /**********************************************************************/
    /*Joueur*/
    /**********************************************************************/
    Long getIdJoueur(String nomPlateau, Color color) throws Exception;
    void quitterPartie(String nomPlateau, Color color) throws Exception;

}
