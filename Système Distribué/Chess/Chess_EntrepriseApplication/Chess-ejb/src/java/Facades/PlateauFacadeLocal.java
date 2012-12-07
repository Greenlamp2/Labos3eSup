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

    void create(Plateau plateau) throws Exception;

    void edit(Plateau plateau) throws Exception;

    void remove(Plateau plateau) throws Exception;

    Plateau find(Object id) throws Exception;

    List<Plateau> findAll() throws Exception;

    List<Plateau> findRange(int[] range);

    int count() throws Exception;

    Plateau getByNom(String nom) throws Exception;

}
