<%--
    Document   : caddy
    Created on : 26-oct.-2012, 14:15:51
    Author     : Greenlamp
--%>

<%@page import="Beans.BeanMovies"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="beanUser" scope="session" class="Beans.BeanUser"/>
<jsp:useBean id="beanCaddy" scope="session" class="Beans.BeanCaddy"/>
<% if(beanUser == null || beanUser.getLogin() == null){ %>
<% RequestDispatcher rd; %>
<% ServletContext sc = getServletContext(); %>
<% rd = sc.getRequestDispatcher("/Servlet_Controle?action=error&message=\"Impossible d'accéder à cette page sans être authentifié au préalable.\"");%>
<% rd.forward(request, response); %>
<% } %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Caddy</title>
    </head>
    <body>
        <h1>Caddy</h1>
        <a href="?action=accueil">Accueil</a>
        <a href="?action=caddy">Caddy</a>
        <a href="?action=deconnecter">deconnecter</a>
        <table align="center" border="1">
            <tr>
                <td align="center">id: </td>
                <td align="center">nom :</td>
            </tr>
            <%for(BeanMovies beanMovie : beanCaddy.getListMovies()){%>
            <tr>
                <td align="center"><%=beanMovie.getIdMovie()%></td>
                <td align="center"><%=beanMovie.getName()%></td>
                <td>
                <form action="ServletControle" method="post">
                    <input type="hidden" name="idReservation" value="<%=beanMovie.getIdMovie()%>"/>
                    <input type="hidden" name="action" value="retraitCaddy"/>
                    <input type="submit" value="Retirer"/>
                </form>
                </td>
            </tr>
            <%}%>
            </table>
            <form action="ServletControle" method="post">
                <input type="hidden" name="action" value="payer"/>
                <input type="submit" value="Payer"/>
            </form>
    </body>
</html>
