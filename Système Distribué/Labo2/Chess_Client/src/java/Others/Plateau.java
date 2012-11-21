/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Others;

import GUI.Main;
import Pions.Piece;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class Plateau extends JPanel{
    int x;
    int y;
    Color background;
    boolean selected;
    Piece pion;
    Main parent;

    public Plateau(){
        this.x = -1;
        this.y = -1;
        this.background = null;
        this.pion = null;
    }

    public Plateau(Main parent, int x, int y, Color background){
        this.x = x;
        this.y = y;
        this.background = background;
        this.parent = parent;
        this.setBackground(this.background);
        this.pion = null;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                onMouseClick();
            }
        });
    }

    public void addPion(Piece pion){
        String sep = System.getProperty("file.separator");
        String Path = System.getProperty("user.dir") + sep + "src" + sep + "java" + sep + "Images" + sep + pion.getImage() + ".png";
        this.pion = pion;
        JLabel label = new JLabel(new ImageIcon(Path));
        this.add(label);
        this.revalidate();
        this.repaint();
    }

    private void onMouseClick(){
        this.parent.onMouseClick(this.x, this.y);
    }

    public int getPositionX(){
        return this.x;
    }

    public int getPositionY(){
        return this.y;
    }

    public Piece getPion(){
        return this.pion;
    }

    public void selectionneCase(boolean alreadySelected){
        Border selected = null;
        selected = BorderFactory.createLineBorder(Color.BLACK);
        if(!alreadySelected){
            this.setBackground(Color.BLUE);
            this.setBorder(selected);
            this.selected = true;
        }else{
            this.setBackground(background);
            this.setBorder(selected);
            this.selected = false;
        }
    }

    public boolean isSelectionne(){
        return this.selected;
    }

    public void removePion() {
        this.removeAll();
        this.updateUI();
        this.pion = null;
    }
}
