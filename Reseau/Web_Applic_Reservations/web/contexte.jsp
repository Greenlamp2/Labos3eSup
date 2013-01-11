<%--
    Document   : contexte
    Created on : 17-déc.-2012, 14:09:51
    Author     : Greenlamp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page errorPage="error.jsp"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" media="all" type="text/css" href="css/style.css" />
        <title>Choix de la langue</title>
    </head>
    <body>
        <div class="site">
            <div class="header">
                <span>Choix de la langue</span>
            </div>
            <div class="clean"></div>
            <div class="corp">
                <div class="menu_v">
                </div>
                <div class="corp_ctn">
                    <h1>Menu</h1>
                    <div class="paragraphe">
                        <APPLET
                            CODE = "Applet/MenuLangue.class"
                            archive="AppletLangue.jar"
                            name="AppletHolidaysLogin" alt="Pas de Java installé"
                            WIDTH ="380" HEIGHT ="250">
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
