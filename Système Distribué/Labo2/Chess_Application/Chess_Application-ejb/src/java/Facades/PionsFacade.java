/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Facades;

import ant.Pions;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class PionsFacade extends AbstractFacade<Pions> implements PionsFacadeLocal {
    @PersistenceContext(unitName = "Chess_Application-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PionsFacade() {
        super(Pions.class);
    }

    @Override
    public List<Pions> findByNomPlateau(String nomPlateau){
        return em.createNamedQuery("Pions.findByNomPlateau",Pions.class).setParameter("nomPlateau", nomPlateau).getResultList();
    }

    @Override
    public Pions findByNomCouleurNomPlateauXY(String nom, int couleur, String nomPlateau, int x, int y){
        return em.createNamedQuery("Pions.findByNomCouleurPlateauXY",Pions.class).setParameter("nomPion", nom).setParameter("couleur", couleur).setParameter("nomPlateau", nomPlateau).setParameter("positionX", x).setParameter("positionY", y).getSingleResult();
    }

    @Override
    public Pions findByPositionXY(int x, int y) throws Exception{
        try{
            return em.createNamedQuery("Pions.findByPositionXY",Pions.class).setParameter("positionX", x).setParameter("positionY", y).getSingleResult();
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

}
