/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Bean.Jdbc_Oracle;
import Beans.BeanCaddy;
import Beans.BeanUser;
import Threads.ThreadTimeout;
import java.beans.Beans;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Greenlamp
 */
public class ServletLogin extends HttpServlet {

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

        if(action == null){
            rediriger(request, response, sc, "/index.jsp");
        }else if(action.equals("login")){
            gestionLogin(request, response, sc);
        }else{
            rediriger(request, response, sc, "/index.jsp");
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
    private void rediriger(HttpServletRequest request, HttpServletResponse response, ServletContext sc, String lien) {
        try {
            RequestDispatcher rd = sc.getRequestDispatcher(lien);
            rd.forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
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

    private void gestionLogin(HttpServletRequest request, HttpServletResponse response, ServletContext sc) {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        if(check_oracle(login, pass)){
            BeanUser beanUser = new BeanUser(login, pass);
            this.addVariableSession(request, "beanUser", beanUser);
            getCaddy(request, sc);
            ThreadTimeout timeOut = new ThreadTimeout(request.getSession(true), sc);
            Thread threadTimeOut = new Thread(timeOut);
            threadTimeOut.start();
            this.addVariableSession(request, "threadTimeOut", threadTimeOut);


            try {
                response.sendRedirect("ServletControle");
            } catch (IOException ex) {
                Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                response.sendRedirect("error.jsp?message=\"Le nom d'utilisateur ou le mot de passe est incorrect.\"");
            } catch (IOException ex) {
                Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private boolean check_oracle(String login, String pass) {
        boolean found = false;
        try {
            Jdbc_Oracle dbOracle = (Jdbc_Oracle) Beans.instantiate(null, "Bean.Jdbc_Oracle");
            dbOracle.init();
            String request = "SELECT login from gestionnaires where login = '"+login+"' and password = '"+pass+"'";
            ResultSet tuples = (ResultSet)dbOracle.select(request);
            while(tuples.next() && found == false){
                found = true;
            }
            dbOracle.endExtract();
            dbOracle.Disconnect();
        } catch (Exception ex) {
            Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return found;
    }

    private void getCaddy(HttpServletRequest request, ServletContext sc) {
        HttpSession session = request.getSession(true);
        BeanUser beanUser = (BeanUser) session.getAttribute("beanUser");
        BeanCaddy beanCaddy = null;
        String idSession = (String) sc.getAttribute(beanUser.getLogin());
        if(idSession == null){
            beanCaddy = new BeanCaddy();
            sc.setAttribute(beanUser.getLogin(), session.getId());
        }else{
            beanCaddy = (BeanCaddy) sc.getAttribute(idSession);
        }
        session.setAttribute("beanCaddy", beanCaddy);
    }


}
