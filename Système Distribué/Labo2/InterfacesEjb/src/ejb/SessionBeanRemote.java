/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import ant.Pions;
import ant.Plateaux;
import java.awt.Point;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Greenlamp
 */
@Remote
public interface SessionBeanRemote {

    void createPlateau(String nomPlateau);
    void createJoueur(int couleur, String nomPlateau);
    void createPion(String nom, int positionX, int positionY, String nomPlateau, int couleur);

    List<Plateaux> getPlateaux();
    List<Pions> getPions();



    List<Pions> findByNomPlateau(String nomPlateau);

    boolean bougerPion(String nom, int couleur, String nomPlateau, int oldX, int oldY, int posX, int posY);

    int rejoindrePartie(String nomPlateau);

    void quitterPartie(String nomPlateau, int couleur);

    int getIdJoueur(String nomPlateau, int couleur);

    void deletePlateau(String nomPlateau);

    List<Point> getDeplacementPossible(int x, int y, int couleur, String nom);
}
