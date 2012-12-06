/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import ant.Plateau;
import java.awt.Color;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Greenlamp
 */
@Remote
public interface SessionBeanRemote {
    /**********************************************************************/
    /*Plateau*/
    /**********************************************************************/
    List<Plateau> getPlateaux();
    public void createPlateau(String nom);
    Plateau getByNom(String nom) throws Exception;
}
