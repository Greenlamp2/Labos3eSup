/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Helpers;

import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class SwingUtils {
    public static void addToList(JList liste, String value){
        if(value == null || value.isEmpty()){
            return;
        }
        DefaultListModel dlm = new DefaultListModel();
        Vector<String> vector = new Vector<>();
        for(int i=0; i<liste.getModel().getSize(); i++){
            dlm.addElement(liste.getModel().getElementAt(i));
            vector.addElement((String)liste.getModel().getElementAt(i));
        }
        if(!vector.contains(value)){
            dlm.addElement(value);
        }
        liste.setModel(dlm);
    }

    public static void removeToList(JList liste, String value){
        DefaultListModel dlm = new DefaultListModel();
        for(int i=0; i<liste.getModel().getSize(); i++){
            if(!liste.getModel().getElementAt(i).equals(value))
            {
                dlm.addElement(liste.getModel().getElementAt(i));
            }
        }
        liste.setModel(dlm);
    }

    public static void emptyList(JList liste){
        DefaultListModel dlm = new DefaultListModel();
        liste.setModel(dlm);
    }

    public static void addToComboBox(JComboBox comboBox, String value){
        comboBox.addItem(value);
    }

    public static void addToTable(JTable table, Vector vec){
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.addRow(vec);
    }
}
