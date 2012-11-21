<%--
    Document   : index
    Created on : 01-oct.-2012, 14:22:35
    Author     : Greenlamp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page errorPage="error.jsp"%>
<html>
    <head>
        <title>Connexion au site</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" media="all" type="text/css" href="css/style.css" />
    </head>
    <body>
        <div class="site">
            <div class="header">
                <span>Site de réservation de chambre</span>
            </div>
            <div class="clean"></div>
            <div class="corp">
                <div class="menu_v">
                </div>
                <div class="corp_ctn">
                    <h1>Connexion</h1>
                    <div class="paragraphe">
                        <APPLET
                            CODE = "applets/AppletHolidaysLogin.class"
                            archive="AppletConnexionServlet.jar"
                            name="AppletHolidaysLogin" alt="Pas de Java installé"
                            WIDTH ="380" HEIGHT ="350">
                        </APPLET>
                                <br/>
                        <APPLET
                            CODE = "applets/AppletHolidaysPassword.class"
                            archive="AppletConnexionServlet.jar"
                            name="AppletHolidaysPassword" alt="Pas de Java installé"
                            WIDTH ="380" HEIGHT ="120">
                        </APPLET>
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
