<%--
    Document   : Seances
    Created on : 10-nov.-2012, 22:13:37
    Author     : Greenlamp
--%>

<%@page import="Beans.BeanHoraire"%>
<%@page import="Beans.BeanProjection"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="beanProjections" scope="session" class="Beans.BeanProjections"/>
<jsp:useBean id="beanHoraires" scope="session" class="Beans.BeanHoraires"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Seances</title>
    </head>
    <body>
        <h1 align="center">Seances</h1>

        <% for(BeanHoraire horaire : beanHoraires.getListeHoraires()){ %>
        <table border align="center" width ="400">
            <tr>
                <td colspan="4" align="center"><%=beanProjections.getProj(horaire.getIdMovie()).getNameMovie()%></td>
            </tr>
            <tr>
                <% String urlServlet = "ServletControle?action=getImage&idMovie=" + beanProjections.getProj(horaire.getIdMovie()).getIdMovie();%>
                <td colspan="1" align="center" width="100"><img width="97" height="138" src="<%=urlServlet%>"/></td>
                <td colspan="3" align="center" width ="300">
                    <table width ="300">
                        <% for(String jour : horaire.getJours()){ %>
                        <tr>
                            <td colspan="1" width ="70"><%=jour%></td>
                            <td colspan="3">
                                <% for(String heure : horaire.getAgenda().get(jour)){ %>
                                    <a href="ServletControle?action=commander&idMovie=<%=horaire.getIdMovie()%>&jour=<%=jour%>&heure=<%=heure%>"><%=heure%></a>
                                <% } %>
                            </td>
                        </tr>
                        <% } %>
                    </table>
                </td>
            </tr>
            </table>
            <br/>
        <% } %>
    </body>
</html>
