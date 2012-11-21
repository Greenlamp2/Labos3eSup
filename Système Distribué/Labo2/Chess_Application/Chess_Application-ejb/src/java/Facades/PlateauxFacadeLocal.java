/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import ant.Plateaux;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Greenlamp
 */
@Local
public interface PlateauxFacadeLocal {

    void create(Plateaux plateaux);

    void edit(Plateaux plateaux);

    void remove(Plateaux plateaux);

    Plateaux find(Object id);

    List<Plateaux> findAll();

    List<Plateaux> findRange(int[] range);

    int count();

    Plateaux findByName(String nomPlateau);

}
