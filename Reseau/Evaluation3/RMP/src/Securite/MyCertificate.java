/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Securite;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;


public class MyCertificate implements Serializable{
    private PrivateKey privateKey;
    private X509Certificate certificate;

    public MyCertificate(){

    }

    public MyCertificate(X509Certificate certif){
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

}
