/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Mails;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PieceJointes {
    File file;
    byte[] bytes;
    private String name;

    public PieceJointes(){

    }
    public PieceJointes(File file, String name){
        this.file = file;
        this.name = name;
    }
    public PieceJointes(byte[] bytes, String name){
        this.bytes = bytes;
        this.name = name;
    }
    public File getFile(){
        return this.file;
    }
    public byte[] getBytes(){
        return this.bytes;
    }

    public void bytesToFile(String name){
        try {
            FileOutputStream fos = new FileOutputStream(name);
            fos.write(bytes);
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(PieceJointes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void fileToBytes(){
        FileInputStream fis = null;
        try {
            bytes = new byte[(int)file.length()];
            fis = new FileInputStream(file);
            fis.read(bytes);
            fis.close();
        } catch (Exception ex) {
            Logger.getLogger(PieceJointes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setFile(File file){
        this.file = file;
    }
    public void setBytes(byte[] bytes){
        this.bytes = bytes;
    }
    public void setBytes(File file){
        int taille = (int)file.length();
        bytes = new byte[taille];
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.read(bytes, 0, taille);
            bis.close();
        } catch (Exception ex) {
            Logger.getLogger(PieceJointes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean isByte(){
        if(this.bytes == null){
            return false;
        }else{
            return true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

