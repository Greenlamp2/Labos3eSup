<%--
    Document   : caddy
    Created on : 18-oct.-2012, 10:12:50
    Author     : Greenlamp
--%>

<%@page import="Bean.BeanReservation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page errorPage="error.jsp"%>
<jsp:useBean id="beanUser" scope="session" class="Bean.BeanUser"/>
<% if(beanUser == null || beanUser.getNom() == null){ %>
<% RequestDispatcher rd; %>
<% ServletContext sc = getServletContext(); %>
<% rd = sc.getRequestDispatcher("/Servlet_Controle?action=error&message=\"Impossible d'accéder à cette page sans être authentifié au préalable.\"");%>
<% rd.forward(request, response); %>
<% } %>
<jsp:useBean id="beanCaddy" scope="session" class="Bean.BeanCaddy"/>
<jsp:useBean id="beanMagasin" scope="session" class="Bean.BeanMagasin"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" media="all" type="text/css" href="css/style.css" />
        <title>Caddy <%=beanUser.getNom()%></title>
    </head>
    <body>
        <div class="site">
            <div class="header">
                <span>Site de réservation de chambre</span>
            </div>
            <div class="clean"></div>
            <div class="corp">
                <div class="menu_v">
                    <div class="menu_v_top">Menu</div>
                    <div class="menu_v_ctn">
                        <ul>
                            <li><a href="?action=accueil">Accueil</a></li>
                            <li><a href="?action=magasin">Magasin</a></li>
                            <li><a href="?action=caddy">Caddy</a></li>
                        </ul>
                    </div>
                </div>
                <div class="corp_ctn">
                    <h1>Caddy</h1>
                    <div class="paragraphe">
                        <% if(beanCaddy.getListeReservation().size() == 0){ %>
                            Vous n'avez éffectué aucunes réservations.<br/>
                            <br/><br/>
                        <% }else{ %>
                        <table align="center" border="1">
                            <tr>
                                <td align="center">Numero Réservation</td>
                                <td align="center">Numéro Chambre</td>
                                <td align="center">prix HTVA</td>
                                <td align="center">Date réservation</td>
                                <td align="center">Heure réservation</td>
                            </tr>
                            <%for(BeanReservation beanReservation: beanCaddy.getListeReservation()){%>
                            <tr>
                                <td align="center"><%=beanReservation.getIdReservation()%></td>
                                <td align="center"><%=beanReservation.getNumeroChambre()%></td>
                                <td align="center"><%=beanReservation.getPrixHtva()%></td>
                                <td align="center"><%=beanReservation.getDate()%></td>
                                <td align="center"><%=beanReservation.getHeure()%></td>
                            <td>
                                <form action="Servlet_Controle" method="post">
                                    <input type="hidden" name="idReservation" value="<%=beanReservation.getIdReservation()%>"/>
                                    <input type="hidden" name="action" value="retraitCaddy"/>
                                    <input type="submit" value="Retirer"/>
                                </form>
                            </td>
                            </tr>
                            <%}%>
                        </table>
                        <form action="Servlet_Controle" method="post">
                            <input type="hidden" name="action" value="payement"/>
                            <input type="submit" value="Payer"/>
                        </form>
                        <% } %>
                    </div>
                </div>
            </div>
            <div class="clean"></div>
            <div class="foot">
                <span>
                    Codé par Gabriel Knuts 2302
                </span>
            </div>
        </div>
    </body>
</html>
