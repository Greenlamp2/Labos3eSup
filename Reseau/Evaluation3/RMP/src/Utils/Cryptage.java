/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import Securite.MyCertificate;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


public class Cryptage {
    final public static String provider = "BC";
    final public static String DES = "DES/ECB/PKCS5Padding";
    final public static String RSA = "RSA/ECB/PKCS1Padding";
    MyCertificate myCertificate;
    public Cryptage(){

    }

    public Cryptage(MyCertificate myCertificate){
        this.myCertificate = myCertificate;
    }

    public SecretKey getSessionKey(String methode){
        KeyGenerator cleGen = null;
        try {
            cleGen = KeyGenerator.getInstance(methode, "BC");
            cleGen.init(new SecureRandom());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cleGen.generateKey();
    }

    public byte[] cleSessionToByte(SecretKey cleSession){
        byte[] cleSessionByte = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(baos);
            out.writeObject((Object)cleSession);
            cleSessionByte = baos.toByteArray();
            out.close();
            baos.close();
        } catch (Exception ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cleSessionByte;
    }

    public SecretKey byteToCleSession(byte[] cleSessionByte){
        SecretKey cleSession = null;
        ObjectInput in;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(cleSessionByte);
            in = new ObjectInputStream(bais);
            cleSession = (SecretKey) in.readObject();
            in.close();
            bais.close();
        } catch (IOException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cleSession;
    }

    public byte[] crypt(String methode, byte[] cleSession, PublicKey clePublic){
        byte[] retour = null;
        try{
            Cipher chiffrement = Cipher.getInstance(methode, "BC");
            chiffrement.init(Cipher.ENCRYPT_MODE, clePublic);
            retour = chiffrement.doFinal(cleSession);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retour;
    }

    public byte[] crypt(String methode, byte[] contenu, SecretKey cleSession){
        byte[] retour = null;
        try{
            Cipher chiffrement = Cipher.getInstance(methode, "BC");
            chiffrement.init(Cipher.ENCRYPT_MODE, cleSession);
            retour = chiffrement.doFinal(contenu);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retour;
    }

    public byte[] decrypt(String methode, byte[] value, Key cle){
        byte[] retour = null;
        Cipher chiffrement;
        try {
            chiffrement = Cipher.getInstance(methode, "BC");
            chiffrement.init(Cipher.DECRYPT_MODE, cle);
            retour = chiffrement.doFinal(value);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Cryptage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retour;
    }

}
