/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.BeanCaddy;
import Beans.BeanDetails;
import Beans.BeanMovie;
import Beans.BeanMovieCaddy;
import Beans.BeanResultat;
import Beans.BeanUser;
import Databases.OracleAccess;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Greenlamp
 */

//RechCopie
//Css
//Film non présent dans cc1 sans affichage du détails

//CmdCopie
//Page de confirmation
//Type de support*Fait*
//Gestion des erreurs
//Multi-Commande avec checkbox qui récupere le nb de copie dans les textBox.
public class ServletControle extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext sc = getServletContext();

        BeanUser beanUser = (BeanUser) getVariableSession(request, "beanUser");
        if(beanUser != null && beanUser.getLogin() != null){
            OracleAccess oracle = (OracleAccess)getVariableSession(request, "oracle");
            if(oracle == null){
                oracle = new OracleAccess("CC1");
                addVariableSession(request, "oracle", oracle);
            }
        }

        String action = null;
        action = request.getParameter("action");
        traitementAction(request, response, sc, action);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


    /******************************************************************************************************************************/
    /*METHODES UTILITAIRES*/
    /******************************************************************************************************************************/
    private void rediriger(HttpServletRequest request, HttpServletResponse response, ServletContext sc, String lien) {
        try {
            RequestDispatcher rd = sc.getRequestDispatcher(lien);
            rd.forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletControle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Object getVariableSession(HttpServletRequest request, String key){
        HttpSession session = request.getSession(true);
        Object object = session.getAttribute(key);
        return object;
    }

    private void addVariableSession(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession(true);
        session.setAttribute(key, value);
    }

    private void delVariableSession(HttpServletRequest request, String key){
        HttpSession session = request.getSession(true);
        session.removeAttribute(key);
    }

    private void setCaddy(HttpServletRequest request, ServletContext sc){
        HttpSession session = request.getSession(true);
        BeanCaddy beanCaddy = (BeanCaddy) session.getAttribute("beanCaddy");
        String idSession = session.getId();
        sc.setAttribute(idSession, beanCaddy);
    }

    /******************************************************************************************************************************/
    /*GESTION DES DIFFERENTES ACTIONS
    /******************************************************************************************************************************/
    private void traitementAction(HttpServletRequest request, HttpServletResponse response, ServletContext sc, String action) {
        if(action == null){
            this.rediriger(request, response, sc, "/Accueil.jsp");
        }else if(action.equals("Recherche")){
            this.rediriger(request, response, sc, "/Recherche.jsp");
        }else if(action.equals("Rechercher")){
            actionRechercher(request, response, sc);
            this.rediriger(request, response, sc, "/Results.jsp");
        }else if(action.equals("resultat")){
            this.rediriger(request, response, sc, "/Results.jsp");
        }else if(action.equals("getImage")){
            int idMovie = Integer.parseInt(request.getParameter("idMovie"));
            BeanResultat beanResultat = (BeanResultat)getVariableSession(request, "beanResultat");
            LinkedList<BeanMovie> listMovie = beanResultat.getListMovie();
            for(BeanMovie beanMovie : listMovie){
                if(beanMovie.getId() == idMovie){
                    Blob imageBlob = beanMovie.getAffiche();
                    response.setContentType("image/jpeg");
                    byte[] bytes;
                    try {
                        bytes = imageBlob.getBytes(1, (int)imageBlob.length());
                        bytes = resize(bytes, 138, 97);
                        response.setContentLength(bytes.length);
                        response.getOutputStream().write(bytes);
                        response.getOutputStream().flush();
                        response.getOutputStream().close();
                    } catch (Exception ex) {
                        Logger.getLogger(ServletControle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }else if(action.equals("getImageCaddy")){
            int idMovie = Integer.parseInt(request.getParameter("idMovie"));
            BeanCaddy beanCaddy = (BeanCaddy)getVariableSession(request, "beanCaddy");
            LinkedList<BeanMovieCaddy> listMovie = beanCaddy.getListMovie();
            for(BeanMovieCaddy beanMovie : listMovie){
                if(beanMovie.getId() == idMovie){
                    Blob imageBlob = beanMovie.getAffiche();
                    response.setContentType("image/jpeg");
                    byte[] bytes;
                    try {
                        bytes = imageBlob.getBytes(1, (int)imageBlob.length());
                        bytes = resize(bytes, 138, 97);
                        response.setContentLength(bytes.length);
                        response.getOutputStream().write(bytes);
                        response.getOutputStream().flush();
                        response.getOutputStream().close();
                    } catch (Exception ex) {
                        Logger.getLogger(ServletControle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }else if(action.equals("details")){
            actionDetails(request, response, sc);
            this.rediriger(request, response, sc, "/Details.jsp");
        }else if(action.equals("getPage")){
            actionGetPage(request, response, sc);
            this.rediriger(request, response, sc, "/Results.jsp");
        }else if(action.equals("getPageCaddy")){
            this.rediriger(request, response, sc, "/Caddy.jsp?numPage=" + request.getParameter("numPage"));
        }else if(action.equals("trier")){
            actionTrier(request, response, sc);
            this.rediriger(request, response, sc, "/Results.jsp");
        }else if(action.equals("commander")){
            actionCommander(request, response, sc);
            rediriger(request, response, sc, "/Caddy.jsp?numPage=0");
        }else if(action.equals("deconnecter")){
            actionDeconnecter(request, response, sc);
            rediriger(request, response, sc, "/index.jsp");
        }else if(action.equals("caddy")){
            rediriger(request, response, sc, "/Caddy.jsp?numPage=0");
        }else if(action.equals("actionCaddy")){
            traiterActionCaddy(request, response, sc);
            rediriger(request, response, sc, "/Caddy.jsp?numPage=0");
        }else if(action.equals("error")){
            rediriger(request, response, sc, "/error.jsp");
        }else if(action.equals("validerCaddy")){
            actionValiderCaddy(request, response, sc);
            rediriger(request, response, sc, "/Caddy.jsp?numPage=0");
        }else{
            this.rediriger(request, response, sc, "/Accueil.jsp");
        }
    }

    private void actionRechercher(HttpServletRequest request, HttpServletResponse response, ServletContext sc) {
        OracleAccess oracle = (OracleAccess)getVariableSession(request, "oracle");

        int nbByPage = Integer.parseInt(request.getParameter("nbPage"));
        String rech_copieNonPresente = request.getParameter("rech_copieNonPresente");
        String requete = null;
        String rech_popularite = request.getParameter("rech_popularite");
        String ticketMin = request.getParameter("ticketMin");
        String ticketMax = request.getParameter("ticketMax");

        String rech_frequence = request.getParameter("rech_frequence");
        String seanceMin = request.getParameter("seanceMin");
        String seanceMax = request.getParameter("seanceMax");

        String rech_dureeProg = request.getParameter("rech_dureeProg");
        String semaineMin = request.getParameter("semaineMin");
        String semaineMax = request.getParameter("semaineMax");

        String rech_copieDispos = request.getParameter("rech_copieDispos");
        String copieMin = request.getParameter("copieMin");
        String copieMax = request.getParameter("copieMax");

        if(rech_popularite != null){
            if(requete == null){
                requete = "select mo.idMovie from movies mo where(select sum(ct.nbre) from commanderTicket ct, projection p where ct.dateHeureProjection = p.dateHeureProjection AND ct.numeroSalle = p.numeroSalle and p.idCopie in (select idCopie from copies where idMovie = mo.idMovie))between "+ticketMin+" and "+ticketMax;
            }else{
                requete += " and idMovie in(select mo.idMovie from movies mo where(select sum(ct.nbre) from commanderTicket ct, projection p where ct.dateHeureProjection = p.dateHeureProjection AND ct.numeroSalle = p.numeroSalle and p.idCopie in (select idCopie from copies where idMovie = mo.idMovie))between "+ticketMin+" and "+ticketMax+")";
            }
        }

        if(rech_frequence != null){
            if(requete == null){
                requete = "select idMovie from movies mo where (select round((decode(max(dateHeureProjection), min(dateHeureProjection), 1, max(dateHeureProjection) - min(dateHeureProjection))) / ((to_number(to_char(max(dateHeureProjection), 'WW')) - to_number(to_char(min(dateHeureProjection), 'WW')) + 52 * (to_number(to_char(max(dateHeureProjection), 'YYYY')) - to_number(to_char(min(dateHeureProjection), 'YYYY'))))+1)) frequenceByWeek from dual, projection p where p.idCopie in(select idCopie from copies where idMovie = mo.idMovie)) between "+seanceMin+" and "+seanceMax;
            }else{
                requete += " and idMovie in(select idMovie from Movies mo where(select round((decode(max(dateHeureProjection), min(dateHeureProjection), 1, max(dateHeureProjection) - min(dateHeureProjection))) / ((to_number(to_char(max(dateHeureProjection), 'WW')) - to_number(to_char(min(dateHeureProjection), 'WW')) + 52 * (to_number(to_char(max(dateHeureProjection), 'YYYY')) - to_number(to_char(min(dateHeureProjection), 'YYYY'))))+1)) frequenceByWeek from dual, projection p where p.idCopie in(select idCopie from copies where idMovie = mo.idMovie)) between "+seanceMin+" and "+seanceMax+")";
            }
        }

        if(rech_dureeProg != null){
            if(requete == null){
                requete = "select idMovie from movies mo where (select ((to_number(to_char(max(p.dateHeureProjection), 'WW')) - to_number(to_char(min(p.dateHeureProjection), 'WW')) + 52 * (to_number(to_char(max(p.dateHeureProjection), 'YYYY')) - to_number(to_char(min(p.dateHeureProjection), 'YYYY'))))+1) diff_week from projection p where p.idCopie in (select idCopie from copies where idMovie = mo.idMovie)) between "+semaineMin+" and "+semaineMax;
            }else{
                requete += " and idMovie in(select idMovie from movies mo where (select ((to_number(to_char(max(p.dateHeureProjection), 'WW')) - to_number(to_char(min(p.dateHeureProjection), 'WW')) + 52 * (to_number(to_char(max(p.dateHeureProjection), 'YYYY')) - to_number(to_char(min(p.dateHeureProjection), 'YYYY'))))+1) diff_week from projection p where p.idCopie in (select idCopie from copies where idMovie = mo.idMovie)) between "+semaineMin+" and "+semaineMax+")";
            }
        }

        if(rech_copieDispos != null){
            if(requete == null){
                requete = "select idMovie from movies mo where(select count(*) from copies c where idMovie = mo.idMovie and idCopie not in(select idCopie from projection)) between "+copieMin+" and "+copieMax;
            }else{
                requete += " and idMovie in(select idMovie from movies mo where(select count(*) from copies c where idMovie = mo.idMovie and idCopie not in(select idCopie from projection)) between "+copieMin+" and "+copieMax+")";
            }
        }

        if(rech_copieNonPresente != null){
            if(requete == null){
                requete = "select distinct idMovie from COPIES_CB where idMovie not in(select distinct idMovie from copies)";
            }else{
                requete += " and idMovie in(select distinct idMovie from COPIES_CB where idMovie not in(select distinct idMovie from copies))";
            }
        }

        oracle.initCallFunc("PACKAGE_RECHERCHE", "doSearch", 1);

        String requeteCount = "select count(*) from(";
        requeteCount += requete + ")";

        String requeteLimitee = getRequeteLimite(0, nbByPage, requete);
        BeanResultat beanResultat = oracle.doGetListMovies(requeteLimitee);
        if(beanResultat != null){
            oracle.initCallFunc("PACKAGE_RECHERCHE", "getCount", 1);
            int numResultat = oracle.doGetCount(requeteCount);
            beanResultat.setNumResultat(numResultat);
            beanResultat.setRequete(requete);
            beanResultat.setNbResultByPage(nbByPage);
        }
        addVariableSession(request, "beanResultat", beanResultat);
    }

    public String getRequeteLimite(int min, int max, String requete){
        String requeteLimitee = "select idMovie from ( select a.*, ROWNUM rnum from(";
        requeteLimitee += requete + ") a) where rnum between "+ min +" and " + max;

        return requeteLimitee;
    }

    private void actionGetPage(HttpServletRequest request, HttpServletResponse response, ServletContext sc) {
        int numpage = Integer.parseInt(request.getParameter("numPage"));
        BeanResultat beanResultat = (BeanResultat)getVariableSession(request, "beanResultat");

        int min = numpage * beanResultat.getNbResultByPage() + 1;
        int max = min + beanResultat.getNbResultByPage() - 1;

        String requeteLimite = getRequeteLimite(min, max, beanResultat.getRequete());
        System.out.println("RequeteLimite: " + requeteLimite);

        OracleAccess oracle = (OracleAccess)getVariableSession(request, "oracle");
        oracle.initCallFunc("PACKAGE_RECHERCHE", "doSearch", 1);
        BeanResultat beanResultat2 = oracle.doGetListMovies(requeteLimite);
        beanResultat.setListMovie(beanResultat2.getListMovie());

        addVariableSession(request, "beanResultat", beanResultat);

    }

    private void actionDetails(HttpServletRequest request, HttpServletResponse response, ServletContext sc) {
        int idMovie = Integer.parseInt(request.getParameter("idMovie"));
        OracleAccess oracle = (OracleAccess)getVariableSession(request, "oracle");
        oracle.initCallFunc("PACKAGE_RECHERCHE", "getDetails", 1);
        BeanDetails beanDetails = oracle.doGetDetails(idMovie);
        addVariableSession(request, "beanDetails", beanDetails);
    }

    public byte[] resize(byte[] img, int hauteur, int largeur) {
        InputStream is = new ByteArrayInputStream(img);
        BufferedImage in = null;
        try {
            in = ImageIO.read(is);
        } catch (IOException ex) {
            Logger.getLogger(ServletControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedImage newImage = new BufferedImage(largeur, hauteur,
            BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.clearRect(0, 0, largeur, hauteur);
            g.drawImage(in, 0, 0, largeur, hauteur, null);
        } finally {
            g.dispose();
        }
        byte[] bytesOut = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(newImage, "jpg", baos);
            bytesOut = baos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(ServletControle.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bytesOut;
    }

    private void actionTrier(HttpServletRequest request, HttpServletResponse response, ServletContext sc) {
        String type = request.getParameter("type");
        BeanResultat beanResultat = (BeanResultat) getVariableSession(request, "beanResultat");
        if(type.equals("popularite")){
            beanResultat.triByPopularite();
        }else if(type.equals("frequence")){
            beanResultat.triByFrequence();
        }else if(type.equals("perenite")){
            beanResultat.triByPerenite();
        }
        addVariableSession(request, "beanResultat", beanResultat);
    }

    private void actionCommander(HttpServletRequest request, HttpServletResponse response, ServletContext sc) {
        int idMovie = Integer.parseInt(request.getParameter("idMovie"));
        int quantite = Integer.parseInt(request.getParameter("quantite"));
        BeanCaddy beanCaddy = (BeanCaddy) getVariableSession(request, "beanCaddy");
        boolean found = false;
        if(beanCaddy == null){
            beanCaddy = new BeanCaddy();
        }else{
            for(BeanMovieCaddy beanMovieCaddy : beanCaddy.getListMovie()){
                if(beanMovieCaddy.getId() == idMovie){
                    beanMovieCaddy.addQuantite(quantite);
                    found = true;
                    break;
                }
            }
        }
        if(found == false){
            BeanResultat beanResultat = (BeanResultat) getVariableSession(request, "beanResultat");
            BeanMovieCaddy beanMovieToAdd = null;
            for(BeanMovie beanMovie : beanResultat.getListMovie()){
                if(beanMovie.getId() == idMovie){
                    beanMovieToAdd = new BeanMovieCaddy(beanMovie);
                    beanMovieToAdd.setQuantite(quantite);
                    break;
                }
            }
            beanCaddy.add(beanMovieToAdd);
        }
        addVariableSession(request, "beanCaddy", beanCaddy);
        setCaddy(request, sc);
    }

    private void actionDeconnecter(HttpServletRequest request, HttpServletResponse response, ServletContext sc) {
        Thread threadTimeOut = (Thread)getVariableSession(request, "threadTimeOut");
        threadTimeOut.stop();
        delVariableSession(request, "threadTimeOut");
        HttpSession session = request.getSession(true);
        BeanUser beanUser = (BeanUser) getVariableSession(request, "beanUser");
        //sc.removeAttribute(session.getId());
        //sc.removeAttribute(beanUser.getLogin());
        //delVariableSession(request, "beanCaddy");
        delVariableSession(request, "beanUser");
    }

    private void actionDeleteItemsCaddy(HttpServletRequest request, HttpServletResponse response, ServletContext sc) {
        Enumeration<String> parameterNames = request.getParameterNames();
        ArrayList<String> listeParametre = Collections.list(parameterNames);
        LinkedList<Integer> listeId = new LinkedList<Integer>();
        for(String param : listeParametre){
            if(param.contains("checkbox")){
                String checkbox = request.getParameter(param);
                if(checkbox != null){
                    String[] split = param.split("checkbox");
                    String num = split[1];
                    int id = Integer.parseInt(num);
                    listeId.add(id);
                }
            }
        }

        BeanCaddy beanCaddy = (BeanCaddy) getVariableSession(request, "beanCaddy");
        int cpt = 0;
        int count = listeId.size();
        while(cpt < count){
            int i = 0;
            int id = listeId.get(cpt);
            int size = beanCaddy.getListMovie().size();
            while(i < size){
                if(beanCaddy.getListMovie().get(i).getId() == id){
                    beanCaddy.getListMovie().remove(i);
                    cpt++;
                    break;
                }
                i++;
            }
        }
        addVariableSession(request, "beanCaddy", beanCaddy);
        setCaddy(request, sc);
    }

    private void traiterActionCaddy(HttpServletRequest request, HttpServletResponse response, ServletContext sc) {
        String bouton = null;
        Enumeration<String> parameterNames = request.getParameterNames();
        ArrayList<String> listeParametre = Collections.list(parameterNames);
        int id = 0;
        for(String param : listeParametre){
            if(param.contains("bouton")){
                bouton = request.getParameter(param);
                String[] split = param.split("bouton");
                id = Integer.parseInt(split[1]);
                break;
            }
        }
        if(bouton.equals("Supprimer")){
            actionDeleteItemsCaddy(request, response, sc);
        }else if(bouton.equals("Ajouter")){
            actionAjouterItemCaddy(request, response, sc, id);
        }else if(bouton.equals("Retirer")){
            actionRetirerItemCaddy(request, response, sc, id);
        }else if(bouton.equals("Vider")){
            actionViderItemCaddy(request, response, sc);
        }else if(bouton.equals("switcher")){
            actionSwitcherDigitale(request, response, sc, id);
        }
    }

    private void actionAjouterItemCaddy(HttpServletRequest request, HttpServletResponse response, ServletContext sc, int id) {
        int quantiteAdd = Integer.parseInt(request.getParameter("quantiteAdd"));
        if(quantiteAdd < 0){
            quantiteAdd *= -1;
        }
        BeanCaddy beanCaddy = (BeanCaddy) getVariableSession(request, "beanCaddy");
        for(BeanMovieCaddy beanMovieCaddy : beanCaddy.getListMovie()){
            if(beanMovieCaddy.getId() == id){
                beanMovieCaddy.addQuantite(quantiteAdd);
                break;
            }
        }
        addVariableSession(request, "beanCaddy", beanCaddy);
        setCaddy(request, sc);
    }

    private void actionRetirerItemCaddy(HttpServletRequest request, HttpServletResponse response, ServletContext sc, int id) {
        int quantiteRemove = Integer.parseInt(request.getParameter("quantiteRemove"));
        BeanCaddy beanCaddy = (BeanCaddy) getVariableSession(request, "beanCaddy");
        for(BeanMovieCaddy beanMovieCaddy : beanCaddy.getListMovie()){
            if(beanMovieCaddy.getId() == id){
                int qte = beanMovieCaddy.getQuantite();
                if(qte - quantiteRemove < 1){
                    beanMovieCaddy.removeQuantite(0);
                }else{
                    beanMovieCaddy.removeQuantite(quantiteRemove);
                }
                break;
            }
        }
        addVariableSession(request, "beanCaddy", beanCaddy);
        setCaddy(request, sc);
    }

    private void actionViderItemCaddy(HttpServletRequest request, HttpServletResponse response, ServletContext sc) {
        BeanCaddy beanCaddy = (BeanCaddy) getVariableSession(request, "beanCaddy");
        beanCaddy.getListMovie().clear();
        addVariableSession(request, "beanCaddy", beanCaddy);
        setCaddy(request, sc);
    }

    private void actionValiderCaddy(HttpServletRequest request, HttpServletResponse response, ServletContext sc) {
        OracleAccess oracle = (OracleAccess)getVariableSession(request, "oracle");
        BeanCaddy beanCaddy = (BeanCaddy) getVariableSession(request, "beanCaddy");
        LinkedList<Integer> listInserer = new LinkedList<Integer>();
        for(BeanMovieCaddy beanMovieCaddy : beanCaddy.getListMovie()){
            oracle.initCallProc("PACKAGE_RECHERCHE", "insertCommande", 3);
            boolean ok = oracle.doInsertCommande(beanMovieCaddy.getId(), beanMovieCaddy.getQuantite(), beanMovieCaddy.getPhysique());
            if(ok){
                listInserer.add(beanMovieCaddy.getId());
            }
        }

        int taille = beanCaddy.getListMovie().size();
        for(int idInserer : listInserer){
            int i = 0;
            while(i < taille){
                if(beanCaddy.getListMovie().get(i).getId() == idInserer){
                    beanCaddy.getListMovie().remove(i);
                    break;
                }
                i++;
            }
        }
        addVariableSession(request, "beanCaddy", beanCaddy);
        setCaddy(request, sc);
    }

    private void actionSwitcherDigitale(HttpServletRequest request, HttpServletResponse response, ServletContext sc, int id) {
        BeanCaddy beanCaddy = (BeanCaddy) getVariableSession(request, "beanCaddy");
        for(BeanMovieCaddy beanMovieCaddy : beanCaddy.getListMovie()){
            if(beanMovieCaddy.getId() == id){
                if(beanMovieCaddy.getPhysique() == 1){
                    beanMovieCaddy.setPhysique(0);
                }else{
                    beanMovieCaddy.setPhysique(1);
                }
            }
        }
        addVariableSession(request, "beanCaddy", beanCaddy);
        setCaddy(request, sc);
    }

}
