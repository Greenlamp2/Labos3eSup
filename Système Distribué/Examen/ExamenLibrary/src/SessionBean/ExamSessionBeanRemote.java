/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean;

import javax.ejb.Remote;

/**
 *
 * @author Greenlamp
 */
@Remote
public interface ExamSessionBeanRemote {

    /********************************************************************/
    //Comptes
    /********************************************************************/
    void createCompte(String nomClient, int sommeInitial) throws Exception;

    /********************************************************************/
    //Clients
    /********************************************************************/
    void createClient(String nomClient) throws Exception;
    void transfertArgent(String nomSender, String nomReceiver, int somme) throws Exception;

    /********************************************************************/
    //Glassfish
    /********************************************************************/
    String getUser() throws Exception;

}
