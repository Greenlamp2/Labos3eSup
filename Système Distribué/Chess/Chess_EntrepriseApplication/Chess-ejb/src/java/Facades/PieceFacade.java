/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Facades;

import ant.Piece;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class PieceFacade extends AbstractFacade<Piece> implements PieceFacadeLocal {
    @PersistenceContext(unitName = "Chess-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PieceFacade() {
        super(Piece.class);
    }

}
