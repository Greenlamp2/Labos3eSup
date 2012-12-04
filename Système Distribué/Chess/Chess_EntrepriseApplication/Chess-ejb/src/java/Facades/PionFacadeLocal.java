/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import ant.Pion;
import ant.Plateau;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Greenlamp
 */
@Local
public interface PionFacadeLocal {

    void create(Pion pion);

    void edit(Pion pion);

    void remove(Pion pion);

    Pion find(Object id);

    List<Pion> findAll();

    List<Pion> findRange(int[] range);

    int count();

}
