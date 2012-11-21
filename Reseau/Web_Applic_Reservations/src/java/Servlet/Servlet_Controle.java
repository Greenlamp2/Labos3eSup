/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Bean.BeanCaddy;
import Bean.BeanChambre;
import Bean.BeanMagasin;
import Bean.BeanReservation;
import Bean.BeanUser;
import Bean.Jdbc_MySQL;
import Thread.TimeOutReservations;
import java.beans.Beans;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Greenlamp
 */
@WebServlet(name = "Servlet_Controle", urlPatterns = {"/Servlet_Controle"})
public class Servlet_Controle extends HttpServlet {
    public int numeroChance = 0;
    TimeOutReservations timeOutReservations = new TimeOutReservations();
    Boolean started = startThread(timeOutReservations);
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
        String action = null;
        action = request.getParameter("action");

        BeanUser beanUser = (BeanUser)getVariableSession(request, "beanUser");
        if(beanUser == null || beanUser.getNom() == null){
            rediriger(request, response, sc, "/error.jsp");
        }else if(action == null){
            String message = request.getParameter("message");
            rediriger(request, response, sc, "/accueil.jsp?message=" + message);
        }else if(action.equals("error")){
            rediriger(request, response, sc, "/magasin.jsp");
        }else if(action.equals("magasin")){
            creerMagasin(request, response, sc);
            rediriger(request, response, sc, "/magasin.jsp");
        }else if(action.equals("caddy")){
            creerMagasin(request, response, sc);
            rediriger(request, response, sc, "/caddy.jsp");
        }else if(action.equals("ajoutCaddy")){
            actionAjoutCaddy(request, response, sc, beanUser.getId());
            creerMagasin(request, response, sc);
            rediriger(request, response, sc, "/magasin.jsp");
        }else if(action.equals("retraitCaddy")){
            actionRetraitCaddy(request, response, sc, beanUser.getId());
            creerMagasin(request, response, sc);
            rediriger(request, response, sc, "/caddy.jsp");
        }else if(action.equals("payement")){
            rediriger(request, response, sc, "/payement.jsp");
        }else if(action.equals("payer")){
            actionPayer(request, response, sc, beanUser.getId());
            rediriger(request, response, sc, "/accueil.jsp");
        }
        else{
            rediriger(request, response, sc, "/accueil.jsp");
        }
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

    /***********************************************************************************************/
    /*UTILITAIRES*/
    /***********************************************************************************************/
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

    private void rediriger(HttpServletRequest request, HttpServletResponse response, ServletContext sc, String lien) {
        try {
            RequestDispatcher rd = sc.getRequestDispatcher(lien);
            rd.forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(Servlet_Controle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servlet_Controle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /***********************************************************************************************/
    /*ACTIONS*/
    /***********************************************************************************************/
    private void actionAjoutCaddy(HttpServletRequest request, HttpServletResponse response, ServletContext sc, int id) {
        int numeroChambre = Integer.parseInt(request.getParameter("numeroChambre"));
        if(!dejaReserver(numeroChambre)){
            String idReservation = genererIdReservation(numeroChambre, id);
            String date = getDateNow();
            String heure = getHeureNow();
            creerReservation(idReservation, numeroChambre, id, date, heure);
            BeanCaddy beanCaddy = (BeanCaddy) getVariableSession(request, "beanCaddy");
            if(beanCaddy == null){
                beanCaddy = new BeanCaddy();
            }
            BeanMagasin beanMagasin = (BeanMagasin)getVariableSession(request, "beanMagasin");
            beanCaddy.addReservation(idReservation, numeroChambre, beanMagasin.getPrix(numeroChambre), date, heure);
            addVariableSession(request, "beanCaddy", beanCaddy);
        }
    }

    private void actionRetraitCaddy(HttpServletRequest request, HttpServletResponse response, ServletContext sc, int id) {
        String idReservation = request.getParameter("idReservation");
        supprimerReservation(idReservation, id);
        BeanCaddy beanCaddy = (BeanCaddy) getVariableSession(request, "beanCaddy");
        beanCaddy.removeReservation(idReservation);
        addVariableSession(request, "beanCaddy", beanCaddy);
    }

    private BeanMagasin recupererMagasin() {
        BeanMagasin beanMagasin = null;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            int count = dbsql.count("chambres");
            if(count != 0){
                String request = "SELECT * FROM chambres WHERE numero NOT IN (SELECT chambre FROM reservations)";
                Object tuples = dbsql.select(request);
                beanMagasin = new BeanMagasin();
                for(int i=1; i<count+1; i++){
                    int numero = Integer.parseInt(dbsql.extract(tuples, i, "numero"));
                    int nbOccupant = Integer.parseInt(dbsql.extract(tuples, i, "nb_occupants"));
                    int prixHtva = Integer.parseInt(dbsql.extract(tuples, i, "prix_htva"));
                    int douche = Integer.parseInt(dbsql.extract(tuples, i, "douche"));
                    int baignoire = Integer.parseInt(dbsql.extract(tuples, i, "baignoire"));
                    int cuvette = Integer.parseInt(dbsql.extract(tuples, i, "cuvette"));
                    BeanChambre beanChambre = new BeanChambre(numero, nbOccupant, douche, baignoire, cuvette, prixHtva);
                    beanMagasin.addChambre(beanChambre);
                }
                dbsql.endExtract();
                dbsql.Disconnect();
            }
        } catch (Exception ex) {
            Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        return beanMagasin;
    }

    private void actionPayer(HttpServletRequest request, HttpServletResponse response, ServletContext sc, int id) {
        String numCarte = request.getParameter("numCarte");
        int total = Integer.parseInt(request.getParameter("total"));
        if(numCarte == null || numCarte.isEmpty()){
            rediriger(request, response, sc, "/error.jsp?message=\"Numéro de carte de crédit invalide.\"");
        }

        payerChambres(request);
    }

    /***********************************************************************************************/
    /*ACCES DB*/
    /***********************************************************************************************/
    private boolean dejaReserver(int numeroChambre) {
        boolean retour = false;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT id FROM reservations WHERE chambre = '"+numeroChambre+"'";

            Object tuples = dbsql.select(request);
            String id = dbsql.extract(tuples, 1, "id");
            if(id != null){
                retour = true;
            }
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retour;
    }

    private void creerReservation(String idReservation, int numeroChambre, int id, String date, String heure) {
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "INSERT INTO reservations(id, chambre, payé, titulaire, date_reservation, heure_reservation) VALUES('"+idReservation+"', '"+numeroChambre+"', '0', '"+id+"', '"+date+"', '"+heure+"')";
            dbsql.update(request);
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(Servlet_Controle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getIdLastReservation() {
        String retour = null;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT id FROM reservations";

            int count = dbsql.count("reservations");

            Object tuples = dbsql.select(request);
            for(int i=1; i<count+1; i++){
                retour = dbsql.extract(tuples, i, "id");
            }
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retour;
    }

    private void supprimerReservation(String idReservation, int id) {
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "DELETE FROM reservations WHERE id = '"+idReservation+"' AND titulaire = '"+id+"'";
            dbsql.update(request);
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void payerChambre(String idReservation) {
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "update reservations set payé='1' where id='"+idReservation+"';";
            dbsql.update(request);
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(Servlet_Controle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /***********************************************************************************************/
    /*METIER*/
    /***********************************************************************************************/
    private String genererIdReservation(int numeroChambre, int id) {
        String idLastReservation = getIdLastReservation();
        if(idLastReservation != null){
            int num = recupNumId(idLastReservation);
            num += 1;
            String numId = null;
            if(num < 10){
                numId = "0" + num;
            }else{
                numId = String.valueOf(num);
            }
            Date now = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String date = dateFormat.format(now);
            String idReservation = date + "-RES" + numId;
            return idReservation;
        }else{
            String numId = "01";
            Date now = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String date = dateFormat.format(now);
            String idReservation = date + "-RES" + numId;
            return idReservation;
        }
    }

    private int recupNumId(String idLastReservation) {
        String[] split = idLastReservation.split("-RES");
        String partieDroite = split[1];
        int numId = Integer.parseInt(partieDroite);
        return numId;
    }

    private void creerMagasin(HttpServletRequest request, HttpServletResponse response, ServletContext sc) {
        BeanMagasin beanMagasin = recupererMagasin();
        this.addVariableSession(request, "beanMagasin", beanMagasin);
    }

    private String getDateNow() {
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(now);
        return date;
    }

    private String getHeureNow() {
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String heure = dateFormat.format(now);
        return heure;
    }

    private void payerChambres(HttpServletRequest request) {
        BeanCaddy beanCaddy = (BeanCaddy) getVariableSession(request, "beanCaddy");
        for(BeanReservation beanReservation: beanCaddy.getListeReservation()){
            payerChambre(beanReservation.getIdReservation());
        }
        beanCaddy.viderCaddy();
        addVariableSession(request, "beanCaddy", beanCaddy);
    }

    private Boolean startThread(TimeOutReservations timeOutReservations) {
        Thread threadTimeOut = new Thread(timeOutReservations);
        threadTimeOut.start();
        return true;
    }
}
