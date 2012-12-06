/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Exceptions;


public class GestionExceptions {

    public static String traiterException(String message){
        System.err.println("Gestion exception: " + message);
        if(message.contains("did not retrieve any entities")){
            return "Aucuns résultats";
        }
        return message;
    }
}
