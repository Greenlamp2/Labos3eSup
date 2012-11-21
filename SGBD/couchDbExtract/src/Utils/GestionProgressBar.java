/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import javax.swing.JProgressBar;


public class GestionProgressBar{

    JProgressBar bar = null;
    public GestionProgressBar(JProgressBar bar) {
        this.bar = bar;
        setMin(0);
        setMin(100);
        setValue(100);
    }

    public void setMax(int max){
        this.bar.setMaximum(max);
    }

    public void setMin(int min){
        this.bar.setMinimum(min);
    }

    public void setValue(int value){
        this.bar.setValue(value);
    }
}
