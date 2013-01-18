/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Serveur;

import Helpers.EasyFile;


public class Main {
    public static void main(String args[]) {
        int portGroup = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Chat-Hollidays", "PORT_GROUP"));
        Thread MyThread = new Thread(new ServeurALD(portGroup));
        MyThread.start();
    }
}
