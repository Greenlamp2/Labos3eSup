/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;


public class Projections {
    private LinkedList<Projection> listeProjection;
    int sensSalle;
    int sensCopie;

    public Projections(){
        this.listeProjection = new LinkedList<>();
        sensSalle = 1;
        sensCopie = 1;
    }

    public void sort(String colonne){
        LinkedList<Projection> listeTemp = new LinkedList<>();
        int count = listeProjection.size();
        int i = 0;
        while(i < count){
            int index = 0;
            int j = 0;
            int min;
            if(colonne.equals("salle")){
                min = listeProjection.getFirst().getNumeroSalle();
                for(Projection proj : listeProjection){
                    if(sensSalle == 1){
                        if(proj.getNumeroSalle() < min){
                            min = proj.getNumeroSalle();
                            index = j;
                        }
                    }else{
                        if(proj.getNumeroSalle() > min){
                            min = proj.getNumeroSalle();
                            index = j;
                        }
                    }
                    j++;
                }
            }else{
                min = listeProjection.getFirst().getIdCopie();
                for(Projection proj : listeProjection){
                    if(sensCopie == 1){
                        if(proj.getIdCopie() < min){
                            min = proj.getIdCopie();
                            index = j;
                        }
                    }else{
                        if(proj.getIdCopie() > min){
                            min = proj.getIdCopie();
                            index = j;
                        }
                    }
                    j++;
                }
            }
            listeTemp.add(listeProjection.get(index));
            listeProjection.remove(index);
            i++;
        }
        listeProjection.addAll(listeTemp);
        if(colonne.equals("salle")){
            sensSalle *= -1;
        }else{
            sensCopie *= -1;
        }
    }

    public void addProjection(Date dateHeureProjection, int numeroSalle, int idCopie, int idMovie, String nameMovie, int duree){
        Projection projection = new Projection(dateHeureProjection, numeroSalle, idCopie, idMovie, nameMovie, duree);
        listeProjection.add(projection);
    }

    public boolean existDate(String date){
        for(Projection projection : listeProjection){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if(sdf.format(projection.getDateHeureProjection()).equalsIgnoreCase(date)){
                return true;
            }
        }
        return false;
    }

    public LinkedList<Projection> getListeProjection() {
        return listeProjection;
    }

    public void setListeProjection(LinkedList<Projection> listeProjection) {
        this.listeProjection = listeProjection;
    }

    public Projections getProjection(String date) {
        Projections bonneProjections = null;
        for(Projection projection : listeProjection){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String format = sdf.format(projection.getDateHeureProjection());
            if(format.equalsIgnoreCase(date)){
                if(bonneProjections == null){
                    bonneProjections = new Projections();
                }
                bonneProjections.addProjection(projection.getDateHeureProjection(), projection.getNumeroSalle(), projection.getIdCopie(), projection.getIdMovie(), projection.getNameMovie(), projection.getDuree());
            }
        }
        return bonneProjections;
    }
}
