/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Mails;

import Helpers.EasyFile;
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class Smtp {
    String host;
    int port;
    String user;
    String pass;

    Session session;
    MimeMessage mimeMessage;
    String from;
    String to;
    String objet;
    String message;
    boolean multipart;
    boolean correct;
    Multipart messageMultipart;

    public Smtp(){
        this.host = EasyFile.getConfig("Configs_Applic_mail", "host_smtp");
        this.port = Integer.parseInt(EasyFile.getConfig("Configs_Applic_mail", "port_smtp"));
        this.user = EasyFile.getConfig("Configs_Applic_mail", "user_smtp");
        this.pass = EasyFile.getConfig("Configs_Applic_mail", "pass_smtp");
        correct = true;

        messageMultipart = null;
    }

    public void init(){
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        session = Session.getDefaultInstance(prop, null);
    }

    public void newMessage(){
        mimeMessage = new MimeMessage(session);
    }

    public void setDestinateur(String from){
        this.from = from;
        try {
            mimeMessage.setFrom(new InternetAddress(this.from));
        } catch (AddressException ex) {
            System.err.println("Le format de l'adresse est incorrecte");
            correct = false;
        } catch (MessagingException ex) {
            Logger.getLogger(Smtp.class.getName()).log(Level.SEVERE, null, ex);
            correct = false;
        }
    }

    public void setDestinataire(String to){
        this.to = to;
        try {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(this.to));
        } catch (AddressException ex) {
            System.err.println("Le format de l'adresse est incorrecte");
            correct = false;
        } catch (MessagingException ex) {
            Logger.getLogger(Smtp.class.getName()).log(Level.SEVERE, null, ex);
            correct = false;
        }

    }

    public void setObjet(String objet){
        this.objet = objet;
        try {
            mimeMessage.setSubject(this.objet);
        } catch (MessagingException ex) {
            Logger.getLogger(Smtp.class.getName()).log(Level.SEVERE, null, ex);
            correct = false;
        }
    }

    public void setMessage(String message){
        multipart = false;
        this.message = message;
        try {
            mimeMessage.setText(this.message);
        } catch (MessagingException ex) {
            Logger.getLogger(Smtp.class.getName()).log(Level.SEVERE, null, ex);
            correct = false;
        }
    }

    public void addPartMessage(String message){
        if(messageMultipart == null){
            messageMultipart = new MimeMultipart();
            multipart = true;
        }
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        try {
            mimeBodyPart.setText(message);
            messageMultipart.addBodyPart(mimeBodyPart);
        } catch (MessagingException ex) {
            Logger.getLogger(Smtp.class.getName()).log(Level.SEVERE, null, ex);
            correct = false;
        }
    }

    public void addPartFichier(String path){
        if(messageMultipart == null){
            messageMultipart = new MimeMultipart();
            multipart = true;
        }
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        DataSource dataSource = new FileDataSource(path);
        try {
            mimeBodyPart.setDataHandler(new DataHandler(dataSource));
            mimeBodyPart.setFileName(path);
            messageMultipart.addBodyPart(mimeBodyPart);
        } catch (MessagingException ex) {
            Logger.getLogger(Smtp.class.getName()).log(Level.SEVERE, null, ex);
            correct = false;
        }
    }

    public void addPartFichier(File file){
        if(messageMultipart == null){
            messageMultipart = new MimeMultipart();
            multipart = true;
        }
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        DataSource dataSource = new FileDataSource(file);
        try {
            mimeBodyPart.setDataHandler(new DataHandler(dataSource));
            mimeBodyPart.setFileName(file.getName());
            messageMultipart.addBodyPart(mimeBodyPart);
        } catch (MessagingException ex) {
            Logger.getLogger(Smtp.class.getName()).log(Level.SEVERE, null, ex);
            correct = false;
        }
    }

    public void sendIt(){
        if(!correct){
            System.err.println("Le message n'est pas bien formé.");
            return;
        }
        if(!multipart){
            try {
                Transport.send(mimeMessage);
            } catch (MessagingException ex) {
                Logger.getLogger(Smtp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                mimeMessage.setContent(messageMultipart);
                Transport.send(mimeMessage);
            } catch (MessagingException ex) {
                Logger.getLogger(Smtp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
