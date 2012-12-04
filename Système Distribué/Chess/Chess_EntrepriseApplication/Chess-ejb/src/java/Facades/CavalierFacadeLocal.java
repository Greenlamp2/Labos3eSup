/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import ant.Cavalier;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Greenlamp
 */
@Local
public interface CavalierFacadeLocal {

    void create(Cavalier cavalier);

    void edit(Cavalier cavalier);

    void remove(Cavalier cavalier);

    Cavalier find(Object id);

    List<Cavalier> findAll();

    List<Cavalier> findRange(int[] range);

    int count();

}
