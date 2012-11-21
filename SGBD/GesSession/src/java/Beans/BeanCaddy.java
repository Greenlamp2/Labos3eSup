/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.util.LinkedList;


public class BeanCaddy {
    private LinkedList<BeanMovies> listMovies;

    public BeanCaddy(){
        this.listMovies = new LinkedList<BeanMovies>();
    }

    public LinkedList<BeanMovies> getListMovies() {
        return listMovies;
    }

    public void setListMovies(LinkedList<BeanMovies> listMovies) {
        this.listMovies = listMovies;
    }

    public void add(BeanMovies beanMovie) {
        this.listMovies.add(beanMovie);
    }
}
