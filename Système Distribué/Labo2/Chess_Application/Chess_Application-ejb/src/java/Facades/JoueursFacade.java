/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Facades;

import ant.Joueurs;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class JoueursFacade extends AbstractFacade<Joueurs> implements JoueursFacadeLocal {
    @PersistenceContext(unitName = "Chess_Application-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JoueursFacade() {
        super(Joueurs.class);
    }

    @Override
    public List<Joueurs> findByNomPlateau(String nomPlateau){
        return em.createNamedQuery("Joueurs.findByNomPlateau",Joueurs.class).setParameter("nomPlateau", nomPlateau).getResultList();
    }

    @Override
    public Joueurs findByCouleurNomPlateau(int couleur, String nomPlateau){
        return em.createNamedQuery("Joueurs.findByCouleurNomPlateau",Joueurs.class).setParameter("couleur", couleur).setParameter("nomPlateau", nomPlateau).getSingleResult();
    }

}
