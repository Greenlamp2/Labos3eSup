<%--
    Document   : index
    Created on : 27-oct.-2012, 11:18:27
    Author     : Greenlamp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Connexion</title>
    </head>
    <body>
        <h1 align="center">Connexion</h1>
        <form action="ServletLogin" method="post">
        <table align="center">
            <tr>
                <td>Login:</td>
                <td><input type="text" name="login" value="admin"/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="text" name="pass" value="admin"/></td>
            </tr>
            <tr>
                <input type="hidden" name="action" value="login"/>
                <td colspan="2"><input type="submit" value="valider"</td>
            </tr>
        </table>
        </form>
        <a href="ServletControle?action=Accueil">Accueil</a>
    </body>
</html>
