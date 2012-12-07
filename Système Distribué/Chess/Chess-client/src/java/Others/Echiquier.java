/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Others;

import GUI.Main;
import ant.Piece;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class Echiquier extends JPanel{
    int x, y;
    Color colorBackground;
    String back;
    Piece piece;
    Main parent;
    boolean selected;

    public Echiquier(Main parent, int x, int y, Color color){
        setParent(parent);
        setPosX(x);
        setPosY(y);
        setColorBackground(color);
        setPiece(null);
        back = (color.getRGB() == Color.WHITE.getRGB() ? "White" : "Gray");

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                onMouseClick();
            }
        });
    }

    private void onMouseClick(){
        getParent().onMouseClick(this);
    }

    public void addPiece(Piece piece){
        String sep = System.getProperty("file.separator");
        String Path = "C:\\Users\\Greenlamp\\Documents\\GitHub\\Labos3eSup\\Système Distribué\\Chess\\Chess-client\\src\\java\\Images\\" + piece.getFileName() + ".png";
        //String Path = System.getProperty("user.dir") + sep + "src" + sep + "java" + sep + "Images" + sep + piece.getFileName() + ".png";
        setPiece(piece);
        JLabel label = new JLabel(new ImageIcon(Path));
        this.add(label);
        this.revalidate();
        this.repaint();
        this.updateUI();
    }

    public void removePion(){
        this.removeAll();
        this.updateUI();
        setPiece(null);
    }

    public void selectionne(){
        Border border = null;
        border = BorderFactory.createLineBorder(Color.BLACK);
        if(isSelected()){
            if(back.equals("White")){
                setBackground(Color.WHITE);
            }else{
                setBackground(Color.GRAY);
            }
            //setBackground(getColorBackground());
            setBorder(border);
            setSelected(false);
        }else{
            setBackground(Color.BLUE);
            setColorBackground(Color.BLUE);
            setSelected(true);
        }
    }


    /*Getters - Setters*/
    public Main getParent(){
        return this.parent;
    }
    public void setParent(Main parent){
        this.parent = parent;
    }
    public int getPosX(){
        return this.x;
    }
    public void setPosX(int x){
        this.x = x;
    }
    public int getPosY(){
        return this.y;
    }
    public void setPosY(int y){
        this.y = y;
    }
    public Color getColorBackground(){
        return this.colorBackground;
    }
    public void setColorBackground(Color color){
        this.setBackground(color);
        this.colorBackground = color;
    }
    public Piece getPiece(){
        return this.piece;
    }
    public void setPiece(Piece piece){
        this.piece = piece;
    }
    public boolean isSelected(){
        return this.selected;
    }
    public void setSelected(boolean selected){
        this.selected = selected;
    }
}
