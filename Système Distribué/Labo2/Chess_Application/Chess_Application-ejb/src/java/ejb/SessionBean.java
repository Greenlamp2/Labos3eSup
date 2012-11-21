/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Exceptions.ExceptionInterceptor;
import Facades.JoueursFacadeLocal;
import Facades.PionsFacadeLocal;
import Facades.PlateauxFacadeLocal;
import ant.Joueurs;
import ant.Pions;
import ant.Plateaux;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;

/**
 *
 * @author Greenlamp
 */
@Stateful
public class SessionBean implements SessionBeanRemote {
    @EJB
    private JoueursFacadeLocal joueursFacade;
    @EJB
    private PionsFacadeLocal pionsFacade;
    @EJB
    private PlateauxFacadeLocal plateauxFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    @Interceptors(ExceptionInterceptor.class)
    public void createPlateau(String nomPlateau){
        plateauxFacade.create(new Plateaux(nomPlateau, 0));
        placerPieces(nomPlateau, 0);
        placerPieces(nomPlateau, 1);
    }

    @Override
    @Interceptors(ExceptionInterceptor.class)
    public void createJoueur(int couleur, String nomPlateau){
        Joueurs joueur = new Joueurs(Integer.MIN_VALUE, couleur, nomPlateau);
        joueursFacade.create(joueur);
    }

    @Override
    @Interceptors(ExceptionInterceptor.class)
    public List<Plateaux> getPlateaux() {
        return plateauxFacade.findAll();
    }

    @Override
    @Interceptors(ExceptionInterceptor.class)
    public List<Pions> getPions() {
        return pionsFacade.findAll();
    }

    @Override
    @Interceptors(ExceptionInterceptor.class)
    public void createPion(String nom, int positionX, int positionY, String nomPlateau, int couleur) {
        Pions pion = new Pions();
        pion.setIdPion(Integer.MIN_VALUE);
        pion.setNomPion(nom);
        pion.setPositionX(positionX);
        pion.setPositionY(positionY);
        pion.setNomPlateau(nomPlateau);
        pion.setCouleur(couleur);
        pionsFacade.create(pion);
    }

    private void placerPieces(String nomPlateau, int couleur) {
        if(couleur == 0){
            //Blanc
            createPion("Tour", 7, 0, nomPlateau, couleur);
            createPion("Cavalier", 7, 1, nomPlateau, couleur);
            createPion("Fou", 7, 2, nomPlateau, couleur);
            createPion("Reine", 7, 3, nomPlateau, couleur);
            createPion("Roi", 7, 4, nomPlateau, couleur);
            createPion("Fou", 7, 5, nomPlateau, couleur);
            createPion("Cavalier", 7, 6, nomPlateau, couleur);
            createPion("Tour", 7, 7, nomPlateau, couleur);

            createPion("Pion", 6, 0, nomPlateau, couleur);
            createPion("Pion", 6, 1, nomPlateau, couleur);
            createPion("Pion", 6, 2, nomPlateau, couleur);
            createPion("Pion", 6, 3, nomPlateau, couleur);
            createPion("Pion", 6, 4, nomPlateau, couleur);
            createPion("Pion", 6, 5, nomPlateau, couleur);
            createPion("Pion", 6, 6, nomPlateau, couleur);
            createPion("Pion", 6, 7, nomPlateau, couleur);
        }

        if(couleur == 1){
            //Noir
            createPion("Tour", 0, 0, nomPlateau, couleur);
            createPion("Cavalier", 0, 1, nomPlateau, couleur);
            createPion("Fou", 0, 2, nomPlateau, couleur);
            createPion("Reine", 0, 3, nomPlateau, couleur);
            createPion("Roi", 0, 4, nomPlateau, couleur);
            createPion("Fou", 0, 5, nomPlateau, couleur);
            createPion("Cavalier", 0, 6, nomPlateau, couleur);
            createPion("Tour", 0, 7, nomPlateau, couleur);

            createPion("Pion", 1, 0, nomPlateau, couleur);
            createPion("Pion", 1, 1, nomPlateau, couleur);
            createPion("Pion", 1, 2, nomPlateau, couleur);
            createPion("Pion", 1, 3, nomPlateau, couleur);
            createPion("Pion", 1, 4, nomPlateau, couleur);
            createPion("Pion", 1, 5, nomPlateau, couleur);
            createPion("Pion", 1, 6, nomPlateau, couleur);
            createPion("Pion", 1, 7, nomPlateau, couleur);
        }
    }

    @Override
    @Interceptors(ExceptionInterceptor.class)
    public List<Pions> findByNomPlateau(String nomPlateau) {
        return pionsFacade.findByNomPlateau(nomPlateau);
    }

    @Override
    public boolean bougerPion(String nom, int couleur, String nomPlateau, int oldX, int oldY, int posX, int posY){
        Pions pion = pionsFacade.findByNomCouleurNomPlateauXY(nom, couleur, nomPlateau, oldX, oldY);
        Pions pionOld = null;
        try{
            pionOld = pionsFacade.findByPositionXY(posX, posY);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(pionOld != null && pionOld.getCouleur() != couleur){
            pionsFacade.remove(pionOld);
        }
        pion.setPositionX(posX);
        pion.setPositionY(posY);

        pionsFacade.edit(pion);
        return true;
    }

    @Override
    public int rejoindrePartie(String nomPlateau){
        Plateaux plateau = plateauxFacade.findByName(nomPlateau);
            if(plateau.getNombreDeJoueurs() == 0){
                plateau.setNombreDeJoueurs(plateau.getNombreDeJoueurs() + 1);
                plateauxFacade.edit(plateau);
                createJoueur(0, nomPlateau);
                return 0;
            }else if(plateau.getNombreDeJoueurs() == 1){
                plateau.setNombreDeJoueurs(plateau.getNombreDeJoueurs() + 1);
                plateauxFacade.edit(plateau);
                List<Joueurs> joueurs = joueursFacade.findByNomPlateau(nomPlateau);
                int couleur = joueurs.get(0).getCouleur();
                int couleurLibre = (couleur == 0 ? 1 : 0);
                createJoueur(couleurLibre, nomPlateau);
                return couleurLibre;
            }else{
                return -1;
            }
    }

    @Override
    public void quitterPartie(String nomPlateau, int couleur){
        System.err.println("!!! QUITTER PARTIE !!!");
        System.err.println("nomPlateau: " + nomPlateau);
        System.err.println("couleur: " + couleur);
        Plateaux plateau = plateauxFacade.findByName(nomPlateau);
        plateau.setNombreDeJoueurs(plateau.getNombreDeJoueurs() - 1);
        Joueurs joueur = joueursFacade.findByCouleurNomPlateau(couleur, nomPlateau);
        System.err.println("joueur.id: " + joueur.getIdJoueur());
        joueursFacade.remove(joueur);
        plateauxFacade.edit(plateau);
    }

    @Override
    public int getIdJoueur(String nomPlateau, int couleur) {
        return joueursFacade.findByCouleurNomPlateau(couleur, nomPlateau).getIdJoueur();
    }

    @Override
    public void deletePlateau(String nomPlateau){
        Plateaux plateau = plateauxFacade.findByName(nomPlateau);
        List<Pions> pions = pionsFacade.findByNomPlateau(nomPlateau);
        for(Pions pion : pions){
            pionsFacade.remove(pion);
        }
        plateauxFacade.remove(plateau);
    }
}
