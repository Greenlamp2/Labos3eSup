/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applets;

/**
 *
 * @author Greenlamp
 */
public class Totalisateur {
    private int total = 0;

    public synchronized void add(int value){
        total += value;
    }

    public synchronized int getTotal(){
        return total;
    }

    public synchronized void reset(){
        this.total = 0;
    }

}
