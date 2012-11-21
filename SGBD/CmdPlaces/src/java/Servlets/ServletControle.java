/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.BeanCmdTicket;
import Beans.BeanHoraire;
import Beans.BeanHoraires;
import Beans.BeanMovie;
import Beans.BeanProjection;
import Beans.BeanProjections;
import Database.OracleAccess;
import java.io.IOException;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    OracleAccess oracle = null;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = null;
        action = request.getParameter("action");
        ServletContext sc = getServletContext();

        oracle = (OracleAccess)getVariableSession(request, "oracle");
        if(oracle == null){
            oracle = new OracleAccess("CC1");
            addVariableSession(request, "oracle", oracle);
        }

        BeanProjections beanProjections = (BeanProjections) getVariableSession(request, "beanProjections");
        if(beanProjections == null){
            oracle.initCallFunc("PACKAGE_PROGRAMMATION", "getAllProjection", 1);
            beanProjections = oracle.doGetAllProjection("ALL");
            initBeanMovie(beanProjections);
            addVariableSession(request, "beanProjections", beanProjections);
        }

        if(action == null){
            rediriger(request, response, sc, "/index.jsp");
        }else if(action.equals("view")){
            rassemblerProjection(request);
            rediriger(request, response, sc, "/Seances.jsp");
        }else if(action.equals("getImage")){
            int idMovie = Integer.parseInt(request.getParameter("idMovie"));
            for(BeanProjection proj : beanProjections.getListeBeanProjection()){
                if(proj.getIdMovie() == idMovie){
                    Blob imageBlob = proj.getBeanMovie().getAffiche();
                    response.setContentType("image/jpeg");
                    byte[] bytes;
                    try {
                        bytes = imageBlob.getBytes(1, (int)imageBlob.length());
                        response.setContentLength(bytes.length);
                        response.getOutputStream().write(bytes);
                        response.getOutputStream().flush();
                        response.getOutputStream().close();
                    } catch (Exception ex) {
                        Logger.getLogger(ServletControle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
        }else if (action.equals("commander")){
            actionCommander(request);
            rediriger(request, response, sc, "/Details.jsp");
        }else if(action.equals("CommanderTicket")){
            actionCommanderTicket(request);
            rediriger(request, response, sc, "/Recap.jsp");
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

    /******************************************************************************************************************************/
    /*METHODES METIERS*/
    /******************************************************************************************************************************/
    private BeanProjections initProjections() {
        oracle.initCallFunc("PACKAGE_PROGRAMMATION", "getAllProjection", 1);
        BeanProjections projections = oracle.doGetAllProjection("ALL");
        return projections;
    }

    private void actionView() {
        Date today = new Date();
        System.out.println("today: " + new SimpleDateFormat("dd/MM/YYYY").format(new Date()));
    }

    private void initBeanMovie(BeanProjections beanProjections) {
        for(BeanProjection proj : beanProjections.getListeBeanProjection()){
            oracle.initCallFunc("PACKAGE_PROGRAMMATION", "getDetail", 1);
            proj.setBeanMovie(oracle.doGetDetail(proj.getIdMovie()));
        }
        System.out.println("test");
    }

    private void rassemblerProjection(HttpServletRequest request) {
        BeanProjections beanProjections = (BeanProjections) getVariableSession(request, "beanProjections");
        BeanHoraires beanHoraires = new BeanHoraires();
        String today = new SimpleDateFormat("dd/MM/YYYY").format(new Date());
        Calendar c = new GregorianCalendar();

        c.setTime(new Date());
        int dayToday = c.get(Calendar.DAY_OF_WEEK);
        String dayTodayStr = getDay(dayToday);

        c.add(Calendar.HOUR, 24);
        int dayTomorow = c.get(Calendar.DAY_OF_WEEK);
        String dayTomorowStr = getDay(dayTomorow);
        Date tomorow = c.getTime();

        c.add(Calendar.HOUR, 24);
        Date afterTomorow = c.getTime();
        int dayAfterTomorow = c.get(Calendar.DAY_OF_WEEK);
        String dayAfterTomorowStr = getDay(dayAfterTomorow);

        BeanProjections projectionsJour = beanProjections.getProjection(today);
        String testTomorow = new SimpleDateFormat("dd/MM/YYYY").format(tomorow);
        BeanProjections projectionsTomorow = beanProjections.getProjection(testTomorow);
        String testAfterTomorow = new SimpleDateFormat("dd/MM/YYYY").format(afterTomorow);
        BeanProjections projectionsAfterTomorow = beanProjections.getProjection(testAfterTomorow);

        if(projectionsJour != null){
            for(BeanProjection proj : projectionsJour.getListeBeanProjection()){
                String heure = new SimpleDateFormat("HH:mm").format(proj.getDateHeureProjection());
                beanHoraires.addHeure(proj.getIdMovie(), heure);
                beanHoraires.addHeure(dayTodayStr, proj.getIdMovie(), heure);
            }
            addVariableSession(request, "projectionsJour", projectionsJour);
        }

        if(projectionsTomorow != null){
            for(BeanProjection proj : projectionsTomorow.getListeBeanProjection()){
                String heure = new SimpleDateFormat("HH:mm").format(proj.getDateHeureProjection());
                beanHoraires.addHeure(proj.getIdMovie(), heure);
                beanHoraires.addHeure(dayTomorowStr, proj.getIdMovie(), heure);
            }
            addVariableSession(request, "projectionsTomorow", projectionsTomorow);
        }

        if(projectionsAfterTomorow != null){
            for(BeanProjection proj : projectionsAfterTomorow.getListeBeanProjection()){
                String heure = new SimpleDateFormat("HH:mm").format(proj.getDateHeureProjection());
                beanHoraires.addHeure(proj.getIdMovie(), heure);
                beanHoraires.addHeure(dayAfterTomorowStr, proj.getIdMovie(), heure);
            }
            addVariableSession(request, "projectionsAfterTomorow", projectionsAfterTomorow);
        }

        addVariableSession(request, "beanHoraires", beanHoraires);
    }

    private String getDay(int nbDay) {
        switch(nbDay){
            case GregorianCalendar.MONDAY:
                return "Lundi";
            case GregorianCalendar.TUESDAY:
                return "Mardi";
            case GregorianCalendar.WEDNESDAY:
                return "Mercredi";
            case GregorianCalendar.THURSDAY:
                return "Jeudi";
            case GregorianCalendar.FRIDAY:
                return "Vendredi";
            case GregorianCalendar.SATURDAY:
                return "Samedi";
            case GregorianCalendar.SUNDAY:
                return "Dimanche";
        }
        return null;
    }

    private void actionCommander(HttpServletRequest request) {
        int idMovie = Integer.parseInt(request.getParameter("idMovie"));
        String jour = request.getParameter("jour");
        String heure = request.getParameter("heure");

        BeanHoraires beanHoraires = (BeanHoraires) getVariableSession(request, "beanHoraires");
        BeanProjections projectionsJour = (BeanProjections) getVariableSession(request, "projectionsJour");
        BeanProjections projectionsTomorow = (BeanProjections) getVariableSession(request, "projectionsTomorow");
        BeanProjections projectionsAfterTomorow = (BeanProjections) getVariableSession(request, "projectionsAfterTomorow");

        Calendar c = new GregorianCalendar();
        c.setTime(projectionsJour.getListeBeanProjection().getFirst().getDateHeureProjection());
        int dayToday = c.get(Calendar.DAY_OF_WEEK);
        String dayTodayStr = getDay(dayToday);

        c.setTime(projectionsTomorow.getListeBeanProjection().getFirst().getDateHeureProjection());
        int dayTomorow = c.get(Calendar.DAY_OF_WEEK);
        String dayTomorowStr = getDay(dayTomorow);

        c.setTime(projectionsAfterTomorow.getListeBeanProjection().getFirst().getDateHeureProjection());
        int dayAfterTomorow = c.get(Calendar.DAY_OF_WEEK);
        String dayAfterTomorowStr = getDay(dayAfterTomorow);

        String dateProjection = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(jour.equalsIgnoreCase(dayTodayStr)){
            dateProjection = sdf.format(projectionsJour.getListeBeanProjection().getFirst().getDateHeureProjection());
        }else if(jour.equalsIgnoreCase(dayTomorowStr)){
            dateProjection = sdf.format(projectionsTomorow.getListeBeanProjection().getFirst().getDateHeureProjection());
        }else if(jour.equalsIgnoreCase(dayAfterTomorowStr)){
            dateProjection = sdf.format(projectionsAfterTomorow.getListeBeanProjection().getFirst().getDateHeureProjection());
        }
        dateProjection += " " + heure;


        oracle.initCallFunc("PACKAGE_CMDTICKET", "getCmdDetails", 2);
        BeanCmdTicket beanCmdTicket = oracle.doGetCmdDetails(dateProjection, idMovie);
        addVariableSession(request, "beanCmdTicket", beanCmdTicket);
    }

    private void actionCommanderTicket(HttpServletRequest request) {
        String date = request.getParameter("date");
        int numeroSalle = Integer.parseInt(request.getParameter("salle"));
        int nbTicket = Integer.parseInt(request.getParameter("nbPlace"));
        oracle.initCallProc("PACKAGE_CMDTICKET", "acheterTicket", 3);
        String messageErreur = oracle.doAcheterTicket(date, numeroSalle, nbTicket);
        addVariableSession(request, "messageErreur", messageErreur);
        addVariableSession(request, "date", date);
        addVariableSession(request, "numeroSalle", numeroSalle);
        addVariableSession(request, "nbTicket", nbTicket);
    }
}
