/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Exceptions.ExceptionInterceptor;
import Facades.PlateauFacadeLocal;
import ant.Plateau;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.emptyType;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author Greenlamp
 */
@Stateful
public class SessionBean implements SessionBeanRemote {
    @EJB
    private PlateauFacadeLocal plateauFacade;
    @PersistenceUnit
    EntityManagerFactory factory;
    @PersistenceContext
    EntityManager entityManager;



    /**********************************************************************/
    /*Plateau*/
    /**********************************************************************/
    @Override
    public List<Plateau> getPlateaux(){
        return plateauFacade.findAll();
    }

    @Override
    public void createPlateau(String nom){
        Plateau plateau = new Plateau();
        plateau.init(nom);
        plateauFacade.create(plateau);
    }

    @Override
    @Interceptors(ExceptionInterceptor.class)
    public Plateau getByNom(String nom) throws Exception{
        return plateauFacade.getByNom(nom);
    }
}
