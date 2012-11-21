/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.LinkedList;


public class BeanDetails implements Serializable{
    private int id;
    private LinkedList<String> listActors;
    private LinkedList<String> listDirectors;
    private java.util.Date released;

    public BeanDetails(){
        id = -1;
        listActors = new LinkedList<String>();
        listDirectors = new LinkedList<String>();
        released = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<String> getListActors() {
        return listActors;
    }

    public void setListActors(LinkedList<String> listActors) {
        this.listActors = listActors;
    }

    public LinkedList<String> getListDirectors() {
        return listDirectors;
    }

    public void setListDirectors(LinkedList<String> listDirectors) {
        this.listDirectors = listDirectors;
    }

    public java.util.Date getReleased() {
        return released;
    }

    public void setReleased(java.util.Date released) {
        this.released = released;
    }

    public String getFormatedDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
        String date = sdf.format(released);
        return date;
    }
}
