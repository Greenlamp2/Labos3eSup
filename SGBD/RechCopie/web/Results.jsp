<%--
    Document   : Results
    Created on : 30-oct.-2012, 0:59:41
    Author     : Greenlamp
--%>


<%@page import="Beans.BeanMovie"%>
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
<jsp:useBean id="beanResultat" scope="session" class="Beans.BeanResultat"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultats</title>
    </head>
    <body>
        <h1 align="center">Resultat de la dernière recherche</h1>
        <p align="center">
            <a href="?action=Accueil">Accueil</a>
            <a href="?action=Recherche">Recherche</a>
            <a href="?action=caddy">Caddy</a>
            <a href="?action=deconnecter">Deconnecter</a>
        </p>
        <p align="center">
            <%=beanResultat.getNumResultat()%> résultat(s)<br/>

            <% if(beanResultat.getNumResultat() > 0){ %>
                Numéro de page: <br/>

                <% int numResultat = beanResultat.getNumResultat(); %>

                <% int nbResultatByPage = beanResultat.getNbResultByPage(); %>
                <% int reste = numResultat % nbResultatByPage; %>
                <% int nbResultat = numResultat - reste; %>
                <% int nbPage = nbResultat / nbResultatByPage; %>
                <% if(reste>0){ nbPage += 1;} %>
                <% System.out.println("nbPage: " + nbPage);%>

                <% for(int i=0; i<nbPage; i++){ %>
                    <% String lien = "ServletControle?action=getPage&numPage=" + i; %>
                    <a href="<%=lien%>"><%=i+1%></a>
                <% } %>
            <% } %>
        </p>

        <% if(beanResultat.getNumResultat() > 0){ %>
            <%for(BeanMovie beanMovie : beanResultat.getListMovie()){%>
            <table border align="center" width="300">
                <tr>
                    <td align="center"><span style="font-weight:bold">Id:</span></td>
                    <td align="center"><%=beanMovie.getId()%></td>
                    <td align="center"><span style="font-weight:bold">Titre:</span></td>
                    <% String urlServlet = "ServletControle?action=details&idMovie=" + beanMovie.getId();%>
                    <td align="center"><a href="<%=urlServlet%>"><%=beanMovie.getNom()%></a></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><span style="font-weight:bold">Affiche</span></td>
                    <td colspan="2" align="center"><span style="font-weight:bold">Palmarès</span></td>
                </tr>
                <tr>
                    <% urlServlet = "ServletControle?action=getImage&idMovie=" + beanMovie.getId();%>
                    <td colspan="2" align="center"><img src="<%=urlServlet%>"/></td>
                    <td colspan="2">
                        <table border align="center">
                            <tr>
                                <td><span style="font-weight:bold">Popularité:</span></td>
                                <td align="center"><%=beanMovie.getPopularite()%></td>
                            </tr>
                            <tr>
                                <td><span style="font-weight:bold">Fréquence:</span></td>
                                <td align="center"><%=beanMovie.getFrequenceProg()%></td>
                            </tr>
                            <tr>
                                <td><span style="font-weight:bold">Pérénité:</span></td>
                                <td align="center"><%=beanMovie.getPerenite()%></td>
                            </tr>
                            <tr>
                                <td><span style="font-weight:bold">Copie disponible:</span></td>
                                <td align="center"><%=beanMovie.getNbCopieDispo()%></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <form action="ServletControle" method="post">
                        <td colspan="2" align="center"><input type="submit" value="commander"/>
                        <input type="hidden" name="action" value="commander"/>
                        <input type="hidden" name="idMovie" value="<%=beanMovie.getId()%>"/>
                        <td colspan="2" align="center"><input type="text" name="quantite" value="1"/></td>
                    </form>
                </tr>
            </table>
            <br/>
            <%}%>
            <p align="center"><a href="ServletControle?action=trier&type=popularite">Trier par popularité</a><br/>
            <a href="ServletControle?action=trier&type=frequence">Trier par fréquence</a><br/>
            <a href="ServletControle?action=trier&type=perenite">Trier par pérénité</a></p>
        <% } %>



    </body>
</html>
