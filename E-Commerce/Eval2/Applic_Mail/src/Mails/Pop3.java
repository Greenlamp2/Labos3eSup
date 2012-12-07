/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Mails;

import Helpers.EasyFile;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;


public class Pop3 {
    String host;
    int port;
    String user;
    String pass;

    Session session;
    Folder folder;
    Store store;

    public Pop3(){
        this.host = EasyFile.getConfig("Configs_Applic_mail", "host_pop3");
        this.port = Integer.parseInt(EasyFile.getConfig("Configs_Applic_mail", "port_pop3"));
        this.user = EasyFile.getConfig("Configs_Applic_mail", "user_pop3");
        this.pass = EasyFile.getConfig("Configs_Applic_mail", "pass_pop3");
    }

    public void init(){
        Properties prop = System.getProperties();
        prop.put("mail.pop3.host", host);
        prop.put("mail.pop3.port", port);
        session = Session.getDefaultInstance(prop, null);
        try {
            store = session.getStore("pop3");
            store.connect(host, user, pass);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Pop3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Pop3.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void initFolder(String nomFolder){
        try {
            folder = store.getFolder(nomFolder);
            folder.open(Folder.READ_ONLY);
        } catch (MessagingException ex) {
            Logger.getLogger(Pop3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public javax.mail.Message[] receiveMessage(){
        try {
            Message[] message = folder.getMessages();
            return message;
        } catch (MessagingException ex) {
            Logger.getLogger(Pop3.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
