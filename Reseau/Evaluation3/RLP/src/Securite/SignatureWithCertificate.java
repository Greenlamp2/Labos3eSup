/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Securite;

import java.io.Serializable;
import java.security.cert.X509Certificate;


public class SignatureWithCertificate implements Serializable{
    private X509Certificate certificate;
    private byte[] signature;

    public SignatureWithCertificate(){

    }

    public SignatureWithCertificate(X509Certificate certif, byte[] sig){
        setCertificate(certif);
        setSignature(sig);
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
}
