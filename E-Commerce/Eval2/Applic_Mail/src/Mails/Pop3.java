/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Mails;

import Helpers.EasyFile;


public class Pop3 {
    String host;
    int port;
    String user;
    String pass;
    
    public Pop3(){
        this.host = EasyFile.getConfig("Configs_Applic_mail", "host_smtp");
        this.port = Integer.parseInt(EasyFile.getConfig("Configs_Applic_mail", "port_smtp"));
        this.user = EasyFile.getConfig("Configs_Applic_mail", "user_smtp");
        this.pass = EasyFile.getConfig("Configs_Applic_mail", "pass_smtp");
    }

}
