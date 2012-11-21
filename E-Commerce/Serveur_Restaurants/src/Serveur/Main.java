/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Serveur;


public class Main {
    public static void main(String args[]) {
        Thread MyThread = new Thread(new ServeurMono());
        MyThread.start();
        Thread MyThread2 = new Thread(new ServeurALD());
        MyThread2.start();
    }
}
