/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Threads;

import GUI.Reception;
import Mails.Messages;
import Mails.Middle;
import java.util.LinkedList;
import javax.swing.JOptionPane;


public class ThreadNouveauMessages implements Runnable{
    int nb;
    Reception parent;
    public ThreadNouveauMessages(Reception parent, int nbMessageActuel){
        this.parent = parent;
        this.nb = nbMessageActuel;
    }

    @Override
    public void run() {
        try{
        while(true){
            LinkedList<Messages> receiveMessages = Middle.receiveMessages();
            if(receiveMessages.size() > nb){
                JOptionPane.showMessageDialog(parent, "nouveau(x) message(s) reçu.");
                this.nb = receiveMessages.size();
            }
            Thread.sleep(10000 * 3);
        }
        }catch(Exception ex){
            System.err.println("exception: " + ex.getMessage());
        }
    }

}
