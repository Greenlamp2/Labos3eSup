/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Securite;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.crypto.SecretKey;


public class MyKeys {
    private PrivateKey clePrivee;
    private PublicKey clePublic;
    private SecretKey cleSession;
    private X509Certificate certificate;

    public MyKeys(){

    }

    public MyKeys(PrivateKey prK, PublicKey pk, SecretKey sk, X509Certificate certif){
        setClePrivee(prK);
        setClePublic(pk);
        setCleSession(sk);
        setCertificate(certificate);
    }

    public PrivateKey getClePrivee() {
        return clePrivee;
    }

    public void setClePrivee(PrivateKey clePrivee) {
        this.clePrivee = clePrivee;
    }

    public PublicKey getClePublic() {
        return clePublic;
    }

    public void setClePublic(PublicKey clePublic) {
        this.clePublic = clePublic;
    }

    public SecretKey getCleSession() {
        return cleSession;
    }

    public void setCleSession(SecretKey cleSession) {
        this.cleSession = cleSession;
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }
}
