/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.io.Serializable;
import java.util.LinkedList;


public class BeanCaddy implements Serializable{
    private LinkedList<BeanMovieCaddy> listMovie;

    public BeanCaddy(){
        listMovie = new LinkedList<BeanMovieCaddy>();
    }

    public void add(BeanMovieCaddy beanMovie){
        this.listMovie.add(beanMovie);
    }

    public LinkedList<BeanMovieCaddy> getListMovie() {
        return listMovie;
    }

    public LinkedList<BeanMovieCaddy> getListMovie(int numPage, int nbFilm) {
        LinkedList<BeanMovieCaddy> listMoviePagine = new LinkedList<BeanMovieCaddy>();
        int cpt = 0;
        int i = 0;
        int indice = numPage * nbFilm;
        for(BeanMovieCaddy beanMovieCaddy : listMovie){
            if(i >= indice && cpt < nbFilm){
                listMoviePagine.add(beanMovieCaddy);
                cpt++;
            }
            i++;
        }
        return listMoviePagine;
    }

    public void setListMovie(LinkedList<BeanMovieCaddy> listMovie) {
        this.listMovie = listMovie;
    }
}
