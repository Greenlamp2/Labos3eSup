/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Facades;

import ant.Pions;
import ant.Plateaux;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class PlateauxFacade extends AbstractFacade<Plateaux> implements PlateauxFacadeLocal {
    @PersistenceContext(unitName = "Chess_Application-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlateauxFacade() {
        super(Plateaux.class);
    }

    public Plateaux findByName(String nomPlateau){
        return em.createNamedQuery("Plateaux.findByNomPlateau",Plateaux.class).setParameter("nomPlateau", nomPlateau).getSingleResult();
    }

}
