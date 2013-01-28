/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean;

import Exceptions.ExceptionInterceptor;
import ant.Clients;
import ant.Comptes;
import facades.ClientsFacadeLocal;
import facades.ComptesFacadeLocal;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;

/**
 *
 * @author Greenlamp
 */
@Stateful
@DeclareRoles({"client", "admin"})
public class ExamSessionBean implements ExamSessionBeanRemote {
    @EJB
    private ComptesFacadeLocal comptesFacade;
    @EJB
    private ClientsFacadeLocal clientsFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    /********************************************************************/
    //Comptes
    /********************************************************************/
    @Override
    @RolesAllowed("client")
    @Interceptors(ExceptionInterceptor.class)
    public void createCompte(String nomClient, int sommeInitial) throws Exception {
        Clients client = clientsFacade.getByName(nomClient);
        if(client == null){
            return;
        }
        Comptes compte = new Comptes();
        compte.setIdCompte(Integer.MIN_VALUE);
        compte.setIdClient(client.getIdClient());
        compte.setSomme(sommeInitial);

        comptesFacade.create(compte);
    }
    @Override
    @RolesAllowed("client")
    @Interceptors(ExceptionInterceptor.class)
    public void transfertArgent(String nomSender, String nomReceiver, int somme) throws Exception {
        Clients clientSender = clientsFacade.getByName(nomSender);
        if(clientSender == null){
            return;
        }

        Clients clientReceiver = clientsFacade.getByName(nomReceiver);
        if(clientReceiver == null){
            return;
        }

        Comptes compteSender = comptesFacade.getByIdClient(clientSender.getIdClient());
        Comptes compteReceiver = comptesFacade.getByIdClient(clientReceiver.getIdClient());

        compteSender.setSomme(compteSender.getSomme() - somme);
        compteReceiver.setSomme(compteReceiver.getSomme() + somme);

        comptesFacade.edit(compteSender);
        comptesFacade.edit(compteReceiver);
    }



    /********************************************************************/
    //Clients
    /********************************************************************/
    @Override
    @RolesAllowed("client")
    @Interceptors(ExceptionInterceptor.class)
    public void createClient(String nomClient) throws Exception {
        Clients client = new Clients();
        client.setIdClient(Integer.MIN_VALUE);
        client.setNom(nomClient);

        clientsFacade.create(client);
    }

    /********************************************************************/
    //Glassfish
    /********************************************************************/
    @Resource SessionContext sc;

    @Override
    public String getUser() throws Exception {
        return sc.getCallerPrincipal().getName();
    }
}
