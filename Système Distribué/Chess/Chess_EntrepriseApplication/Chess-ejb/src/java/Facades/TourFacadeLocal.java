/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import ant.Tour;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Greenlamp
 */
@Local
public interface TourFacadeLocal {

    void create(Tour tour);

    void edit(Tour tour);

    void remove(Tour tour);

    Tour find(Object id);

    List<Tour> findAll();

    List<Tour> findRange(int[] range);

    int count();

}
