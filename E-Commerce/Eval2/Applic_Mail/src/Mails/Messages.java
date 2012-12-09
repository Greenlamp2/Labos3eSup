/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Mails;

import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;


public class Messages {
    private String from;
    private String to;
    private String sujet;
    private String message;
    private LinkedList<PieceJointes> piecesJointes;
    private String sentDate;

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

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public void addPieceJointes(String name, File file){
        PieceJointes pj = new PieceJointes(file, name);
        this.getPiecesJointes().add(pj);
    }

    public void addPieceJointes(String name, byte[] bytes){
        PieceJointes pj = new PieceJointes(bytes, name);
        this.getPiecesJointes().add(pj);
    }

    public int getNbPieceJointes(){
        return this.getPiecesJointes().size();
    }

    public LinkedList<PieceJointes> getPiecesJointes() {
        return piecesJointes;
    }

    public void setPiecesJointes(LinkedList<PieceJointes> piecesJointes) {
        this.piecesJointes = piecesJointes;
    }

    public boolean isMultipart(){
        if(this.piecesJointes.size() > 0){
            return true;
        }else{
            return false;
        }
    }
}
