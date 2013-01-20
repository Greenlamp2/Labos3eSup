/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Securite;

import java.io.Serializable;
import java.security.cert.X509Certificate;


public class KeyExchange implements Serializable{
    private X509Certificate certificatClient;
    private X509Certificate certificatServeur;
    private byte[] cleSession;

    public KeyExchange(){

    }

    public X509Certificate getCertificatClient() {
        return certificatClient;
    }

    public void setCertificatClient(X509Certificate certificatClient) {
        this.certificatClient = certificatClient;
    }

    public X509Certificate getCertificatServeur() {
        return certificatServeur;
    }

    public void setCertificatServeur(X509Certificate certificatServeur) {
        this.certificatServeur = certificatServeur;
    }

    public byte[] getCleSession() {
        return cleSession;
    }

    public void setCleSession(byte[] cleSession) {
        this.cleSession = cleSession;
    }

}
