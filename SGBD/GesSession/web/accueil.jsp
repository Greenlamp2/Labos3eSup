<%--
    Document   : accueil
    Created on : 26-oct.-2012, 13:48:43
    Author     : Greenlamp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="beanUser" scope="session" class="Beans.BeanUser"/>
<% if(beanUser == null || beanUser.getLogin() == null){ %>
<% RequestDispatcher rd; %>
<% ServletContext sc = getServletContext(); %>
<% rd = sc.getRequestDispatcher("/Servlet_Controle?action=error&message=\"Impossible d'accéder à cette page sans être authentifié au préalable.\"");%>
<% rd.forward(request, response); %>
<% } %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Accueil</title>
    </head>
    <body>
        <h1>Accueil</h1>
        <a href="?action=accueil">Accueil</a>
        <a href="?action=caddy">Caddy</a>
        <a href="?action=deconnecter">deconnecter</a>
        <form action="ServletControle" method="post">
            <table align="center">
                <tr><td>id film: </td><td><input type="text" name="idMovie" value="10895"/></td><tr/>
                <input type="hidden" name="action" value="rechercher"/>
                <tr><td></td><td><input type="submit" value="valider"/></td><tr/>
            </table>
        </form>
    </body>
</html>
