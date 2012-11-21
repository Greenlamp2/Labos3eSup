/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.io.Serializable;
import java.util.LinkedList;


public class BeanResultat implements Serializable{
    private LinkedList<BeanMovie> listMovie;
    private int numResultat;
    private String requete;
    private int nbResultByPage;

    public BeanResultat(){
        listMovie = new LinkedList<BeanMovie>();
        numResultat = 0;
        requete = null;
        nbResultByPage = 2;
    }

    public void triByPopularite(){
        int count = listMovie.size();
        LinkedList<BeanMovie> temp = new LinkedList<BeanMovie>();
        int cpt = 0;
        while(cpt < count){
            int max = listMovie.getFirst().getPopularite();
            int i = 0;
            int index = 0;
            for(BeanMovie beanMovie : listMovie){
                if(beanMovie.getPopularite() > max){
                    max = beanMovie.getPopularite();
                    index = i;
                }
                i++;
            }
            temp.add(listMovie.get(index));
            listMovie.remove(index);
            cpt++;
        }
        listMovie.clear();
        listMovie.addAll(temp);
    }

    public void triByFrequence(){
        int count = listMovie.size();
        LinkedList<BeanMovie> temp = new LinkedList<BeanMovie>();
        int cpt = 0;
        while(cpt < count){
            int max = listMovie.getFirst().getFrequenceProg();
            int i = 0;
            int index = 0;
            for(BeanMovie beanMovie : listMovie){
                if(beanMovie.getFrequenceProg() > max){
                    max = beanMovie.getFrequenceProg();
                    index = i;
                }
                i++;
            }
            temp.add(listMovie.get(index));
            listMovie.remove(index);
            cpt++;
        }
        listMovie.clear();
        listMovie.addAll(temp);
    }

    public void triByPerenite(){
        int count = listMovie.size();
        LinkedList<BeanMovie> temp = new LinkedList<BeanMovie>();
        int cpt = 0;
        while(cpt < count){
            int max = listMovie.getFirst().getPerenite();
            int i = 0;
            int index = 0;
            for(BeanMovie beanMovie : listMovie){
                if(beanMovie.getPerenite() > max){
                    max = beanMovie.getPerenite();
                    index = i;
                }
                i++;
            }
            temp.add(listMovie.get(index));
            listMovie.remove(index);
            cpt++;
        }
        listMovie.clear();
        listMovie.addAll(temp);
    }

    public void add(BeanMovie movie){
        listMovie.add(movie);
    }
    public LinkedList<BeanMovie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(LinkedList<BeanMovie> listMovie) {
        this.listMovie = listMovie;
    }

    public int getNumResultat() {
        return numResultat;
    }

    public void setNumResultat(int numResultat) {
        this.numResultat = numResultat;
    }

    public String getRequete() {
        return requete;
    }

    public void setRequete(String requete) {
        this.requete = requete;
    }

    public int getNbResultByPage() {
        return nbResultByPage;
    }

    public void setNbResultByPage(int nbResultByPage) {
        this.nbResultByPage = nbResultByPage;
    }
}
