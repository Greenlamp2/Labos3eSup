/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import ant.Clients;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class ClientsFacade extends AbstractFacade<Clients> implements ClientsFacadeLocal {
    @PersistenceContext(unitName = "ExamenApplication-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientsFacade() {
        super(Clients.class);
    }

    @Override
    public Clients getByName(String nameClient) throws Exception{
        return em.createNamedQuery("Clients.findByNom", Clients.class).setParameter("nom", nameClient).getSingleResult();
    }

}
