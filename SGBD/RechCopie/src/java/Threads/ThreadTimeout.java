/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Threads;

import Beans.BeanCaddy;
import java.io.Serializable;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class ThreadTimeout implements Runnable, Serializable{
    Date dateCreation = null;
    HttpSession session = null;
    ServletContext sc = null;

    public ThreadTimeout(HttpSession session, ServletContext sc) {
        this.session = session;
        this.sc = sc;
    }

    @Override
    public void run() {
        System.out.println("Thread TimeOUT démarré !");
        while(true){
            try {
                Thread.sleep(1000 * 60 * 20); // 20 minutes
                timeOut();
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadTimeout.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void timeOut() {
        System.out.println("TIMEOUT !");
        String idSession = session.getId();
        this.sc.removeAttribute(idSession);
        session.removeAttribute("beanCaddy");
        BeanCaddy beanCaddy = new BeanCaddy();
        this.sc.setAttribute(idSession, beanCaddy);
        session.setAttribute("beanCaddy", beanCaddy);
    }
}