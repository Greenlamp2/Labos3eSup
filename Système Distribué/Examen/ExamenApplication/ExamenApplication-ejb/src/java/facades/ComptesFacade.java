/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import ant.Clients;
import ant.Comptes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class ComptesFacade extends AbstractFacade<Comptes> implements ComptesFacadeLocal {
    @PersistenceContext(unitName = "ExamenApplication-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComptesFacade() {
        super(Comptes.class);
    }

    @Override
    public Comptes getByIdClient(int idClient) throws Exception{
        return em.createNamedQuery("Comptes.findByIdClient", Comptes.class).setParameter("idClient", idClient).getSingleResult();
    }

}
