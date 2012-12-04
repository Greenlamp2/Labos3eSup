/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import ant.Roi;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Greenlamp
 */
@Local
public interface RoiFacadeLocal {

    void create(Roi roi);

    void edit(Roi roi);

    void remove(Roi roi);

    Roi find(Object id);

    List<Roi> findAll();

    List<Roi> findRange(int[] range);

    int count();

}
