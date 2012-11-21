<%--
    Document   : Recap
    Created on : 11-nov.-2012, 14:54:37
    Author     : Greenlamp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="beanProjections" scope="session" class="Beans.BeanProjections"/>
<jsp:useBean id="beanCmdTicket" scope="session" class="Beans.BeanCmdTicket"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recapitulatif</title>
    </head>
    <body>
        <h1 align="center">Recapitulatif</h1>

        <% String messageErreur = (String)request.getSession(true).getAttribute("messageErreur"); %>
        <% String date = (String)request.getSession(true).getAttribute("date"); %>
        <% int numeroSalle = (Integer)request.getSession(true).getAttribute("numeroSalle"); %>
        <% int nbTicket = (Integer)request.getSession(true).getAttribute("nbTicket"); %>
        <% if(messageErreur != null){ %>
            <%=messageErreur%>
            <a href="ServletControle?action=view">Retour</a>
        <% }else{ %>
        <p align="center"><a href="ServletControle?action=view">Retour</a></p>
        <p align="center">Veuillez imprimer ce ticket et le présenter à la caisse du complexe pour récupérer vos tickets.</p>
            <table border align="center">
                <tr>
                    <td colspan="4" align="center"><%=beanProjections.getProj(beanCmdTicket.getIdMovie()).getNameMovie()%></td>
                </tr>
                <tr>
                    <td colspan="4" align="center">Date projection: <%=date%></td>
                </tr>
                <tr>
                    <td colspan="4" align="center">numero de la salle: <%=numeroSalle%></td>
                </tr>
                <tr>
                    <td colspan="4" align="center">nombre de ticket: <%=nbTicket%></td>
                </tr>
            </table>
        <% } %>


    </body>
</html>
