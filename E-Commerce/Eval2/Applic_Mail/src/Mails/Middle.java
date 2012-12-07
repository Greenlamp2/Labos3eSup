/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Mails;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;


public class Middle {
    public Middle(){

    }

    public static void sendMessage(Messages message){
        Smtp smtp = new Smtp();
        smtp.init();
        smtp.newMessage();
        smtp.setDestinateur(message.getFrom());
        smtp.setDestinataire(message.getTo());
        smtp.setObjet(message.getSujet());
        if(message.getNbPiecesJointes() == 0){
            smtp.setMessage(message.getMessage());
        }else{
            smtp.addPartMessage(message.getMessage());
            for(File file : message.getPiecesJointes()){
                smtp.addPartFichier(file);
            }
        }
        smtp.sendIt();
    }

    public static LinkedList<Messages> receiveMessages(){
        LinkedList<Messages> listeMessage = new LinkedList<>();
        Pop3 pop3 = new Pop3();
        pop3.init();
        pop3.initFolder("INBOX");
        Message[] messages = pop3.receiveMessage();
        buildListe(messages, listeMessage);
        return listeMessage;
    }

    private static void buildListe(Message[] messages, LinkedList<Messages> listeMessage) {
        Messages nouveauMessage = new Messages();
        try {
            for(Message message : messages){
                if(message.getContentType().contains("multipart")){
                    Multipart multipart = (Multipart)message.getContent();
                    int nbPart = multipart.getCount();

                    for(int i=0; i<nbPart; i++){
                        Part part = multipart.getBodyPart(i);
                        String attachment = part.getDescription();
                        if(part.isMimeType("text/plain")){
                            nouveauMessage.setMessage((String)part.getContent());
                        }
                        if(attachment != null && attachment.equalsIgnoreCase(Part.ATTACHMENT)){
                            InputStream is = part.getInputStream();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            int buffer;
                            while((buffer = is.read()) != -1){
                                baos.write(buffer);
                            }
                            baos.flush();
                            String nameFile = part.getFileName();
                            File file = File.createTempFile("Binary", nameFile);
                            file
                            nouveauMessage.addPiecesJointes(file);

                        }
                    }
                }
            }
        } catch (MessagingException ex) {
            Logger.getLogger(Middle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Middle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
