/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Serveur;

import Helpers.EasyFile;


public class Main {
    public static void main(String args[]) {
        int portActivite = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Activites", "PORT_ACTIVITES"));
        int portStat = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Activites", "PORT_STAT"));
        Thread MyThread = new Thread(new ServeurPool(portActivite));
        Thread MyThreadStat = new Thread(new ServeurPoolStat(portStat));
        MyThread.start();
        MyThreadStat.start();
    }
}
