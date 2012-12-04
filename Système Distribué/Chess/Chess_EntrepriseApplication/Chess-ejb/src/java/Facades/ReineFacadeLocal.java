/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import ant.Reine;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Greenlamp
 */
@Local
public interface ReineFacadeLocal {

    void create(Reine reine);

    void edit(Reine reine);

    void remove(Reine reine);

    Reine find(Object id);

    List<Reine> findAll();

    List<Reine> findRange(int[] range);

    int count();

}
