/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Facades;

import ant.Joueur;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class JoueurFacade extends AbstractFacade<Joueur> implements JoueurFacadeLocal {
    @PersistenceContext(unitName = "Chess-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JoueurFacade() {
        super(Joueur.class);
    }

}
