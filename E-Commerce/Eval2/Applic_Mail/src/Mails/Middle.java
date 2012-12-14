/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Mails;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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
        String from = message.getFrom();
        if(from != null){
            smtp.setDestinateur(message.getFrom());
        }
        smtp.setDestinataire(message.getTo());
        smtp.setObjet(message.getSujet());
        if(message.getNbPieceJointes() == 0){
            smtp.setMessage(message.getMessage());
        }else{
            smtp.addPartMessage(message.getMessage());
            for(PieceJointes piece : message.getPiecesJointes()){
                if(piece.isByte()){
                    smtp.addPartFichier(piece.getBytes(), piece.getName());
                }else{
                    smtp.addPartFichier(piece.getFile());
                }
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

        try {
            for(Message message : messages){
                Messages nouveauMessage = new Messages();
                Object temp = message.getFrom();
                if(temp != null){
                    nouveauMessage.setFrom(""+message.getFrom()[0]);
                }
                nouveauMessage.setSujet(message.getSubject());
                if(message.getSentDate() != null){
                    nouveauMessage.setSentDate(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(message.getSentDate()));
                }

                if(message.getContentType().contains("multipart")){
                    Multipart multipart = (Multipart)message.getContent();
                    int nbPart = multipart.getCount();

                    for(int i=0; i<nbPart; i++){
                        Part part = multipart.getBodyPart(i);
                        String attachment = part.getDisposition();
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
                            nouveauMessage.addPieceJointes(nameFile, baos.toByteArray());
                            baos.close();
                        }
                    }
                }else{
                    nouveauMessage.setMessage((String)message.getContent());
                }
                listeMessage.add(nouveauMessage);
            }
        } catch (MessagingException ex) {
            Logger.getLogger(Middle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Middle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
