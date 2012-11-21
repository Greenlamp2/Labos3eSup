<%--
    Document   : Magasin
    Created on : 17-oct.-2012, 14:21:49
    Author     : Greenlamp
--%>

<%@page import="Bean.BeanChambre"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page errorPage="error.jsp"%>
<jsp:useBean id="beanUser" scope="session" class="Bean.BeanUser"/>
<jsp:useBean id="beanMagasin" scope="session" class="Bean.BeanMagasin"/>
<% if(beanUser == null || beanUser.getNom() == null){ %>
<% RequestDispatcher rd; %>
<% ServletContext sc = getServletContext(); %>
<% rd = sc.getRequestDispatcher("/Servlet_Controle?action=error&message=\"Impossible d'accéder à cette page sans être authentifié au préalable.\"");%>
<% rd.forward(request, response); %>
<% } %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" media="all" type="text/css" href="css/style.css" />
        <title>Magasin <%=beanUser.getNom()%></title>
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
                    <h1>Magasin</h1>
                    <div class="paragraphe">
                        <% if(beanMagasin.getListeChambre().size() == 0){ %>
                            Il n'y a plus de chambre disponible.<br/>
                            <br/><br/>
                        <% }else{ %>
                        <table align="center" border="1">
                            <tr>
                                <td align="center">numero chambre</td>
                                <td align="center">nombre d'occupant</td>
                                <td align="center">prix HTVA</td>
                                <td align="center">douche</td>
                                <td align="center">baignoire</td>
                                <td align="center">cuvette</td>
                            </tr>
                            <% for(BeanChambre beanChambre : beanMagasin.getListeChambre()){%>
                                <tr>
                                    <td align="center"><%=beanChambre.getNumeroChambre()%></td>
                                    <td align="center"><%=beanChambre.getNbOccupant()%></td>
                                    <td align="center"><%=beanChambre.getPrixHtva()%></td>
                                    <td align="center"><%=beanChambre.isDouche()%></td>
                                    <td align="center"><%=beanChambre.isBaignoire()%></td>
                                    <td align="center"><%=beanChambre.isCuvette()%></td>
                                    <td>
                                        <form action="Servlet_Controle" method="post">
                                            <input type="hidden" name="numeroChambre" value="<%=beanChambre.getNumeroChambre()%>"/>
                                            <input type="hidden" name="action" value="ajoutCaddy">
                                            <input type="submit" value="Réserver">
                                        </form>
                                    </td>
                                </tr>
                            <%}%>
                        </table>
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
