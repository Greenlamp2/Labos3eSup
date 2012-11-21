/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applets;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Greenlamp
 */
public class AppletHolidaysLogin extends javax.swing.JApplet {

    /**
     * Initializes the applet AppletHolidaysLogin
     */
    private boolean register = false;
    private AppletHolidaysPassword app = null;
    @Override
    public void init() {
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
            java.util.logging.Logger.getLogger(AppletHolidaysLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppletHolidaysLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppletHolidaysLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppletHolidaysLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the applet */
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                    startDates();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is called from within the init() method to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Gnon = new javax.swing.JButton();
        Goui = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        Gpass = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Gnom = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Gdate1 = new javax.swing.JLabel();
        Gdate2 = new javax.swing.JLabel();
        Gdate3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Gnumero = new javax.swing.JLabel();

        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(300, 300));

        Gnon.setText("Non");
        Gnon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GnonActionPerformed(evt);
            }
        });

        Goui.setText("Oui");
        Goui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GouiActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Nous nous connaissons ?");

        Gpass.setText("a");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Votre mot de passe: ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Votre nom: ");

        Gnom.setText("gabriel knuts");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Sur le site des vacances");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("(-: Bienvenue :-)");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Brussels: ");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("New York:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Tokyo:");

        Gdate1.setText("a");

        Gdate2.setText("a");

        Gdate3.setText("a");

        jLabel9.setText("Numéro de chance: réduction de 5%:");

        Gnumero.setText("000");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(Goui, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(Gnon, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(86, 86, 86)
                            .addComponent(jLabel5))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(18, 18, 18)
                            .addComponent(Gdate1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(Gdate2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(34, 34, 34)
                            .addComponent(Gdate3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(53, 53, 53)
                            .addComponent(jLabel9)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(Gpass, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                                .addComponent(Gnom)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(Gnumero)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(1, 1, 1)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Gnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Gpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Goui)
                    .addComponent(Gnon))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(Gdate1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(Gdate2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(Gdate3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(Gnumero)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void GouiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GouiActionPerformed
        String nom = null;
        String pass = null;
        String numero = null;
        nom = Gnom.getText();
        pass = Gpass.getText();
        numero = Gnumero.getText();
        if(verifNom(nom)){
            if(verifPass(pass)){
                redirectServlet(nom, pass, numero);
            }
        }
    }//GEN-LAST:event_GouiActionPerformed

    private void GnonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GnonActionPerformed
        if(register == true){
            Gnon.setEnabled(false);
        }else{
            String nom = null;
            nom = Gnom.getText();
            Gpass.setText("");
            if(verifNom(nom)){
                app = (AppletHolidaysPassword) getAppletContext().getApplet("AppletHolidaysPassword");
                app.activer(true);
                app.setNom(nom);
                this.activer(false);
            }
        }
    }//GEN-LAST:event_GnonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Gdate1;
    private javax.swing.JLabel Gdate2;
    private javax.swing.JLabel Gdate3;
    private javax.swing.JTextField Gnom;
    private javax.swing.JButton Gnon;
    private javax.swing.JLabel Gnumero;
    private javax.swing.JButton Goui;
    private javax.swing.JTextField Gpass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables

    private boolean verifNom(String nom) {
        if(nom.isEmpty()){
            JOptionPane.showMessageDialog(this, "Veuillez fournir un nom");
            return false;
        }
        Pattern pattern = Pattern.compile(".*.\\s.*.");
        Matcher matcher = pattern.matcher(nom);
        boolean match = matcher.matches();
        if(!match){
            JOptionPane.showMessageDialog(this, "Format à respecter: nom[ESPACE]prénom.");
            return false;
        }
        return true;
    }

    public void setPass(String pass){
        Gpass.setText(pass);
    }

    private boolean verifPass(String pass) {
        if(pass.isEmpty()){
            JOptionPane.showMessageDialog(this, "Veuillez avoir un mot de passe");
            return false;
        }
        return true;
    }

    private void startDates() {
        String[] zones = {"Europe/Brussels", "America/New_York", "Asia/Tokyo" };
        JLabel[] labels = {Gdate1, Gdate2, Gdate3 };
        ArrayList<Thread> threads = new ArrayList<Thread>();

        for(int i=0; i<3; i++){
            GestionDates gestionDate = new GestionDates(labels[i], zones[i]);
            threads.add(new Thread(gestionDate));
        }
        Recuperator recuperator = new Recuperator(Gnumero, getUrlServletNombre());
        threads.add(new Thread(recuperator));

        for(Thread thread : threads){
            thread.start();
        }
    }

    private void redirectServlet(String user, String pass, String numero) {
        URL url = this.getDocumentBase();
        String protocole = url.getProtocol();
        String host = url.getHost();
        int port = url.getPort();
        String trace = null;
        String servletPath = "/Web_Applic_Reservations/Servlet_Login";
        servletPath += "?user=" + user;
        servletPath += "&pass=" + pass;
        servletPath += "&numero=" + numero;
        if(register){
            servletPath += "&action=register";
        }else{
            servletPath += "&action=login";
        }
        try {
            URL urlServlet = new URL(protocole, host, port, servletPath);
            getAppletContext().showDocument(urlServlet);

        } catch (MalformedURLException ex) {
            Logger.getLogger(AppletHolidaysLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void activer(boolean value) {
        Goui.setEnabled(value);
        Gnon.setEnabled(value);
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    private URL getUrlServletNombre() {
        URL url = getDocumentBase();
        String protocole = url.getProtocol();
        String host = url.getHost();
        int port = url.getPort();

        URL urlServletNombre = null;
        try {
            urlServletNombre = new URL(protocole, host, port, "/Web_Applic_Reservations/Servlet_Nombre");
        } catch (MalformedURLException ex) {
            Logger.getLogger(AppletHolidaysLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return urlServletNombre;

    }
}
