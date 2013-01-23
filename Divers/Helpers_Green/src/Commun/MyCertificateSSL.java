/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Commun;

import java.io.Serializable;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;


public class MyCertificateSSL implements Serializable{
    private PrivateKey privateKey;
    private X509Certificate certificate;
    private KeyStore keystore;
    private String password;

    public MyCertificateSSL(){

    }

    public MyCertificateSSL(X509Certificate certif){
        setCertificate(certif);
        setPrivateKey(null);
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }

    public KeyStore getKeystore() {
        return keystore;
    }

    public void setKeystore(KeyStore keystore) {
        this.keystore = keystore;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
