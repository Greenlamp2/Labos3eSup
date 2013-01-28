/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import ant.Comptes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Greenlamp
 */
@Local
public interface ComptesFacadeLocal {

    void create(Comptes comptes);

    void edit(Comptes comptes);

    void remove(Comptes comptes);

    Comptes find(Object id);

    List<Comptes> findAll();

    List<Comptes> findRange(int[] range);

    int count();
    Comptes getByIdClient(int idClient) throws Exception;

}
