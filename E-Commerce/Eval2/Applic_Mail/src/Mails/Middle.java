/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Mails;

import java.io.File;


public class Middle {
    public Middle(){

    }

    public static void sendMessage(Message message){
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
}
