/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.Menu;
import Helpers.EasyFile;
import Helpers.SwingUtils;
import Protocole.NetworkClient;
import Protocole.PacketCom;
import Protocole.VSMEAP;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.w3c.dom.Document;
import xml.MyDom;

/**
 *
 * @author Greenlamp
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    Menu menu;
    NetworkClient sock;
    public Main(NetworkClient sock) {
        initComponents();
        this.sock = sock;
        initComboBox();
        menu = new Menu();
        int port = Integer.parseInt(EasyFile.getConfig("Configs_Serveur_Restaurants", "PORT_HEAD"));
        String host = EasyFile.getConfig("Configs_Serveur_Restaurants", "HOST");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GgroupeEntree = new javax.swing.ButtonGroup();
        GgroupePlat = new javax.swing.ButtonGroup();
        GgroupeDessert = new javax.swing.ButtonGroup();
        LnomMenu = new javax.swing.JLabel();
        GnomMenu = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Gvedette = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Gentree = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        GnomEntree = new javax.swing.JTextField();
        GchaudEntree = new javax.swing.JRadioButton();
        GfroidEntree = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        GingrédientsEntree = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        GquantiteEntree = new javax.swing.JTextField();
        BaddIngEntree = new javax.swing.JButton();
        BdelIngEntree = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        GlisteEntree = new javax.swing.JList();
        GvaliderEntree = new javax.swing.JButton();
        Gplat = new javax.swing.JPanel();
        GvaliderPlat = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        GlistePlat = new javax.swing.JList();
        BdelIngPlat = new javax.swing.JButton();
        GquantitePlat = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        GingrédientsPlat = new javax.swing.JComboBox();
        BaddIngPlat = new javax.swing.JButton();
        GfroidPlat = new javax.swing.JRadioButton();
        GchaudPlat = new javax.swing.JRadioButton();
        GnomPlat = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        Gdessert = new javax.swing.JPanel();
        GvaliderDessert = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        GlisteDessert = new javax.swing.JList();
        BdelIngDessert = new javax.swing.JButton();
        GquantiteDessert = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        GingrédientsDessert = new javax.swing.JComboBox();
        BaddIngDessert = new javax.swing.JButton();
        GfroidDessert = new javax.swing.JRadioButton();
        GchaudDessert = new javax.swing.JRadioButton();
        GnomDessert = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        BvaliderMenu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Applic_Head_Food: Menu");

        LnomMenu.setText("Nom du menu:");

        jLabel1.setText("Vedette du jour: ");

        jLabel2.setText("Nom: ");

        GgroupeEntree.add(GchaudEntree);
        GchaudEntree.setSelected(true);
        GchaudEntree.setText("Chaud");

        GgroupeEntree.add(GfroidEntree);
        GfroidEntree.setText("Froid");

        jLabel3.setText("Ingrédients: ");

        jLabel4.setText("Quantité: ");

        GquantiteEntree.setText("1");

        BaddIngEntree.setText(">>");
        BaddIngEntree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BaddIngEntreeActionPerformed(evt);
            }
        });

        BdelIngEntree.setText("<<");
        BdelIngEntree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BdelIngEntreeActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(GlisteEntree);

        GvaliderEntree.setText("Valider Entrée");
        GvaliderEntree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GvaliderEntreeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout GentreeLayout = new javax.swing.GroupLayout(Gentree);
        Gentree.setLayout(GentreeLayout);
        GentreeLayout.setHorizontalGroup(
            GentreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GentreeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GentreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GentreeLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GnomEntree, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(GchaudEntree))
                    .addGroup(GentreeLayout.createSequentialGroup()
                        .addGroup(GentreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GentreeLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GingrédientsEntree, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(GentreeLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(GentreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(GvaliderEntree)
                                    .addComponent(GquantiteEntree, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(GentreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BaddIngEntree, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(BdelIngEntree, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(GentreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GfroidEntree)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        GentreeLayout.setVerticalGroup(
            GentreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GentreeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GentreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(GnomEntree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GchaudEntree)
                    .addComponent(GfroidEntree))
                .addGap(18, 18, 18)
                .addGroup(GentreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GentreeLayout.createSequentialGroup()
                        .addGroup(GentreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(GingrédientsEntree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BaddIngEntree))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(GentreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(GquantiteEntree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(BdelIngEntree))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GvaliderEntree))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Entrée", Gentree);

        GvaliderPlat.setText("Valider plat");
        GvaliderPlat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GvaliderPlatActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(GlistePlat);

        BdelIngPlat.setText("<<");
        BdelIngPlat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BdelIngPlatActionPerformed(evt);
            }
        });

        GquantitePlat.setText("1");

        jLabel5.setText("Quantité: ");

        jLabel6.setText("Ingrédients: ");

        BaddIngPlat.setText(">>");
        BaddIngPlat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BaddIngPlatActionPerformed(evt);
            }
        });

        GgroupePlat.add(GfroidPlat);
        GfroidPlat.setText("Froid");

        GgroupePlat.add(GchaudPlat);
        GchaudPlat.setSelected(true);
        GchaudPlat.setText("Chaud");

        jLabel7.setText("Nom: ");

        javax.swing.GroupLayout GplatLayout = new javax.swing.GroupLayout(Gplat);
        Gplat.setLayout(GplatLayout);
        GplatLayout.setHorizontalGroup(
            GplatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GplatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GplatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GplatLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GnomPlat, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(GchaudPlat))
                    .addGroup(GplatLayout.createSequentialGroup()
                        .addGroup(GplatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GplatLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GingrédientsPlat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(GplatLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(GplatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(GvaliderPlat)
                                    .addComponent(GquantitePlat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(46, 46, 46)))
                        .addGroup(GplatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BaddIngPlat, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(BdelIngPlat, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(GplatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GfroidPlat)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        GplatLayout.setVerticalGroup(
            GplatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GplatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GplatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(GnomPlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GchaudPlat)
                    .addComponent(GfroidPlat))
                .addGap(18, 18, 18)
                .addGroup(GplatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GplatLayout.createSequentialGroup()
                        .addGroup(GplatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(GingrédientsPlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BaddIngPlat))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(GplatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(GquantitePlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(BdelIngPlat))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GvaliderPlat))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Plat", Gplat);

        GvaliderDessert.setText("Valider dessert");
        GvaliderDessert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GvaliderDessertActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(GlisteDessert);

        BdelIngDessert.setText("<<");
        BdelIngDessert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BdelIngDessertActionPerformed(evt);
            }
        });

        GquantiteDessert.setText("1");

        jLabel8.setText("Quantité: ");

        jLabel9.setText("Ingrédients: ");

        BaddIngDessert.setText(">>");
        BaddIngDessert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BaddIngDessertActionPerformed(evt);
            }
        });

        GgroupeDessert.add(GfroidDessert);
        GfroidDessert.setSelected(true);
        GfroidDessert.setText("Froid");

        GgroupeDessert.add(GchaudDessert);
        GchaudDessert.setText("Chaud");

        jLabel10.setText("Nom: ");

        javax.swing.GroupLayout GdessertLayout = new javax.swing.GroupLayout(Gdessert);
        Gdessert.setLayout(GdessertLayout);
        GdessertLayout.setHorizontalGroup(
            GdessertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GdessertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GdessertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GdessertLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GnomDessert, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(GchaudDessert))
                    .addGroup(GdessertLayout.createSequentialGroup()
                        .addGroup(GdessertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GdessertLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GingrédientsDessert, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(GdessertLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(GdessertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(GvaliderDessert)
                                    .addComponent(GquantiteDessert, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)))
                        .addGroup(GdessertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BaddIngDessert, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(BdelIngDessert, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(GdessertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GfroidDessert)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        GdessertLayout.setVerticalGroup(
            GdessertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GdessertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GdessertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(GnomDessert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GchaudDessert)
                    .addComponent(GfroidDessert))
                .addGap(18, 18, 18)
                .addGroup(GdessertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GdessertLayout.createSequentialGroup()
                        .addGroup(GdessertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(GingrédientsDessert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BaddIngDessert))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(GdessertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(GquantiteDessert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(BdelIngDessert))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GvaliderDessert))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dessert", Gdessert);

        BvaliderMenu.setText("Valider Menu");
        BvaliderMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BvaliderMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(BvaliderMenu)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LnomMenu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GnomMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Gvedette)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LnomMenu)
                    .addComponent(GnomMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(Gvedette, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BvaliderMenu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-459)/2, (screenSize.height-374)/2, 459, 374);
    }// </editor-fold>//GEN-END:initComponents

    private void BaddIngEntreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BaddIngEntreeActionPerformed
        String nom = GingrédientsEntree.getSelectedItem().toString();
        SwingUtils.addToList(GlisteEntree, nom);
        int quantite = Integer.parseInt(GquantiteEntree.getText());
        String nomEntree = GnomEntree.getText();
        boolean exist = menu.addIngredientEntree(nomEntree, nom, quantite);
        if(!exist){
            boolean chaud = true;
            if(GfroidEntree.isSelected()){
                chaud = false;
            }
            menu.addEntree(nomEntree, chaud);
            menu.addIngredientEntree(nomEntree, nom, quantite);
        }
    }//GEN-LAST:event_BaddIngEntreeActionPerformed

    private void BdelIngEntreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BdelIngEntreeActionPerformed
        try{
            String nom = GlisteEntree.getSelectedValue().toString();
            SwingUtils.removeToList(GlisteEntree, nom);
            String nomEntree = GnomEntree.getText();
            menu.delIngredientEntree(nomEntree, nom);
        }catch(Exception ex){
            return;
        }
    }//GEN-LAST:event_BdelIngEntreeActionPerformed

    private void GvaliderEntreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GvaliderEntreeActionPerformed
        SwingUtils.emptyList(GlisteEntree);
        GquantiteEntree.setText("1");
        String nomEntree = GnomEntree.getText();
        menu.validerEntree(nomEntree);
        GnomEntree.setText("");
    }//GEN-LAST:event_GvaliderEntreeActionPerformed

    private void BvaliderMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BvaliderMenuActionPerformed
        String nomMenu = GnomMenu.getText();
        String nomVedette = Gvedette.getText();
        menu.setNomMenu(nomMenu);
        menu.setVedetteJour(nomVedette);
        try {
            byte[] xml = MyDom.serialize(menu);
            JOptionPane.showMessageDialog(this, "Création du fichier xml réussi !");
            Object[] infos = {(Object)xml, (Object)nomMenu};
            PacketCom packet = new PacketCom("UPMENU", (Object)infos);
            sock.send(packet);
            PacketCom packetReponse = sock.receive();
            String type = packetReponse.getType();
            if(type.equals(VSMEAP.UPMENU_OUI)){
                JOptionPane.showMessageDialog(this, "Menu bien reçu par le serveur");
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BvaliderMenuActionPerformed

    private void BaddIngPlatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BaddIngPlatActionPerformed
        String nom = GingrédientsPlat.getSelectedItem().toString();
        SwingUtils.addToList(GlistePlat, nom);
        int quantite = Integer.parseInt(GquantitePlat.getText());
        String nomPlat = GnomPlat.getText();
        boolean exist = menu.addIngredientPlat(nomPlat, nom, quantite);
        if(!exist){
            boolean chaud = true;
            if(GfroidPlat.isSelected()){
                chaud = false;
            }
            menu.addPlat(nomPlat, chaud);
            menu.addIngredientPlat(nomPlat, nom, quantite);
        }
    }//GEN-LAST:event_BaddIngPlatActionPerformed

    private void BdelIngPlatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BdelIngPlatActionPerformed
        try{
            String nom = GlistePlat.getSelectedValue().toString();
            SwingUtils.removeToList(GlistePlat, nom);
            String nomPlat = GnomPlat.getText();
            menu.delIngredientPlat(nomPlat, nom);
        }catch(Exception ex){
            return;
        }
    }//GEN-LAST:event_BdelIngPlatActionPerformed

    private void BaddIngDessertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BaddIngDessertActionPerformed
        String nom = GingrédientsDessert.getSelectedItem().toString();
        SwingUtils.addToList(GlisteDessert, nom);
        int quantite = Integer.parseInt(GquantiteDessert.getText());
        String nomDessert = GnomDessert.getText();
        boolean exist = menu.addIngredientDessert(nomDessert, nom, quantite);
        if(!exist){
            boolean chaud = true;
            if(GfroidDessert.isSelected()){
                chaud = false;
            }
            menu.addDessert(nomDessert, chaud);
            menu.addIngredientDessert(nomDessert, nom, quantite);
        }
    }//GEN-LAST:event_BaddIngDessertActionPerformed

    private void BdelIngDessertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BdelIngDessertActionPerformed
        try{
            String nom = GlisteDessert.getSelectedValue().toString();
            SwingUtils.removeToList(GlisteDessert, nom);
            String nomDessert = GnomDessert.getText();
            menu.delIngredientDessert(nomDessert, nom);
        }catch(Exception ex){
            return;
        }
    }//GEN-LAST:event_BdelIngDessertActionPerformed

    private void GvaliderDessertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GvaliderDessertActionPerformed
        SwingUtils.emptyList(GlisteDessert);
        GquantiteDessert.setText("1");
        String nomDessert = GnomDessert.getText();
        menu.validerDessert(nomDessert);
        GnomDessert.setText("");
    }//GEN-LAST:event_GvaliderDessertActionPerformed

    private void GvaliderPlatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GvaliderPlatActionPerformed
        SwingUtils.emptyList(GlistePlat);
        GquantitePlat.setText("1");
        String nomPlat = GnomPlat.getText();
        menu.validerEntree(nomPlat);
        GnomPlat.setText("");
    }//GEN-LAST:event_GvaliderPlatActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BaddIngDessert;
    private javax.swing.JButton BaddIngEntree;
    private javax.swing.JButton BaddIngPlat;
    private javax.swing.JButton BdelIngDessert;
    private javax.swing.JButton BdelIngEntree;
    private javax.swing.JButton BdelIngPlat;
    private javax.swing.JButton BvaliderMenu;
    private javax.swing.JRadioButton GchaudDessert;
    private javax.swing.JRadioButton GchaudEntree;
    private javax.swing.JRadioButton GchaudPlat;
    private javax.swing.JPanel Gdessert;
    private javax.swing.JPanel Gentree;
    private javax.swing.JRadioButton GfroidDessert;
    private javax.swing.JRadioButton GfroidEntree;
    private javax.swing.JRadioButton GfroidPlat;
    private javax.swing.ButtonGroup GgroupeDessert;
    private javax.swing.ButtonGroup GgroupeEntree;
    private javax.swing.ButtonGroup GgroupePlat;
    private javax.swing.JComboBox GingrédientsDessert;
    private javax.swing.JComboBox GingrédientsEntree;
    private javax.swing.JComboBox GingrédientsPlat;
    private javax.swing.JList GlisteDessert;
    private javax.swing.JList GlisteEntree;
    private javax.swing.JList GlistePlat;
    private javax.swing.JTextField GnomDessert;
    private javax.swing.JTextField GnomEntree;
    private javax.swing.JTextField GnomMenu;
    private javax.swing.JTextField GnomPlat;
    private javax.swing.JPanel Gplat;
    private javax.swing.JTextField GquantiteDessert;
    private javax.swing.JTextField GquantiteEntree;
    private javax.swing.JTextField GquantitePlat;
    private javax.swing.JButton GvaliderDessert;
    private javax.swing.JButton GvaliderEntree;
    private javax.swing.JButton GvaliderPlat;
    private javax.swing.JTextField Gvedette;
    private javax.swing.JLabel LnomMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

    private void initComboBox() {
        PacketCom packet = new PacketCom(VSMEAP.DOWNPROD, "DOWNPROD_OUI");
        sock.send(packet);
        try {
            PacketCom packetReponse = sock.receive();
            String type = packetReponse.getType();
            Object contenu = packetReponse.getObjet();
            Object[] infos = (Object[]) contenu;
            LinkedList<String> listeProduitsEntree = (LinkedList<String>)infos[0];
            LinkedList<String> listeProduitsPlat = (LinkedList<String>)infos[1];
            LinkedList<String> listeProduitsDessert = (LinkedList<String>)infos[2];

            for(String produitEntree : listeProduitsEntree){
                SwingUtils.addToComboBox(GingrédientsEntree, produitEntree);
            }
            for(String produitPlat : listeProduitsPlat){
                SwingUtils.addToComboBox(GingrédientsPlat, produitPlat);
            }
            for(String produitDessert : listeProduitsDessert){
                SwingUtils.addToComboBox(GingrédientsDessert, produitDessert);
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
