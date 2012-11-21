<%--
    Document   : Details
    Created on : 11-nov.-2012, 12:53:19
    Author     : Greenlamp
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Beans.BeanMovie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="beanProjections" scope="session" class="Beans.BeanProjections"/>
<jsp:useBean id="beanCmdTicket" scope="session" class="Beans.BeanCmdTicket"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Details</title>
    </head>
    <body>
        <h1>Details</h1>
        <a href="ServletControle?action=view">Séances</a>
        <table align="center">
        <form action="ServletControle" method="post">
            <tr><td>Nombre de places: <input type="text" name="nbPlace"/></td></tr>
            <input type="hidden" name="action" value="CommanderTicket"/>
            <% String dateString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(beanCmdTicket.getDate());%>
            <input type="hidden" name="date" value="<%=dateString%>"/>
            <input type="hidden" name="salle" value="<%=beanCmdTicket.getNumeroSalle()%>"/>
            <tr><td><input type="submit" value="commander"/></td></tr>
        </form>
        </table>
        <% BeanMovie movie = beanProjections.getProj(beanCmdTicket.getIdMovie()).getBeanMovie(); %>
        <table border align="center" width="600">
            <tr>
                <td colspan="4" align="center"><%=movie.getName()%></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    Salle:
                    <%=beanCmdTicket.getNumeroSalle()%>
                </td>
                <td colspan="2" align="center">
                    Nombre de places restantes:
                    <%=beanCmdTicket.getNbPlacesDispo()%>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    date de projection:
                    <% String dateProj = new SimpleDateFormat("dd/MM/yyyy").format(beanCmdTicket.getDate());%>
                    <%=dateProj%>
                </td>
                <td colspan="2" align="center">
                    Heure de projection:
                    <% String heureProj = new SimpleDateFormat("HH:mm").format(beanCmdTicket.getDate());%>
                    <%=heureProj%>
                </td>
            </tr>
            <tr>
                <% String urlServlet = "ServletControle?action=getImage&idMovie=" + beanCmdTicket.getIdMovie();%>
                <td colspan="1" width="100" align="center"><img width="97" height="138" src="<%=urlServlet%>"/></td>
                <td colspan="3" valign="top">
                    <table>
                        <tr>
                            <td colspan="3">Imdb id: <%=movie.getImdbId()%></td>
                        </tr>
                        <tr>
                            <% String dateFormat = new SimpleDateFormat("dd/MM/yyyy").format(movie.getDateSortie()); %>
                            <td colspan="3">Date de sortie: <%=dateFormat%></td>
                        </tr>
                        <tr>
                            <td colspan="3">Rating: <%=movie.getRating()%></td>
                        </tr>
                        <tr>
                            <td colspan="3">Nombre de votes <%=movie.getVotes()%></td>
                        </tr>
                        <tr>

                            <td colspan="3">
                                Trailer
                                <% if(movie.getTrailer() == null){ %>
                                    Pas de trailer
                                <% }else{ %>
                                    <a href="<%=movie.getTrailer()%>"><%=movie.getTrailer()%></a>
                                <% } %>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                Classification:
                                <% if(!movie.getCertifications().isEmpty()){ %>
                                    <% for(String classification : movie.getCertifications()){ %>
                                        <%=classification%>
                                    <% } %>
                                <% }else{ %>
                                    Aucunes
                                <% } %>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                Genres:
                                <% if(!movie.getGenres().isEmpty()){ %>
                                    <% for(String genre : movie.getGenres()){ %>
                                        <%=genre%>
                                    <% } %>
                                <% }else{ %>
                                    Aucuns
                                <% } %>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                Durée: <%=movie.getRuntime()%> Minutes
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                Sous-titré:
                                <% if(movie.isTraduit()){ %>
                                    Oui
                                <% }else{ %>
                                    Non
                                <% } %>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    Resumé:<br/>
                    <%=movie.getResume()%>
                </td>
            </tr>
            <tr>
                <td colspan="1" valign="top" width="150">
                    Réalisé par:<br/>
                    <% for(String realisateur : movie.getRealisateurs()){ %>
                        <%=realisateur%><br/>
                    <% } %>
                </td>
                <td colspan="1" valign="top" width="150">
                    Acteurs:<br/>
                    <% for(String acteur : movie.getActeurs()){ %>
                        <%=acteur%><br/>
                    <% } %>
                </td>
                <td colspan="1" valign="top" width="150">
                    Studios<br/>
                    <% for(String studio : movie.getStudios()){ %>
                        <%=studio%><br/>
                    <% } %>
                </td>
                <td colspan="1" valign="top" width="150">
                    Langues<br/>
                    <% for(String langue : movie.getLangues()){ %>
                        <%=langue%><br/>
                    <% } %>
                </td>
            </tr>
        </table>
    </body>
</html>
