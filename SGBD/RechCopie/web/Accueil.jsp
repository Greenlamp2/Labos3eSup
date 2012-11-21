<%--
    Document   : Accueil
    Created on : 27-oct.-2012, 11:36:38
    Author     : Greenlamp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page errorPage="error.jsp"%>
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
        <title>Accueil</title>
    </head>
    <body>
        <h1 align="center">Accueil</h1>
        <p align="center">
            <a href="?action=Accueil">Accueil</a>
            <a href="?action=Recherche">Recherche</a>
            <a href="?action=caddy">Caddy</a>
            <a href="?action=deconnecter">Deconnecter</a>
        </p>
    </body>
</html>
