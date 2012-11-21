/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.io.Serializable;


public class BeanMovies implements Serializable{
    private int idMovie;
    private String name;

    public BeanMovies(){
        this.idMovie = -1;
        this.name = null;
    }

    public BeanMovies(int idMovie, String name){
        this.idMovie = idMovie;
        this.name = name;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
