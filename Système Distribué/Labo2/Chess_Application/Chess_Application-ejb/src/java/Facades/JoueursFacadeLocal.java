/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import ant.Joueurs;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Greenlamp
 */
@Local
public interface JoueursFacadeLocal {

    void create(Joueurs plateaux);

    void edit(Joueurs plateaux);

    void remove(Joueurs plateaux);

    Joueurs find(Object id);

    List<Joueurs> findAll();

    List<Joueurs> findRange(int[] range);

    int count();

    List<Joueurs> findByNomPlateau(String nomPlateau);

    Joueurs findByCouleurNomPlateau(int couleur, String nomPlateau);

}
