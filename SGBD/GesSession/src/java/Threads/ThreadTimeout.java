/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Threads;

import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class ThreadTimeout implements Runnable{
    Date dateCreation = null;
    HttpSession session = null;

    public ThreadTimeout(HttpSession session){
        this.session = session;
    }

    @Override
    public void run() {
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
        session.removeAttribute("beanCaddy");
    }
}