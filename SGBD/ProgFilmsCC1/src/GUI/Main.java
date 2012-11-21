/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Data.Dispo;
import Data.Dispos;
import Data.Projection;
import Data.Projections;
import Database.OracleAccess;
import Helpers.SwingUtils;
import com.toedter.calendar.JDayChooser;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

/**
 *
 * @author Greenlamp
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    OracleAccess oracle;
    Projections projections;
    Dispos dispos;
    Map<String, Set<String>> listeMois;
    boolean selectedInList = false;
    Projections bonneProjections = null;
    Reservations reservations = null;
    ListSelectionListener listSelectionListener = new ListSelectionListener() {

        //Listener sur la selection de dates à gauche dans la liste
        @Override
        public void valueChanged(ListSelectionEvent e) {
            boolean adjust = e.getValueIsAdjusting();
            if (!adjust) {
                SwingUtils.emptyTable(Gprogrammation);
                selectedInList = true;
                String month = (String) GlistDates.getSelectedValue();
                String[] split = month.split("/");
                String mois = split[0];
                String annee = split[1];
                Gcalendar.getMonthChooser().setMonth(Integer.parseInt(mois) - 1);
                Gcalendar.getYearChooser().setYear(Integer.parseInt(annee));
                JDayChooser dayChooser = Gcalendar.getDayChooser();
                for(String day : listeMois.get(month)){
                    dayChooser.setColorDay(Integer.parseInt(day), Color.RED);
                }
            }
        }
    };
    PropertyChangeListener propertyChangeListenerDay = new PropertyChangeListener() {

        //Listener sur la selection d'un jour sur le calendar.
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            SwingUtils.emptyTable(Gprogrammation);
            String day = evt.getNewValue().toString();
            String month = String.valueOf(Gcalendar.getMonthChooser().getMonth() + 1);
            if(Integer.parseInt(month) < 10){
                month = "0" + month;
            }
            int year = (int)Gcalendar.getYearChooser().getYear();
            String key = month + "/" + year;
            if(listeMois.containsKey(key)){
                String date = null;
                if(Integer.parseInt(day) < 10){
                    date = "0" + day + "/" + key;
                }else{
                    date = day + "/" + key;
                }
                bonneProjections = projections.getProjection(date);
                if(bonneProjections != null){
                    fillTable(bonneProjections);
                }
            }
        }
    };
    PropertyChangeListener propertyChangeListenerMonth = new PropertyChangeListener() {

        ////Listener sur la selection d'un mois sur le calendar.
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            SwingUtils.emptyTable(Gprogrammation);
            if(!selectedInList){
                String month = String.valueOf(Integer.parseInt(evt.getNewValue().toString()) + 1);
                if(Integer.parseInt(month) < 10){
                    month = "0" + month;
                }
                int year = (int)Gcalendar.getYearChooser().getYear();
                String key = month + "/" + year;
                if(listeMois.containsKey(key)){
                    JDayChooser dayChooser = Gcalendar.getDayChooser();
                    for(String day : listeMois.get(key)){
                        dayChooser.setColorDay(Integer.parseInt(day), Color.RED);
                    }
                }
            }else{
                selectedInList = false;
            }
        }
    };
    PropertyChangeListener propertyChangeListenerYear = new PropertyChangeListener() {

        //Listener sur la selection d'une année sur le calendar.
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            SwingUtils.emptyTable(Gprogrammation);
            if(!selectedInList){
                String year = evt.getNewValue().toString();
                String month = String.valueOf(Gcalendar.getMonthChooser().getMonth() + 1);
                String key = month + "/" + year;
                if(listeMois.containsKey(key)){
                    JDayChooser dayChooser = Gcalendar.getDayChooser();
                    for(String day : listeMois.get(key)){
                        dayChooser.setColorDay(Integer.parseInt(day), Color.RED);
                    }
                }
            }else{
                selectedInList = false;
            }
        }
    };

    MouseListener mouseHeaderListener = new MouseListener() {

        //Listener sur le click sur l'entete de la table pour trier
        @Override
        public void mouseClicked(MouseEvent e) {
            TableColumnModel columnModel = Gprogrammation.getColumnModel();
            int x = columnModel.getColumnIndexAtX(e.getX());
            System.out.println("x: " + x);
            if(x == 1){
                bonneProjections.sort("copie");
                SwingUtils.emptyTable(Gprogrammation);
                fillTable(bonneProjections);
            }else if(x == 4){
                bonneProjections.sort("salle");
                SwingUtils.emptyTable(Gprogrammation);
                fillTable(bonneProjections);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    };

    public Main() {
        initComponents();
        oracle = new OracleAccess("CC1");
        listeMois = new TreeMap<>();
        initOracle();
        fillListeDate();
        fillCombo();
        colorThisMonth();
        GlistDates.addListSelectionListener(listSelectionListener);
        Gcalendar.getMonthChooser().addPropertyChangeListener("month", propertyChangeListenerMonth);
        Gcalendar.getYearChooser().addPropertyChangeListener("year", propertyChangeListenerYear);
        Gcalendar.getDayChooser().addPropertyChangeListener("day", propertyChangeListenerDay);
        Gprogrammation.getTableHeader().addMouseListener(mouseHeaderListener);
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
        Gcalendar = new com.toedter.calendar.JCalendar();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Gprogrammation = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        Gmovies = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        GlistDates = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Gcalendar, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Gcalendar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Programmations");

        Gprogrammation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Film", "Copie", "Heure", "Durée", "Salle"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Gprogrammation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GprogrammationMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Gprogrammation);
        Gprogrammation.getColumnModel().getColumn(0).setResizable(false);
        Gprogrammation.getColumnModel().getColumn(1).setResizable(false);
        Gprogrammation.getColumnModel().getColumn(2).setResizable(false);
        Gprogrammation.getColumnModel().getColumn(3).setResizable(false);
        Gprogrammation.getColumnModel().getColumn(4).setResizable(false);

        jButton1.setText("Nouveau");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Prolonger");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Gmovies, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(jLabel1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(Gmovies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(GlistDates);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Dates");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
    }// </editor-fold>//GEN-END:initComponents

    private void GprogrammationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GprogrammationMouseClicked
        if(evt.getClickCount() == 2){
            int row = Gprogrammation.rowAtPoint(evt.getPoint());
            System.out.println("Row: " + row);
            String nameClicked = Gprogrammation.getValueAt(row, 0).toString();
            System.out.println("nameClicked: " + nameClicked);
            int idMovie = getIdMovieWithName(nameClicked);
            if(idMovie != -1){
                Details details = new Details(this, true, idMovie);
                details.setVisible(true);
            }
        }
    }//GEN-LAST:event_GprogrammationMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String nameSelected = Gmovies.getSelectedItem().toString();
        int idMovie = 0;
        LinkedList<Integer> copies = null;
        for(Dispo dispo : dispos.getListeDispo()){
            if(dispo.getName().equalsIgnoreCase(nameSelected)){
                idMovie = dispo.getIdMovie();
                copies = dispo.getIdCopies();
                break;
            }
        }
        LinkedList<Integer> salles = dispos.getSallesDispo();
        String day = String.valueOf(Gcalendar.getDayChooser().getDay());
        String month = String.valueOf(Gcalendar.getMonthChooser().getMonth() + 1);
        if(Integer.parseInt(month) < 10){
            month = "0" + month;
        }
        String year = String.valueOf((int)Gcalendar.getYearChooser().getYear());
        reservations = new Reservations(this, true, idMovie, nameSelected, copies, salles, day, month, year, oracle);
        reservations.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int selectedRow = Gprogrammation.getSelectedRow();
        if(selectedRow != -1){
            String input = JOptionPane.showInputDialog(this, "Combien de minutes ?");
            int minutes = Integer.parseInt(input);
            if(minutes > 0){
            int idCopie = Integer.parseInt(Gprogrammation.getValueAt(selectedRow, 1).toString());
            String heure = Gprogrammation.getValueAt(selectedRow, 2).toString();
            int salle = Integer.parseInt(Gprogrammation.getValueAt(selectedRow, 4).toString());
            String day = String.valueOf(Gcalendar.getDayChooser().getDay());
            String month = String.valueOf(Gcalendar.getMonthChooser().getMonth() + 1);
            if(Integer.parseInt(day) < 10){
                day = "0" + day;
            }
            if(Integer.parseInt(month) < 10){
                month = "0" + month;
            }
            String year = String.valueOf((int)Gcalendar.getYearChooser().getYear());
            String dateFormat = day + "/" + month + "/" + year + " " + heure;
            oracle.initCallProc("PACKAGE_PROGRAMMATION", "prolongerProjection", 4);
            boolean correct = oracle.doProlongerProjection(dateFormat, salle, idCopie, minutes);
            if(correct){
                refreshAfterProlonger(dateFormat, salle, idCopie, minutes);
            }else{
                JOptionPane.showMessageDialog(this, "Une erreur est survenue lors de la prolongation de la projection");
            }
            }else{
                JOptionPane.showMessageDialog(this, "La durée doit être supérieure à 0");
            }
        }else{
            JOptionPane.showMessageDialog(this, "Aucune projection sélectionnée");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        oracle.disconnect();
    }//GEN-LAST:event_formWindowClosing

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
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JCalendar Gcalendar;
    private javax.swing.JList GlistDates;
    private javax.swing.JComboBox Gmovies;
    private javax.swing.JTable Gprogrammation;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

    private void initOracle() {
        oracle.initCallFunc("PACKAGE_PROGRAMMATION", "getAllProjection", 1);
        projections = oracle.doGetAllProjection("ALL");
        oracle.initCallFunc("PACKAGE_PROGRAMMATION", "getDispos", 1);
        dispos = oracle.doGetDispos("ALL");
    }

    public void colourDate(){
        JDayChooser dayChooser = Gcalendar.getDayChooser();
        dayChooser.setColorDay(9, Color.RED);
        dayChooser.setColorDay(14, Color.RED);
        dayChooser.setColorDay(22, Color.RED);
    }

    private void fillListeDate() {
        for(Projection projection : projections.getListeProjection()){
            String mois = projection.getMonthYear();
            if(listeMois.containsKey(mois)){
                listeMois.get(mois).add(projection.getDay());
            }else{
                Set<String> liste = new TreeSet<>();
                liste.add(projection.getDay());
                listeMois.put(mois, liste);
            }
        }

        for(String key : listeMois.keySet()){
            SwingUtils.addToList(GlistDates, key);
        }
    }

    private void colorThisMonth() {
        String month = String.valueOf(Gcalendar.getMonthChooser().getMonth() + 1);
        int year = (int)Gcalendar.getYearChooser().getYear();
        String key = month + "/" + year;
        if(listeMois.containsKey(key)){
            JDayChooser dayChooser = Gcalendar.getDayChooser();
            for(String day : listeMois.get(key)){
                dayChooser.setColorDay(Integer.parseInt(day), Color.RED);
            }
        }
    }

    private void fillTable(Projections bonneProjections) {
        SwingUtils.emptyTable(Gprogrammation);
        for(Projection proj : bonneProjections.getListeProjection()){
            Vector vec = new Vector();
            vec.add(proj.getNameMovie());
            vec.add(proj.getIdCopie());
            vec.add(new SimpleDateFormat("HH:mm").format(proj.getDateHeureProjection()));
            vec.add(proj.getDuree());
            vec.add(proj.getNumeroSalle());
            SwingUtils.addToTable(Gprogrammation, vec);
        }
    }

    private int getIdMovieWithName(String nameClicked) {
        for(Projection projection : projections.getListeProjection()){
            if(projection.getNameMovie().equalsIgnoreCase(nameClicked)){
                return projection.getIdMovie();
            }
        }
        return -1;
    }

    private String getNameMovieWithId(int id) {
        for(Dispo dispo : dispos.getListeDispo()){
            if(dispo.getIdMovie() == id){
                return dispo.getName();
            }
        }
        return null;
    }

    private void fillCombo() {
        for(Dispo dispo: dispos.getListeDispo()){
            SwingUtils.addToComboBox(Gmovies, dispo.getName());
        }
    }

    void nouvelleReservation(int idMovie, String idCopie, Date date, String salle) {
        //Ajouter la reservation dans la base de donnée, la rajoutée dans la liste.
        oracle.initCallProc("PACKAGE_PROGRAMMATION", "insertProjection", 3);
        boolean correct = oracle.doInsertProjection(Integer.parseInt(idCopie), date, Integer.parseInt(salle));
        if(correct){
            reservations.setVisible(false);
            String nameMovie = getNameMovieWithId(idMovie);
            int duree = getDureeWithId(idMovie);
            if(bonneProjections == null){
                bonneProjections = new Projections();
            }
            bonneProjections.addProjection(date, Integer.parseInt(salle), Integer.parseInt(idCopie), idMovie, nameMovie, duree);
            if(bonneProjections != null){
                SwingUtils.emptyTable(Gprogrammation);
                fillTable(bonneProjections);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Une erreur est survenur pendant l'insertion de la programmation");
        }
    }

    private int getDureeWithId(int id) {
        for(Dispo dispo : dispos.getListeDispo()){
            if(dispo.getIdMovie() == id){
                return dispo.getDuree();
            }
        }
        return -1;
    }

    private void refreshAfterProlonger(String date, int salle, int idCopie, int duree) {
        int cpt = 0;
        for(Projection proj : bonneProjections.getListeProjection()){
            if(cpt > 0){
                Date dateFormat = null;
                try {
                    dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date);
                } catch (ParseException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(proj.getDateHeureProjection().compareTo(dateFormat) > 0){
                    if(proj.getNumeroSalle() == salle && proj.getIdCopie() == idCopie){
                        proj.addDuree(duree);
                    }
                }
            }
            cpt++;
        }
        SwingUtils.emptyTable(Gprogrammation);
        fillTable(bonneProjections);
    }
}
