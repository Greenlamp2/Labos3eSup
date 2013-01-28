/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import ant.Clients;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Greenlamp
 */
@Local
public interface ClientsFacadeLocal {

    void create(Clients clients);

    void edit(Clients clients);

    void remove(Clients clients);

    Clients find(Object id);

    List<Clients> findAll();

    List<Clients> findRange(int[] range);

    int count();

    Clients getByName(String nameClient) throws Exception;

}
