/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Exceptions.ExceptionInterceptor;
import Facades.JoueurFacadeLocal;
import Facades.PieceFacadeLocal;
import Facades.PlateauFacadeLocal;
import Others.EchiquierE;
import ant.Joueur;
import ant.Piece;
import ant.Plateau;
import ant.Roi;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author Greenlamp
 */
@Stateful
public class SessionBean implements SessionBeanRemote {
    @EJB
    private PieceFacadeLocal pieceFacade;
    @EJB
    private JoueurFacadeLocal joueurFacade;
    @EJB
    private PlateauFacadeLocal plateauFacade;
    @PersistenceUnit
    EntityManagerFactory factory;
    @PersistenceContext
    EntityManager entityManager;



    /**********************************************************************/
    /*Plateau*/
    /**********************************************************************/
    @Override
    @Interceptors(ExceptionInterceptor.class)
    public List<Plateau> getPlateaux() throws Exception{
        return plateauFacade.findAll();
    }

    @Override
    @Interceptors(ExceptionInterceptor.class)
    public void createPlateau(String nom) throws Exception{
        Plateau plateau = new Plateau();
        plateau.init(nom);
        plateauFacade.create(plateau);
    }

    @Override
    @Interceptors(ExceptionInterceptor.class)
    public Plateau getPlateauByNom(String nom) throws Exception{
        return plateauFacade.getByNom(nom);
    }

    @Override
    @Interceptors(ExceptionInterceptor.class)
    public Joueur rejoindrePartie(String nomPlateau) throws Exception{
        Plateau plateau = plateauFacade.getByNom(nomPlateau);
        if(plateau.getJoueur1() == null && plateau.getJoueur2() == null){
            Joueur joueur = new Joueur(Color.WHITE);
            joueurFacade.create(joueur);
            plateau.setJoueur1(joueur);
            plateauFacade.edit(plateau);
            return joueur;
        }
        if(plateau.getJoueur1() != null && plateau.getJoueur2() == null){
            Joueur joueur = new Joueur(Color.BLACK);
            joueurFacade.create(joueur);
            plateau.setJoueur2(joueur);
            plateauFacade.edit(plateau);
            return joueur;
        }
        return null;
    }

    @Override
    @Interceptors(ExceptionInterceptor.class)
    public List<Piece> getPieceByPlateau(String nomPlateau) throws Exception{
        Plateau plateau = plateauFacade.getByNom(nomPlateau);
        Collection<Piece> listePiece;
        listePiece = plateau.getListePiece();
        return new ArrayList<Piece>(listePiece);
    }

    @Override
    public void deletePlateau(String nomPlateau) throws Exception{
        Plateau plateau = plateauFacade.getByNom(nomPlateau);
        plateauFacade.remove(plateau);
    }


    /**********************************************************************/
    /*Piece*/
    /**********************************************************************/

    @Override
    public Piece getPieceAt(String nomPlateau, int posX, int posY) throws Exception{
        Plateau plateau = plateauFacade.getByNom(nomPlateau);
        if(plateau == null){
            return null;
        }
        Piece piece = plateau.getPiece(posX, posY);
        return piece;
    }

    @Override
    public List<Point> getDeplacementPossible(String nomPlateau, int posX, int posY) throws Exception{
        Plateau plateau = plateauFacade.getByNom(nomPlateau);
        if(plateau == null){
            return new ArrayList<Point>();
        }
        Piece selectedPiece = plateau.getPiece(posX, posY);
        if(selectedPiece == null){
            return new ArrayList<Point>();
        }
        Collection<Piece> listePiece = plateau.getListePiece();
        EchiquierE[][] cases = new EchiquierE[8][8];
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                cases[x][y] = new EchiquierE();
            }
        }
        for(Piece piece : listePiece){
            cases[piece.getPosX()][piece.getPosY()].setPiece(piece);
        }

        return selectedPiece.getDeplacementPossible(cases);
    }

    @Override
    public boolean bougerPion(String nomPlateau, int oldX, int oldY, int newX, int newY) throws Exception{
        Plateau plateau = plateauFacade.getByNom(nomPlateau);
        if(plateau == null){
            return false;
        }
        List<Point> listePoints = getDeplacementPossible(nomPlateau, oldX, oldY);
        boolean autoriser = false;
        for(Point point : listePoints){
            if(point.getX() == newX && point.getY() == newY){
                autoriser = true;
                break;
            }
        }

        //Deplacement non autorisé
        if(!autoriser){
            return false;
        }

        //On se déplace sur une case avec déja une piece.
        Piece newPiece = plateau.getPiece(newX, newY);
        String nom = null;
        if(newPiece != null){
            nom = newPiece.getNom();
            plateau.removePiece(newX, newY);
            plateauFacade.edit(plateau);
            pieceFacade.remove(newPiece);
        }

        //On se déplace normalement
        Piece piece = plateau.getPiece(oldX, oldY);
        piece.setPosX(newX);
        piece.setPosY(newY);
        pieceFacade.edit(piece);
        return true;

    }



    /**********************************************************************/
    /*Joueur*/
    /**********************************************************************/
    @Override
    @Interceptors(ExceptionInterceptor.class)
    public Long getIdJoueur(String nomPlateau, Color color) throws Exception{
        Plateau plateau = plateauFacade.getByNom(nomPlateau);
        Joueur joueur = plateau.getJoueurByCouleur(color);
        return joueur.getId();
    }

    @Override
    @Interceptors(ExceptionInterceptor.class)
    public void quitterPartie(String nomPlateau, Color color) throws Exception{
        Plateau plateau = plateauFacade.getByNom(nomPlateau);
        if(plateau != null){
            for(Piece piece : plateau.getListePiece()){
                pieceFacade.remove(piece);
            }
            plateau.clearPiece();
            plateauFacade.edit(plateau);
            Joueur joueur = plateau.quitterPlateau(color);
            if(joueur != null){
                joueurFacade.remove(joueur);
            }
            plateauFacade.edit(plateau);
            int nombreDeJoueurs = plateau.getNombreDeJoueurs();
            if(nombreDeJoueurs == 0){
                deletePlateau(nomPlateau);
            }
        }
    }

    public boolean onEchec(String nomPlateau, Color color) throws Exception{
        boolean retour = false;
        Plateau plateau = plateauFacade.getByNom(nomPlateau);
        if(plateau == null) return false;
        System.err.println("plateau ok");

        Collection<Piece> listePiece = plateau.getListePiece();
        EchiquierE[][] cases = new EchiquierE[8][8];
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                cases[x][y] = new EchiquierE();
            }
        }
        for(Piece piece : listePiece){
            cases[piece.getPosX()][piece.getPosY()].setPiece(piece);
        }
        System.err.println("init case ok");

        for(Piece piece : plateau.getListePiece()){
            if(piece.getNom().equalsIgnoreCase("Roi") && piece.getColor().getRGB() == color.getRGB()){
                System.err.println("on a trouvé le roi");
                Roi roi = (Roi)piece;
                retour = roi.isOnEchec(cases, color);
            }
        }
        return retour;
    }

    public boolean onEchecEtMat(String nomPlateau, Color color) throws Exception{
        boolean retour = false;
        Plateau plateau = plateauFacade.getByNom(nomPlateau);
        if(plateau == null) return false;
        Collection<Piece> listePiece = plateau.getListePiece();
        EchiquierE[][] cases = new EchiquierE[8][8];
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                cases[x][y] = new EchiquierE();
            }
        }
        for(Piece piece : listePiece){
            cases[piece.getPosX()][piece.getPosY()].setPiece(piece);
        }

        for(Piece piece : plateau.getListePiece()){
            if(piece.getNom().equalsIgnoreCase("Roi") && piece.getColor().getRGB() == color.getRGB()){
                System.err.println("on a trouvé le roi");
                Roi roi = (Roi)piece;
                Roi roiTemp = new Roi(roi.getPosX()-1, roi.getPosY()-1, roi.getColor());
                if(roiTemp.isOnEchecEtMat(cases, color)){
                    roiTemp = new Roi(roi.getPosX(), roi.getPosY()-1, roi.getColor());
                    if(roiTemp.isOnEchecEtMat(cases, color)){
                        roiTemp = new Roi(roi.getPosX()+1, roi.getPosY()-1, roi.getColor());
                        if(roiTemp.isOnEchecEtMat(cases, color)){
                            roiTemp = new Roi(roi.getPosX()-1, roi.getPosY(), roi.getColor());
                            if(roiTemp.isOnEchecEtMat(cases, color)){
                                roiTemp = new Roi(roi.getPosX(), roi.getPosY(), roi.getColor());
                                if(roiTemp.isOnEchecEtMat(cases, color)){
                                    roiTemp = new Roi(roi.getPosX()+1, roi.getPosY(), roi.getColor());
                                    if(roiTemp.isOnEchecEtMat(cases, color)){
                                        roiTemp = new Roi(roi.getPosX()-1, roi.getPosY()+1, roi.getColor());
                                        if(roiTemp.isOnEchecEtMat(cases, color)){
                                            roiTemp = new Roi(roi.getPosX(), roi.getPosY()+1, roi.getColor());
                                            if(roiTemp.isOnEchecEtMat(cases, color)){
                                                roiTemp = new Roi(roi.getPosX()+1, roi.getPosY()+1, roi.getColor());
                                                if(roiTemp.isOnEchecEtMat(cases, color)){
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return retour;

    }
}
