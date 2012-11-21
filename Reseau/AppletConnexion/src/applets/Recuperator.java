/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applets;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Greenlamp
 */
public class Recuperator implements Runnable{
    private JLabel label = null;
    private Totalisateur totalisateur = null;
    public Recuperator(JLabel label, Totalisateur totalisateur){
        this.label = label;
        this.totalisateur = totalisateur;
    }
	
    @Override
    public void run() {
        try {
            while(true){
                int value = totalisateur.getTotal();
                label.setText(String.valueOf(value));
                totalisateur.reset();
                Thread.sleep(5000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Recuperator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
