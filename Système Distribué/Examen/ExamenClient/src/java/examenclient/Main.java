/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package examenclient;

import SessionBean.ExamSessionBeanRemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Greenlamp
 */
public class Main {
    @EJB
    private static ExamSessionBeanRemote examSessionBean;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("=============Demarrage=============");
        System.out.println("v2");
        if(examSessionBean == null){
            examSessionBean = lookupSession();
        }

        try{
            //examSessionBean.createClient("Gabriel");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        try{
            //examSessionBean.createClient("Kevin");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        try {
            //examSessionBean.createCompte("Gabriel", 100);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            //examSessionBean.createCompte("Kevin", 100);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        try {
            examSessionBean.transfertArgent(examSessionBean.getUser(), "Kevin", 10);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static ExamSessionBeanRemote lookupSession() {
        try {
            Context c = new InitialContext();
            return (ExamSessionBeanRemote) c.lookup("java:global/ExamenApplication/ExamenApplication-ejb/ExamSessionBean");
        } catch (NamingException ne) {
            throw new RuntimeException(ne);
        }
    }
}
