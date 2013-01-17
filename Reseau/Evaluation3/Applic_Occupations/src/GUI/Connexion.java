/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Helpers.EasyFile;
import Protocole.NetworkClient;
import Protocole.PacketCom;
import Protocole.RLP;
import Securite.MyCertificate;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.Random;
import javax.crypto.SecretKey;

/**
 *
 * @author Greenlamp
 */
public class Connexion extends javax.swing.JFrame {

    /**
     * Creates new form Connexion
     */
    NetworkClient socket;
    int port;
    String host;
    KeyStore ks;
    KeyStore ksSsl;
    MyCertificate myCertificate;
    MyCertificate myCertificateSsl;
    String login;
    String password;
    SecretKey cleSession;

    public Connexion() {
        initComponents();
        host = EasyFile.getConfig("Configs_Serveur_Occupations", "HOST");
        port = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Occupations", "PORT_VILLAGES_MOTELS"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Glogin = new javax.swing.JTextField();
        Gpassword = new javax.swing.JTextField();
        Bconnexion = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        Gstatus = new javax.swing.JLabel();
        Bload = new javax.swing.JButton();
        GpassKs = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        GstatusSsl = new javax.swing.JLabel();
        BloadSSL = new javax.swing.JButton();
        GpassKsSsl = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Module de connexion");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(82, 82, 82))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setText("Login: ");

        jLabel3.setText("Password: ");

        Glogin.setText("admin");
        Glogin.setEnabled(false);

        Gpassword.setText("admin");
        Gpassword.setEnabled(false);

        Bconnexion.setText("Se connecter");
        Bconnexion.setEnabled(false);
        Bconnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BconnexionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Glogin)
                    .addComponent(Bconnexion)
                    .addComponent(Gpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Glogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Gpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Bconnexion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel4.setText("KeyStore:");

        Gstatus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Gstatus.setForeground(new java.awt.Color(255, 0, 0));
        Gstatus.setText("not loaded");

        Bload.setText("charger");
        Bload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BloadActionPerformed(evt);
            }
        });

        GpassKs.setText("lolilol");

        jLabel5.setText("SSL:");

        GstatusSsl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        GstatusSsl.setForeground(new java.awt.Color(255, 0, 0));
        GstatusSsl.setText("not loaded");

        BloadSSL.setText("charger");
        BloadSSL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BloadSSLActionPerformed(evt);
            }
        });

        GpassKsSsl.setText("lolilol");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(Gstatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Bload)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GpassKs, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(GstatusSsl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BloadSSL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GpassKsSsl)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Gstatus)
                    .addComponent(Bload)
                    .addComponent(GpassKs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(GstatusSsl)
                    .addComponent(BloadSSL)
                    .addComponent(GpassKsSsl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
    }// </editor-fold>//GEN-END:initComponents

    private void BconnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BconnexionActionPerformed
        if(verifChamps()){
            initConnexion();
            socket = new NetworkClient(host, port, myCertificate, myCertificateSsl);
            if(!socket.isConnected()){
                JOptionPane.showMessageDialog(this, "Impossible de contacter le serveur");
            }else{
                this.login = Glogin.getText();
                this.password = Gpassword.getText();
                PacketCom packet = new PacketCom(RLP.LOGIN, null);
                socket.send(packet);
                PacketCom packetReponse;
                try {
                    packetReponse = socket.receive();
                    traitementPacket(packetReponse);
                } catch (Exception ex) {
                    Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_BconnexionActionPerformed

    private void BloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BloadActionPerformed
        String path = EasyFile.getConfig("Configs_Serveur_Occupations", "ADRESSE_KS_NO_SSL");
        File fichierKeyStore = new File(path);
        try {
            ks = KeyStore.getInstance("PKCS12", "BC");
            String passKs = new String(GpassKs.getPassword());
            ks.load(new FileInputStream(fichierKeyStore), passKs.toCharArray());
            Glogin.setEnabled(true);
            Gpassword.setEnabled(true);
            Bconnexion.setEnabled(true);
            Gstatus.setText("Loaded");
            Gstatus.setForeground(Color.GREEN);
            Bload.setEnabled(false);
            GpassKs.setEnabled(false);
        } catch (KeyStoreException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BloadActionPerformed

    private void BloadSSLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BloadSSLActionPerformed
        String pathSsl = EasyFile.getConfig("Configs_Serveur_Occupations", "ADRESSE_KS_SSL");
        File fichierKeyStoreSsl = new File(pathSsl);
        try {
            ksSsl = KeyStore.getInstance("JKS");
            String passKsSsl = new String(GpassKsSsl.getPassword());
            ksSsl.load(new FileInputStream(fichierKeyStoreSsl), passKsSsl.toCharArray());
            Glogin.setEnabled(true);
            Gpassword.setEnabled(true);
            Bconnexion.setEnabled(true);
            GstatusSsl.setText("Loaded");
            GstatusSsl.setForeground(Color.GREEN);
            BloadSSL.setEnabled(false);
            GpassKsSsl.setEnabled(false);
        } catch (KeyStoreException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BloadSSLActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Connexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Connexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Connexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Connexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Connexion().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bconnexion;
    private javax.swing.JButton Bload;
    private javax.swing.JButton BloadSSL;
    private javax.swing.JTextField Glogin;
    private javax.swing.JPasswordField GpassKs;
    private javax.swing.JPasswordField GpassKsSsl;
    private javax.swing.JTextField Gpassword;
    private javax.swing.JLabel Gstatus;
    private javax.swing.JLabel GstatusSsl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables


    private boolean verifChamps() {
        String login = Glogin.getText();
        String pass = Gpassword.getText();
        if(login.isEmpty()){
            JOptionPane.showMessageDialog(this, "Le login est incorrect");
            return false;
        }else if(pass.isEmpty()){
            JOptionPane.showMessageDialog(this, "Le password est incorrect");
            return false;
        }
        return true;
    }

    private void traitementPacket(PacketCom packetReponse) {
        String type = packetReponse.getType();
        Object contenu = packetReponse.getObjet();
        if(type.equalsIgnoreCase(RLP.NONCE)){
            int number1 = (Integer) contenu;
            int number2 = new Random().nextInt(10000);
            byte[] digest = generateDigest(login, password, number1, number2);
            Object[] infos = {login, digest, number2};
            socket.send(new PacketCom(RLP.LOGIN_NEXT_STEP, (Object)infos));
            PacketCom packetRetour;
            try {
                packetRetour = socket.receive();
                traitementPacket(packetRetour);
            } catch (Exception ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(type.equalsIgnoreCase(RLP.LOGIN_OUI)){
            Main main = new Main(socket, myCertificate, myCertificateSsl);
            main.setVisible(true);
            this.dispose();
        }else if(type.equalsIgnoreCase(RLP.LOGIN_NON)){
            JOptionPane.showMessageDialog(this, "Connexion échouée");
        }
    }

    private byte[] generateDigest(String login, String password, int number1, int number2) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1", "BC");
            md.update(String.valueOf(number1).getBytes());
            md.update(login.getBytes());
            md.update(password.getBytes());
            md.update(String.valueOf(number2).getBytes());
            byte[] digest = md.digest();
            return digest;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void initConnexion() {
        myCertificate = new MyCertificate();
        myCertificateSsl = new MyCertificate();
        try {
            myCertificate.setCertificate((X509Certificate) ks.getCertificate("client"));
            myCertificate.getCertificate().checkValidity();
            String passKs = new String(GpassKs.getPassword());
            myCertificate.setPrivateKey((PrivateKey) ks.getKey("client", passKs.toCharArray()));
            myCertificate.setKeystore(ks);
            myCertificate.setPassword(passKs);

            myCertificateSsl.setCertificate((X509Certificate) ksSsl.getCertificate("client"));
            myCertificateSsl.getCertificate().checkValidity();
            String passKsSsl = new String(GpassKsSsl.getPassword());
            myCertificateSsl.setPrivateKey((PrivateKey) ksSsl.getKey("client", passKsSsl.toCharArray()));
            myCertificateSsl.setKeystore(ksSsl);
            myCertificateSsl.setPassword(passKsSsl);

        } catch (KeyStoreException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateExpiredException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateNotYetValidException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}