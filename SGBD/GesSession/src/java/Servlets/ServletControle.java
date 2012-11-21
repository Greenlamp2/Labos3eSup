/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Bean.Jdbc_Oracle;
import Beans.BeanCaddy;
import Beans.BeanMovies;
import Beans.BeanUser;
import Threads.ThreadTimeout;
import java.beans.Beans;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*Amélioration:
 * Ne pas perdre le caddy lorsqu'on ferme le navigateur. (meme avec un autre navigateur)
 * Mais on ne peut pas avoir d'identifiant de gestionnaire dans la trace de la transaction
 * Comme en réseau, sauf qu'on doit être indépendant de l'utilisateur
 */

/**
 *
 * @author Greenlamp
 */
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
        PrintWriter out = response.getWriter();
        ServletContext sc = getServletContext();

        ThreadTimeout timeOut = new ThreadTimeout(request.getSession(true));
        Thread threadTimeOut = new Thread(timeOut);
        threadTimeOut.start();

        String action = null;
        action = request.getParameter("action");
        BeanUser beanUser = (BeanUser)getVariableSession(request, "beanUser");
        if(beanUser == null || beanUser.getLogin() == null){
            rediriger(request, response, sc, "/error.jsp");
        }else if(action == null){
            rediriger(request, response, sc, "/accueil.jsp");
        }else if(action.equals("rechercher")){
            actionRechercher(request, response, sc);
            rediriger(request, response, sc, "/caddy.jsp");
        }else if(action.equals("caddy")){
            rediriger(request, response, sc, "/caddy.jsp");
        }else if(action.equals("deconnecter")){
            actionDeconnecter(request, response, sc);
            rediriger(request, response, sc, "/index.jsp");
        }else{
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

    private void delAllVariableSession(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        session.invalidate();
    }

    private void rediriger(HttpServletRequest request, HttpServletResponse response, ServletContext sc, String lien) {
        try {
            RequestDispatcher rd = sc.getRequestDispatcher(lien);
            rd.forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ServletControle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServletControle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actionRechercher(HttpServletRequest request, HttpServletResponse response, ServletContext sc) {
        int idMovie = Integer.parseInt(request.getParameter("idMovie"));

        BeanMovies beanMovie = getMovie(idMovie);
        if(beanMovie != null){
            BeanCaddy beanCaddy = (BeanCaddy)getVariableSession(request, "beanCaddy");
            if(beanCaddy == null){
                beanCaddy = new BeanCaddy();
            }
            beanCaddy.add(beanMovie);
            addVariableSession(request, "beanMovie", "beanMovie");
        }
    }

    private BeanMovies getMovie(int idMovie) {
        BeanMovies beanMovie = null;
        try {
            Jdbc_Oracle dbOracle = (Jdbc_Oracle) Beans.instantiate(null, "Bean.Jdbc_Oracle");
            dbOracle.init();
            String request = "SELECT idMovie, name from Movies where idMovie = "+idMovie+"";
            ResultSet tuples = (ResultSet)dbOracle.select(request);
            while(tuples.next() && beanMovie == null){
                int id = tuples.getInt("idMovie");
                String name = tuples.getString("name");
                beanMovie = new BeanMovies(id, name);
            }
            dbOracle.endExtract();
            dbOracle.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return beanMovie;
    }

    private void actionDeconnecter(HttpServletRequest request, HttpServletResponse response, ServletContext sc) {
        delVariableSession(request, "beanUser");
        delVariableSession(request, "beanCaddy");
        Thread threadTimeOut = (Thread)getVariableSession(request, "threadTimeOut");
        threadTimeOut.stop();
        delVariableSession(request, "threadTimeOut");
    }
}
