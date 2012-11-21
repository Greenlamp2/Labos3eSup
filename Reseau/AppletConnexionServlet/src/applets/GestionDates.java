/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Greenlamp
 */
public class GestionDates implements Runnable{
    private JLabel label = null;
    private String tz = null;
    private int nbAleatoire = 0;

    public GestionDates(JLabel labelDate, String timezone){
        label = labelDate;
        tz = timezone;
    }

    @Override
    public void run() {
        System.out.println("Thread Date lancé");
        try {
            while(true){
                Date date = new Date();
                TimeZone timeZone = TimeZone.getTimeZone(tz);
                DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.FRANCE);
                dateFormat.setTimeZone(timeZone);
                String dateWithFormat = dateFormat.format(date);
                label.setText(dateWithFormat);
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(GestionDates.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public synchronized int getNbAlea(){
        return nbAleatoire;
    }

}
