/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Facades;

import Exceptions.ExceptionInterceptor;
import ant.Plateau;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class PlateauFacade extends AbstractFacade<Plateau> implements PlateauFacadeLocal {
    @PersistenceContext(unitName = "Chess-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlateauFacade() {
        super(Plateau.class);
    }

    @Interceptors(ExceptionInterceptor.class)
    public Plateau getByNom(String nom) throws Exception{
        return em.createNamedQuery("Plateau.findByNom", Plateau.class).setParameter("nom", nom).getSingleResult();
    }

}
