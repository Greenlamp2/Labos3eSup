/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import ant.Pions;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Greenlamp
 */
@Local
public interface PionsFacadeLocal {

    void create(Pions pions);

    void edit(Pions pions);

    void remove(Pions pions);

    Pions find(Object id);

    List<Pions> findAll();

    List<Pions> findRange(int[] range);

    int count();

    List<Pions> findByNomPlateau(String nomPlateau);

    Pions findByNomCouleurNomPlateauXY(String nom, int couleur, String nomPlateau, int x, int y);

    Pions findByPositionXY(int x, int y) throws Exception;

}
