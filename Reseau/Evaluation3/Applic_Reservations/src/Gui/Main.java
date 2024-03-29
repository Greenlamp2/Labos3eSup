/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Helpers.SwingUtils;
import Protocole.NetworkClient;
import Protocole.PacketCom;
import Protocole.RMP;
import Securite.MyKeys;
import Utils.Cryptage;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Greenlamp
 */
public class Main extends javax.swing.JDialog {

    /**
     * Creates new form Main
     */
    NetworkClient socket;
    MyKeys myKeys;
    Cryptage cryptage;

    public Main(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    Main(Connexion parent, NetworkClient socket, MyKeys myKeys) {
        super(parent, true);
        initComponents();
        this.socket = socket;
        this.myKeys = myKeys;
        this.cryptage = new Cryptage();
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        GbRoom = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        GcategorieBroom = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        GtypeBroom = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        GdateBroom = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        GnbNuitBroom = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        GnomBroom = new javax.swing.JTextField();
        Bvalider = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        GpRoom = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        GnumChambreProom = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        GnomProom = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        GcarteProom = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        GdateProom = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        GcRoom = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        GnumChambreCroom = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        GnomCroom = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        GdateCroom = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        GlRooms = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Gliste = new javax.swing.JList();
        jButton3 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Applic_Reservations");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setText("Catégorie:");

        GcategorieBroom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Motel", "Village" }));

        jLabel4.setText("Type de chambre:");

        GtypeBroom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Simple", "Double", "Familiale" }));

        jLabel5.setText("Date d'arrivée:");

        GdateBroom.setText("01/03/2013");

        jLabel6.setText("Nombre de nuits:");

        GnbNuitBroom.setText("2");

        jLabel7.setText("Nom du client:");

        GnomBroom.setText("knuts");

        Bvalider.setText("valider");
        Bvalider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BvaliderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GcategorieBroom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GtypeBroom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GdateBroom)
                    .addComponent(GnbNuitBroom)
                    .addComponent(GnomBroom)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(0, 71, Short.MAX_VALUE))
                    .addComponent(Bvalider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GcategorieBroom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GtypeBroom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GdateBroom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GnbNuitBroom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GnomBroom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Bvalider)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel12.setText("Réservation d'une chambre");

        javax.swing.GroupLayout GbRoomLayout = new javax.swing.GroupLayout(GbRoom);
        GbRoom.setLayout(GbRoomLayout);
        GbRoomLayout.setHorizontalGroup(
            GbRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GbRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addContainerGap(266, Short.MAX_VALUE))
        );
        GbRoomLayout.setVerticalGroup(
            GbRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GbRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GbRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("BROOM", GbRoom);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setText("Numéro chambre:");

        GnumChambreProom.setText("2");

        jLabel8.setText("Nom du client:");

        GnomProom.setText("knuts");

        jLabel9.setText("Numéro de carte de crédit:");

        GcarteProom.setText("123-123456-12");

        jButton1.setText("valider");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel17.setText("Date réservation:");

        GdateProom.setText("01/04/2013");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GcarteProom)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9)
                                .addComponent(GnumChambreProom)
                                .addComponent(GnomProom, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                            .addComponent(jButton1)
                            .addComponent(jLabel17)
                            .addComponent(GdateProom, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 176, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GnumChambreProom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GnomProom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GcarteProom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GdateProom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setText("Payement d'une reservation");

        javax.swing.GroupLayout GpRoomLayout = new javax.swing.GroupLayout(GpRoom);
        GpRoom.setLayout(GpRoomLayout);
        GpRoomLayout.setHorizontalGroup(
            GpRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GpRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addContainerGap(101, Short.MAX_VALUE))
        );
        GpRoomLayout.setVerticalGroup(
            GpRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GpRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GpRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("PROOM", GpRoom);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        jLabel10.setText("Numéro chambre:");

        GnumChambreCroom.setText("1");

        jLabel11.setText("Nom du client:");

        GnomCroom.setText("knuts");

        jButton2.setText("valider");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel16.setText("Date réservation:");

        GdateCroom.setText("01/03/2013");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(GnumChambreCroom)
                    .addComponent(GnomCroom, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(jButton2)
                    .addComponent(jLabel16)
                    .addComponent(GdateCroom))
                .addContainerGap(186, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GnumChambreCroom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GnomCroom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GdateCroom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );

        jLabel14.setText("Suppression d'une reservation");

        javax.swing.GroupLayout GcRoomLayout = new javax.swing.GroupLayout(GcRoom);
        GcRoom.setLayout(GcRoomLayout);
        GcRoomLayout.setHorizontalGroup(
            GcRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GcRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addContainerGap(97, Short.MAX_VALUE))
        );
        GcRoomLayout.setVerticalGroup(
            GcRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GcRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GcRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(124, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CROOM", GcRoom);

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

        jScrollPane1.setViewportView(Gliste);

        jButton3.setText("actualiser");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel15.setText("Liste des chambres réservés");

        javax.swing.GroupLayout GlRoomsLayout = new javax.swing.GroupLayout(GlRooms);
        GlRooms.setLayout(GlRoomsLayout);
        GlRoomsLayout.setHorizontalGroup(
            GlRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GlRoomsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addContainerGap(161, Short.MAX_VALUE))
        );
        GlRoomsLayout.setVerticalGroup(
            GlRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GlRoomsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GlRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GlRoomsLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("LROOMS", GlRooms);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
    }// </editor-fold>//GEN-END:initComponents

    private void BvaliderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BvaliderActionPerformed
        String categorie = GcategorieBroom.getSelectedItem().toString();
        String type = GtypeBroom.getSelectedItem().toString();
        String date = GdateBroom.getText();
        String nbNuit = GnbNuitBroom.getText();
        String nomClient = GnomBroom.getText();

        byte[] categorieCrypted = cryptage.crypt(Cryptage.DES, categorie.getBytes(), myKeys.getCleSession());
        byte[] typeCrypted = cryptage.crypt(Cryptage.DES, type.getBytes(), myKeys.getCleSession());
        byte[] dateCrypted = cryptage.crypt(Cryptage.DES, date.getBytes(), myKeys.getCleSession());
        byte[] nbNuitCrypted = cryptage.crypt(Cryptage.DES, nbNuit.getBytes(), myKeys.getCleSession());
        byte[] nomClientCrypted = cryptage.crypt(Cryptage.DES, nomClient.getBytes(), myKeys.getCleSession());

        Signature signature;
        byte[] signatureResponsable = null;
        try {
            signature = Signature.getInstance("SHA1withRSA", "BC");
            signature.initSign(myKeys.getClePrivee());
            signatureResponsable = signature.sign();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        MessageDigest md;
        byte[] digest = null;
        try {
            md = MessageDigest.getInstance("SHA1");
            md.update(categorieCrypted);
            md.update(typeCrypted);
            md.update(dateCrypted);
            md.update(nbNuitCrypted);
            md.update(nomClientCrypted);
            md.update(signatureResponsable);
            digest = md.digest();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object[] infos = {categorieCrypted, typeCrypted, dateCrypted, nbNuitCrypted, nomClientCrypted, signatureResponsable, digest};
        socket.send(new PacketCom(RMP.BROOM, (Object)infos));
        PacketCom packetRetour;
        try {
            packetRetour = socket.receive();
            traitementPacket(packetRetour);
        } catch (Exception ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_BvaliderActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String numChambre = GnumChambreProom.getText();
        String nomClient = GnomProom.getText();
        String numCarteCredit = GcarteProom.getText();
        String date = GdateProom.getText();

        byte[] numChambreCrypted = cryptage.crypt(Cryptage.DES, numChambre.getBytes(), myKeys.getCleSession());
        byte[] nomClientCrypted = cryptage.crypt(Cryptage.DES, nomClient.getBytes(), myKeys.getCleSession());
        byte[] numCarteCreditCrypted = cryptage.crypt(Cryptage.DES, numCarteCredit.getBytes(), myKeys.getCleSession());
        byte[] dateCrypted = cryptage.crypt(Cryptage.DES, date.getBytes(), myKeys.getCleSession());

        Signature signature;
        byte[] signatureResponsable = null;
        try {
            signature = Signature.getInstance("SHA1withRSA", "BC");
            signature.initSign(myKeys.getClePrivee());
            signatureResponsable = signature.sign();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        MessageDigest md;
        byte[] digest = null;
        try {
            md = MessageDigest.getInstance("SHA1");
            md.update(numChambreCrypted);
            md.update(nomClientCrypted);
            md.update(numCarteCreditCrypted);
            md.update(dateCrypted);
            md.update(signatureResponsable);
            digest = md.digest();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object[] infos = {numChambreCrypted, nomClientCrypted, numCarteCreditCrypted, dateCrypted, signatureResponsable, digest};
        socket.send(new PacketCom(RMP.PROOM, (Object)infos));
        PacketCom packetRetour;
        try {
            packetRetour = socket.receive();
            traitementPacket(packetRetour);
        } catch (Exception ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String numChambre = GnumChambreCroom.getText();
        String nomClient = GnomCroom.getText();
        String dateReservaton = GdateCroom.getText();

        Signature signature;
        byte[] signatureResponsable = null;
        try {
            signature = Signature.getInstance("SHA1withRSA", "BC");
            signature.initSign(myKeys.getClePrivee());
            signatureResponsable = signature.sign();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        MessageDigest md;
        byte[] digest = null;
        try {
            md = MessageDigest.getInstance("SHA1");
            md.update(numChambre.getBytes());
            md.update(nomClient.getBytes());
            md.update(dateReservaton.getBytes());
            md.update(signatureResponsable);
            digest = md.digest();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object[] infos = {numChambre, nomClient, dateReservaton, signatureResponsable, digest};
        socket.send(new PacketCom(RMP.CROOM, (Object)infos));
        PacketCom packetRetour;
        try {
            packetRetour = socket.receive();
            traitementPacket(packetRetour);
        } catch (Exception ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        socket.send(new PacketCom(RMP.LROOM, null));
        PacketCom packetRetour;
        try {
            packetRetour = socket.receive();
            traitementPacket(packetRetour);
        } catch (Exception ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Main dialog = new Main(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bvalider;
    private javax.swing.JPanel GbRoom;
    private javax.swing.JPanel GcRoom;
    private javax.swing.JTextField GcarteProom;
    private javax.swing.JComboBox GcategorieBroom;
    private javax.swing.JTextField GdateBroom;
    private javax.swing.JTextField GdateCroom;
    private javax.swing.JTextField GdateProom;
    private javax.swing.JPanel GlRooms;
    private javax.swing.JList Gliste;
    private javax.swing.JTextField GnbNuitBroom;
    private javax.swing.JTextField GnomBroom;
    private javax.swing.JTextField GnomCroom;
    private javax.swing.JTextField GnomProom;
    private javax.swing.JTextField GnumChambreCroom;
    private javax.swing.JTextField GnumChambreProom;
    private javax.swing.JPanel GpRoom;
    private javax.swing.JComboBox GtypeBroom;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

    private void traitementPacket(PacketCom packetReponse) {
        String type = packetReponse.getType();
        Object contenu = packetReponse.getObjet();
        if(type.equalsIgnoreCase(RMP.BROOM_OUI)){
            Object[] infos = (Object[]) contenu;
            int numeroChambre = (Integer) infos[0];
            int prix = (int) infos[1];
            JOptionPane.showMessageDialog(this, "Chambre n°" + numeroChambre + " réservée au prix de " + prix);
        }else if(type.equalsIgnoreCase(RMP.BROOM_NON)){
            String message = (String)contenu;
            JOptionPane.showMessageDialog(this, message);
        }else if(type.equalsIgnoreCase(RMP.PROOM_NON)){
            String message = (String)contenu;
            JOptionPane.showMessageDialog(this, message);
        }else if(type.equalsIgnoreCase(RMP.PROOM_OUI)){
            JOptionPane.showMessageDialog(this, "Payement de la réservation éffectué");
        }else if(type.equalsIgnoreCase(RMP.CROOM_NON)){
            String message = (String)contenu;
            JOptionPane.showMessageDialog(this, message);
        }else if(type.equalsIgnoreCase(RMP.CROOM_OUI)){
            JOptionPane.showMessageDialog(this, "Suppression de la réservation éffectuée");
        }else if(type.equalsIgnoreCase(RMP.LROOM_OUI)){
            lister(contenu);
        }else if(type.equalsIgnoreCase(RMP.LROOM_NON)){
            JOptionPane.showMessageDialog(this, "échec de listage des chambres réservée");
        }else{
            String message = (String)contenu;
            JOptionPane.showMessageDialog(this, message);
        }
    }

    private void lister(Object contenu) {
        LinkedList<byte[]> infos = (LinkedList<byte[]>) contenu;
        SwingUtils.emptyList(Gliste);
        for(byte[] elm : infos){
            String elmDecrypted = new String(cryptage.decrypt(Cryptage.DES, elm, myKeys.getCleSession()));
            SwingUtils.addToList(Gliste, elmDecrypted);
        }
    }

}
