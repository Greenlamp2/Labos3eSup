<%--
    Document   : Recherche
    Created on : 27-oct.-2012, 11:38:23
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
        <title>Recherche</title>
    </head>
    <body>
        <h1 align="center">Recherche</h1>
        <p align="center">
            <a href="?action=Accueil">Accueil</a>
            <a href="?action=Recherche">Recherche</a>
            <a href="?action=caddy">Caddy</a>
            <a href="?action=deconnecter">Deconnecter</a>
        </p>
            <form action="ServletControle" method="post">
                <br/>
                <table align="center">
                    <tr><td colspan="2"><input type="checkbox" name="rech_popularite"/>Recherche par popularité (Nombre de ticket vendus)[Popularité]</td></tr>
                    <tr><td width="220">Nombre de ticket minimum vendu:</td><td><input type ="text" name="ticketMin" value="1"/></td></tr>
                    <tr><td width="220">Nombre de ticket maximum vendu:</td><td><input type ="text" name="ticketMax" value="150"/></td></tr>
                    <tr><td></td></tr>
                    <tr><td colspan="2"><input type="checkbox" name="rech_frequence"/>Recherche par fréquence de programmation (Nombre de séance sur une semaine)[Fréquence]</td></tr>
                    <tr><td width="220">Nombre de séance minimum:</td><td><input type ="text" name="seanceMin" value="1"/></td></tr>
                    <tr><td width="220">Nombre de séance maximum:</td><td><input type ="text" name="seanceMax" value="20"/></td></tr>

                    <tr><td colspan="2"><input type="checkbox" name="rech_dureeProg"/>Recherche par durée de programmation (Nombre de semaines de présence)[Pérénité]</td></tr>
                    <tr><td width="220">Nombre de semaine minimum:</td><td><input type ="text" name="semaineMin" value="1"/></td></tr>
                    <tr><td width="220">Nombre de semaine maximum:</td><td><input type ="text" name="semaineMax" value="6"/></td></tr>

                    <tr><td colspan="2"><input type="checkbox" name="rech_copieDispos"/>Recherche par nombre de copies disponibles (non programmée)</td></tr>
                    <tr><td width="220">Nombre de copies minimum:</td><td><input type ="text" name="copieMin" value="1"/></td></tr>
                    <tr><td width="220">Nombre de copies maximum:</td><td><input type ="text" name="copieMax" value="4"/></td></tr>


                    <tr><td colspan="2"><input type="checkbox" name="rech_copieNonPresente"/>Copies non présentes dans le complexe cinématographique 1</td></tr>
                    <tr><td width="220">Nombre de films par page:</td><td><input type="text" name="nbPage" value="3"/></td></tr>
                    <input type="hidden" name="action" value="Rechercher"/>
                    <tr><td colspan="2"><input type="submit" value="Rechercher"/></td></tr>
                </table>
            </form>
    </body>
</html>
