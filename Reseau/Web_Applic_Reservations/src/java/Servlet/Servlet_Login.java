/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Bean.BeanCaddy;
import Bean.BeanChambre;
import Bean.BeanMagasin;
import Bean.BeanUser;
import Bean.Jdbc_MySQL;
import java.beans.Beans;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "Servlet_Login", urlPatterns = {"/Servlet_Login"})
public class Servlet_Login extends HttpServlet {

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

        String action = request.getParameter("action");

        if(action.equals("login")){
            gestionLogin(request, response);
        }else if(action.equals("register")){
            gestionRegister(request, response);
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

    private void gestionLogin(HttpServletRequest request, HttpServletResponse response) {
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String numero = request.getParameter("numero");

        String[] split = user.split(" ");
        String prenom = split[0];
        String nom = split[1];
        int id = -1;
        if((id = check_Mysql(nom, prenom, pass)) != -1){
            try {
                getServletContext().setAttribute("numero", numero);
                BeanUser beanUser = new BeanUser(nom, prenom, id);
                BeanCaddy beanCaddy = recupererCaddy(nom, prenom);

                this.addVariableSession(request, "beanUser", beanUser);
                this.addVariableSession(request, "beanCaddy", beanCaddy);
                response.sendRedirect("Servlet_Controle");
            } catch (IOException ex) {
                Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                response.sendRedirect("error.jsp");
            } catch (IOException ex) {
                Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void gestionRegister(HttpServletRequest request, HttpServletResponse response) {
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String numero = request.getParameter("numero");

        String[] split = user.split(" ");
        String prenom = split[0];
        String nom = split[1];

        int id = existeDeja(nom, prenom);
        if(id == -1){
            creerUser(nom, prenom, pass);
            id = existeDeja(nom, prenom);
            getServletContext().setAttribute("numero", numero);
            BeanUser beanUser = new BeanUser(nom, prenom, id);

            this.addVariableSession(request, "beanUser", beanUser);
            try {
                response.sendRedirect("Servlet_Controle");
            } catch (IOException ex) {
                Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                response.sendRedirect("error.jsp?message=\"nom ou prénom déja utilisé.\"");
            } catch (IOException ex) {
                Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private int check_Mysql(String nom, String prenom, String pass) {
        int id = -1;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = ""
                    + "SELECT id "
                    + "FROM voyageurs "
                    + "WHERE nom = '" + nom + "' "
                    + "AND prenom = '" + prenom + "' "
                    + "AND password = '" + pass + "'";

            Object tuples = dbsql.select(request);
            id = Integer.parseInt(dbsql.extract(tuples, 1, "id"));
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    private BeanMagasin recupererMagasin() {
        BeanMagasin beanMagasin = null;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            int count = dbsql.count("chambres");
            if(count != 0){
                String request = "SELECT * FROM chambres";
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

    private BeanCaddy recupererCaddy(String nom, String prenom) {
        BeanCaddy beanCaddy = null;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            int count = dbsql.count("reservations");
            if(count != 0){
                String request = "SELECT * FROM reservations, voyageurs "
                        + "WHERE reservations.titulaire = voyageurs.id "
                        + "AND voyageurs.nom = '"+nom+"' AND voyageurs.prenom = '"+prenom+"'";
                Object tuples = dbsql.select(request);
                beanCaddy = new BeanCaddy();
                for(int i=1; i<count+1; i++){
                    String idReservation = dbsql.extract(tuples, i, "id");
                    int numeroChambre = Integer.parseInt(dbsql.extract(tuples, i, "chambre"));
                    int prix = getPrixChambre(numeroChambre);
                    String date = dbsql.extract(tuples, i, "date_reservation");
                    String heure = dbsql.extract(tuples, i, "heure_reservation");
                    beanCaddy.addReservation(idReservation, numeroChambre, prix, date, heure);
                }
                dbsql.endExtract();
                dbsql.Disconnect();
            }
        } catch (Exception ex) {
            Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        return beanCaddy;
    }

    private int getPrixChambre(int numeroChambre) {
        int prix = 0;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT prix_htva FROM chambres WHERE numero = '"+numeroChambre+"'";
            Object tuples = dbsql.select(request);
            prix = Integer.parseInt(dbsql.extract(tuples, 1, "prix_htva"));
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        return prix;
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

    private int existeDeja(String nom, String prenom) {
        int id = -1;
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String request = "SELECT id from voyageurs where nom = '"+nom+"' AND prenom = '"+prenom+"'";
            ResultSet tuples = (ResultSet)dbsql.select(request);
            while(tuples.next()){
                id = tuples.getInt("id");
            }
            dbsql.endExtract();
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(Servlet_Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    private void creerUser(String nom, String prenom, String pass) {
        try {
            Jdbc_MySQL dbsql = (Jdbc_MySQL) Beans.instantiate(null, "Bean.Jdbc_MySQL");
            dbsql.init();
            String requete = "INSERT INTO voyageurs(nom, prenom, password) VALUES('" + nom + "','" + prenom + "','" +  pass + "')";
            dbsql.update(requete);
            dbsql.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(Servlet_Controle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
