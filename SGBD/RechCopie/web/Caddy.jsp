<%--
    Document   : Caddy
    Created on : 31-oct.-2012, 18:39:31
    Author     : Greenlamp
--%>

<%@page import="Beans.BeanMovieCaddy"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page errorPage="error.jsp"%>
<jsp:useBean id="beanUser" scope="session" class="Beans.BeanUser"/>
<jsp:useBean id="beanCaddy" scope="session" class="Beans.BeanCaddy"/>
<% if(beanUser == null || beanUser.getLogin() == null){ %>
    <% RequestDispatcher rd; %>
    <% ServletContext sc = getServletContext(); %>
    <% rd = sc.getRequestDispatcher("/ServletControle?action=error&message=\"Impossible d'accéder à cette page sans être authentifié au préalable.\"");%>
    <% rd.forward(request, response); %>
<% } %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Caddy</title>
    </head>
    <body>
        <h1 align="center">Caddy</h1>
        <p align="center">
            <a href="?action=Accueil">Accueil</a>
            <a href="?action=Recherche">Recherche</a>
            <a href="?action=caddy">Caddy</a>
            <a href="?action=deconnecter">Deconnecter</a>
        </p>

        <p align="center">
            Nombre de film dans le caddy: <%=beanCaddy.getListMovie().size()%>
        </p>

        <% if(beanCaddy.getListMovie().size() > 0){ %>

            <% int numResultat = beanCaddy.getListMovie().size(); %>
            <% int nbResultatByPage = 2; %>
            <% int reste = numResultat % nbResultatByPage; %>
            <% int nbResultat = numResultat - reste; %>
            <% int nbPage = nbResultat / nbResultatByPage; %>
            <% if(reste>0){ nbPage += 1;} %>
            <% System.out.println("nbPage: " + nbPage);%>

            <p align="center">
            <% for(int i=0; i<nbPage; i++){ %>
                <% String lien = "ServletControle?action=getPageCaddy&numPage=" + i; %>
                <a href="<%=lien%>"><%=i+1%></a>
            <% } %>
            </p>

            <% int numPage = Integer.parseInt(request.getParameter("numPage")); %>

            <form action="ServletControle" method="post">
            <input type="hidden" name="action" value="actionCaddy"/>
            <% for(BeanMovieCaddy beanMovie : beanCaddy.getListMovie(numPage, nbResultatByPage)){ %>
            <table border align="center" width="300">
                <tr>
                    <td align="center" colspan="1">
                        <input type="checkbox" name="checkbox<%=beanMovie.getId()%>"/>
                    </td>
                </tr>
                <tr>
                    <td align="center"><span style="font-weight:bold">Id:</span></td>
                    <td align="center"><%=beanMovie.getId()%></td>
                    <td align="center"><span style="font-weight:bold">Titre:</span></td>
                    <td align="center"><%=beanMovie.getNom()%></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><span style="font-weight:bold">Affiche</span></td>
                    <td colspan="2" align="center"><span style="font-weight:bold">Palmarès</span></td>
                </tr>
                <tr>
                    <% String urlServlet = "ServletControle?action=getImageCaddy&idMovie=" + beanMovie.getId();%>
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
                    <td colspan="2" align="center">Quantité</td>
                    <td colspan="2" align="center"><%=beanMovie.getQuantite()%></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">Type de support</td>
                    <% if(beanMovie.getPhysique() == 1){ %>
                        <td colspan="1" align="center">Physique</td>
                    <% }else{ %>
                        <td colspan="1" align="center">Digital</td>
                    <% } %>
                    <td colspan="1" align="center"><input type="submit" name="bouton<%=beanMovie.getId()%>" value="switcher" id="switcher"/></td>
                </tr>
                <tr>
                    <td colspan="1" align="center" width="20">
                        <input type="text" name="quantiteAdd" value="1" size="4"/>
                    </td>
                    <td colspan="1" align="center">
                        <input type="submit" name="bouton<%=beanMovie.getId()%>" value="Ajouter" id="Ajouter"/>
                    </td>
                    <td colspan="1" align="center" width="20">
                        <input type="text" name="quantiteRemove" value="1" size="4"/>
                    </td>
                    <td colspan="1" align="center">
                        <input type="submit" name="bouton<%=beanMovie.getId()%>" value="Retirer" id="Retirer"/>
                    </td>
                </tr>
            <br/>
            <% } %>
            </table>
            <br/>
            <table align="center">
            <tr>
                <td colspan="4" align="center">
                    <input type="submit" name="bouton0" value="Supprimer" id="Supprimer"/>
                    <input type="submit" name="bouton0" value="Vider" id="Supprimer"/>
                </td>
            </tr>
            </table>

            </form>
        <form action="ServletControle" method="post">
            <input type="hidden" name="action" value="validerCaddy"/>
            <p align="center">
            <input type="submit" value="Valider Caddy"/>
            </p>
        </form>
        <% }else{%>
        <br/>
        <table align="center">
            <tr>
                <td>Le panier est vide.</td>
            </tr>
        </table>
        <% } %>
    </body>
</html>
