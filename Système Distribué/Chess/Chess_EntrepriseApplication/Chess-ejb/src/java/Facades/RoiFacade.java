/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Facades;

import ant.Roi;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class RoiFacade extends AbstractFacade<Roi> implements RoiFacadeLocal {
    @PersistenceContext(unitName = "Chess-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoiFacade() {
        super(Roi.class);
    }

}
