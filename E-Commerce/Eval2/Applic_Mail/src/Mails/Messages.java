/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Mails;

import java.io.File;
import java.util.LinkedList;


public class Messages {
    private String from;
    private String to;
    private String sujet;
    private String message;
    private LinkedList<File> piecesJointes;

    public Messages(){
        from = null;
        to = null;
        sujet = null;
        message = null;
        piecesJointes = new LinkedList<>();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LinkedList<File> getPiecesJointes() {
        return piecesJointes;
    }

    public void setPiecesJointes(LinkedList<File> piecesJointes) {
        this.piecesJointes = piecesJointes;
    }

    public void addPiecesJointes(File file){
        this.piecesJointes.add(file);
    }

    public int getNbPiecesJointes(){
        return this.piecesJointes.size();
    }

}
