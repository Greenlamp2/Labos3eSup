<%--
    Document   : index
    Created on : 26-oct.-2012, 12:22:21
    Author     : Greenlamp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Accueil</title>
    </head>
    <body>
        <h1 align="center">Bienvenue sur la page d'authentification</h1>
        <form action="ServletLogin" method="post">
            <table align="center">
                <tr><td>Nom d'utilisateur: </td><td><input type="text" name="login" value="admin"/></td><tr/>
                <tr><td>Mot de passe: </td><td><input type="text" name="pass" value="admin"/></td><tr/>
                <input type="hidden" name="action" value="login"/>
                <tr><td></td><td><input type="submit" value="valider"/></td><tr/>
            </table>
        </form>
    </body>
</html>
