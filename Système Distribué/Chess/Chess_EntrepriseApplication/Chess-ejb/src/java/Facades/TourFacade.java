/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Facades;

import ant.Tour;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class TourFacade extends AbstractFacade<Tour> implements TourFacadeLocal {
    @PersistenceContext(unitName = "Chess-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TourFacade() {
        super(Tour.class);
    }

}
