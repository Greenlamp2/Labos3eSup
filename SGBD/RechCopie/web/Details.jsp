<%--
    Document   : Details
    Created on : 30-oct.-2012, 11:23:25
    Author     : Greenlamp
--%>

<%@page import="Beans.BeanMovie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page errorPage="error.jsp"%>
<jsp:useBean id="beanDetails" scope="session" class="Beans.BeanDetails"/>
<jsp:useBean id="beanResultat" scope="session" class="Beans.BeanResultat"/>
<jsp:useBean id="beanUser" scope="session" class="Beans.BeanUser"/>
<% if(beanUser == null || beanUser.getLogin() == null){ %>
    <% RequestDispatcher rd; %>
    <% ServletContext sc = getServletContext(); %>
    <% rd = sc.getRequestDispatcher("/ServletControle?action=error&message=\"Impossible d'accéder à cette page sans être authentifié au préalable.\"");%>
    <% rd.forward(request, response); %>
<% } %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Details</title>
    </head>
    <body>
        <h1 align="center">Details</h1>
        <p align="center">
            <a href="?action=Accueil">Accueil</a>
            <a href="?action=Recherche">Recherche</a>
            <a href="?action=caddy">Caddy</a>
            <a href="?action=deconnecter">Deconnecter</a>
        </p>
        <br/>
        <% BeanMovie beanMovie = null; %>
        <% for(BeanMovie movie : beanResultat.getListMovie()){ %>
            <% if(movie.getId() == beanDetails.getId()){ beanMovie = movie; }%>
        <%}%>


        <table align="center">
            <tr>
                <td>
                    <% String urlServlet = "ServletControle?action=getImage&idMovie=" + beanMovie.getId();%>
                    <img src="<%=urlServlet%>"/>
                </td>
            </tr>
        </table>
        <table border align="center" width="400">
            <tr>
                <td align="center" width="50"><span style="font-weight:bold">Identifiant</span></td>
                <td align="center" width="150"><%=beanDetails.getId()%></td>
                <td align="center" width="50"><span style="font-weight:bold">Titre</span></td>
                <td align="center" width="150"><%=beanMovie.getNom()%></td>
            </tr>
            <tr>
                <td colspan="2" align="center" width="200"><span style="font-weight:bold">Date de sortie</span></td>
                <td colspan="2" align="center" width="200"><%=beanDetails.getFormatedDate()%></td>
            </tr>
            <tr>
                <td colspan="2" align="center" width="200"><span style="font-weight:bold">Popularité</span></td>
                <td colspan="2" align="center" width="200"><%=beanMovie.getPopularite()%></td>
            </tr>
            <tr>
                <td colspan="2" align="center" width="200"><span style="font-weight:bold">Fréquence</span></td>
                <td colspan="2" align="center" width="200"><%=beanMovie.getFrequenceProg()%></td>
            </tr>
            <tr>
                <td colspan="2" align="center" width="200"><span style="font-weight:bold">Pérénité</span></td>
                <td colspan="2" align="center" width="200"><%=beanMovie.getPerenite()%></td>
            </tr>
            <tr>
                <td colspan="2" align="center" width="200"><span style="font-weight:bold">Acteurs</span></td>
                <td colspan="2" align="center" width="200"><span style="font-weight:bold">Réalisateurs</span></td>
            </tr>
            <tr>
                <td colspan="2" valign="top" align="center" width="200">
                    <% for(String acteur : beanDetails.getListActors()){ %>
                        <%=acteur%><br/>
                    <%}%>
                </td>
                <td colspan="2" valign="top" align="center" width="200">
                    <% for(String realisateur : beanDetails.getListDirectors()){ %>
                    <%=realisateur%><br/>
                    <%}%>
                </td>
            </tr>
        </table>

                <p align="center"><a href="ServletControle?action=resultat"><span style="font-weight:bold">Retour</span></a></p>




    </body>
</html>
