/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import ant.Plateau;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Greenlamp
 */
@Local
public interface PlateauFacadeLocal {

    void create(Plateau plateau);

    void edit(Plateau plateau);

    void remove(Plateau plateau);

    Plateau find(Object id);

    List<Plateau> findAll();

    List<Plateau> findRange(int[] range);

    int count();

    Plateau getByNom(String nom) throws Exception;

}
