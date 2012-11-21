/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;


public class BeanProjections implements Serializable{
private LinkedList<BeanProjection> listeProjection;

    public BeanProjections(){
        this.listeProjection = new LinkedList<BeanProjection>();
    }

    public BeanProjection getProj(int idMovie){
        for(BeanProjection proj : listeProjection){
            if(proj.getIdMovie() == idMovie){
                return proj;
            }
        }
        return null;
    }

    public void addProjection(Date dateHeureProjection, int numeroSalle, int idCopie, int idMovie, String nameMovie, int duree){
        BeanProjection projection = new BeanProjection(dateHeureProjection, numeroSalle, idCopie, idMovie, nameMovie, duree);
        listeProjection.add(projection);
    }

    private void addProjection(Date dateHeureProjection, int numeroSalle, int idCopie, int idMovie, String nameMovie, int duree, BeanMovie beanMovie) {
        BeanProjection projection = new BeanProjection(dateHeureProjection, numeroSalle, idCopie, idMovie, nameMovie, duree);
        projection.setBeanMovie(beanMovie);
        listeProjection.add(projection);
    }

    public boolean existDate(String date){
        for(BeanProjection projection : listeProjection){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if(sdf.format(projection.getDateHeureProjection()).equalsIgnoreCase(date)){
                return true;
            }
        }
        return false;
    }

    public LinkedList<BeanProjection> getListeBeanProjection() {
        return listeProjection;
    }

    public void setListeBeanProjection(LinkedList<BeanProjection> listeBeanProjection) {
        this.listeProjection = listeBeanProjection;
    }

    public BeanProjections getProjection(String date) {
        BeanProjections bonneProjections = null;
        for(BeanProjection projection : listeProjection){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String format = sdf.format(projection.getDateHeureProjection());
            if(format.equalsIgnoreCase(date)){
                if(bonneProjections == null){
                    bonneProjections = new BeanProjections();
                }
                bonneProjections.addProjection(projection.getDateHeureProjection(), projection.getNumeroSalle(), projection.getIdCopie(), projection.getIdMovie(), projection.getNameMovie(), projection.getDuree(), projection.getBeanMovie());
            }
        }
        return bonneProjections;
    }
}
