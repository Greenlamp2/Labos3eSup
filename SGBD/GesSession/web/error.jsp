<%--
    Document   : error
    Created on : 26-oct.-2012, 13:38:32
    Author     : Greenlamp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page isErrorPage="true" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Erreur</title>
    </head>
    <body>
        <h1>Erreur</h1>
        <% String message = request.getParameter("message");%>
        <% if(message == null){%>
            Vous devez être connecté pour accéder à cette page.
        <% }else{ %>
        <%=message%>
        <% } %>
        <% if(exception != null){ out.print(exception); } %>
    </body>
</html>
