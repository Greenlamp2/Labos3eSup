<%--
    Document   : motelVillage
    Created on : 20-janv.-2013, 13:10:12
    Author     : Greenlamp
--%>

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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" media="all" type="text/css" href="css/style.css" />
        <title>motelVillage <%=beanUser.getNom()%></title>
    </head>
    <body>
        <div class="site">
            <div class="header">
                <span>Site de réservation de chambres DANS motel et villages</span>
            </div>
            <div class="clean"></div>
            <div class="corp">
                <div class="menu_v">
                    <div class="menu_v_top">Menu</div>
                    <div class="menu_v_ctn">
                        <ul>
                            <li><a href="?action=accueil">Accueil</a></li>
                            <li><a href="?action=broom">Booking Room</a></li>
                            <li><a href="?action=proom">Pay Room</a></li>
                            <li><a href="?action=croom">Cancel Room</a></li>
                        </ul>
                    </div>
                </div>
                <div class="corp_ctn">
                    <h1>Accueil</h1>
                    <div class="paragraphe">
                        <% String msgConfirmation = (String)request.getSession(true).getAttribute("msgConfirmation"); %>
                        <% if(msgConfirmation != null){ %>
                            Réponse: <%=msgConfirmation%> <br/><br/>
                            <% session.removeAttribute("msgConfirmation"); %>
                        <% } %>

                        Bonjour, Bienvenue sur la page d'accueil du site de réservation de chambres DANS motel et villages.<br/>
                        Veuillez sélectionnez dans le menu de gauche votre destination.<br/>
                        <br/>


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
