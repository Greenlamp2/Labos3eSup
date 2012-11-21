<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : menu.xsl
    Created on : 4 novembre 2012, 20:43
    Author     : Greenlamp
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules
         syntax recommendation http://www.w3.org/TR/xslt
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>menu.xsl</title>
            </head>
            <body>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="menu">
        Nom du menu: <xsl:value-of select="nom"/><br/>
        Vedette: <xsl:value-of select="vedette"/><br/>
        Prix: <xsl:value-of select="prix"/><br/>
        Supplement: <xsl:value-of select="supplement"/>
        <br/>
        <table border="1">
            <tr>
                <td align="center" colspan="3">Entrées</td>
            </tr>
            <tr>
                <td align="center"><xsl:text>id</xsl:text></td>
                <td align="center"><xsl:text>nom</xsl:text></td>
                <td align="center"><xsl:text>ingrédients</xsl:text></td>
            </tr>
            <xsl:for-each select="entree">
            <tr>
                <td align="center"><xsl:value-of select="@id"/></td>
                <td align="center"><xsl:value-of select="nom"/></td>
                <td>
                <xsl:for-each select="ingredient">
                        <xsl:value-of select="quantité"/>
                        <xsl:text>x </xsl:text>
                        <xsl:value-of select="nom"/>
                        <xsl:text>  </xsl:text>
                        <xsl:value-of select="alergene"/>
                        <br/>
                </xsl:for-each>
                </td>
            </tr>
            </xsl:for-each>
        </table>
        
        <br/>
        <table border="1">
            <tr>
                <td align="center" colspan="3">Plats</td>
            </tr>
            <tr>
                <td align="center"><xsl:text>id</xsl:text></td>
                <td align="center"><xsl:text>nom</xsl:text></td>
                <td align="center"><xsl:text>ingrédients</xsl:text></td>
            </tr>
            <xsl:for-each select="plat">
            <tr>
                <td align="center"><xsl:value-of select="@id"/></td>
                <td align="center"><xsl:value-of select="nom"/></td>
                <td>
                <xsl:for-each select="ingredient">
                        <xsl:value-of select="quantité"/>
                        <xsl:text>x </xsl:text>
                        <xsl:value-of select="nom"/>
                        <xsl:text>  </xsl:text>
                        <xsl:value-of select="alergene"/>
                        <br/>
                </xsl:for-each>
                </td>
            </tr>
            </xsl:for-each>
        </table>
        
        <br/>
        <table border="1">
            <tr>
                <td align="center" colspan="3">Desserts</td>
            </tr>
            <tr>
                <td align="center"><xsl:text>id</xsl:text></td>
                <td align="center"><xsl:text>nom</xsl:text></td>
                <td align="center"><xsl:text>ingrédients</xsl:text></td>
            </tr>
            <xsl:for-each select="dessert">
            <tr>
                <td align="center"><xsl:value-of select="@id"/></td>
                <td align="center"><xsl:value-of select="nom"/></td>
                <td>
                <xsl:for-each select="ingredient">
                        <xsl:value-of select="quantité"/>
                        <xsl:text>x </xsl:text>
                        <xsl:value-of select="nom"/>
                        <xsl:text>  </xsl:text>
                        <xsl:value-of select="alergene"/>
                        <br/>
                </xsl:for-each>
                </td>
            </tr>
            </xsl:for-each>
        </table>
    </xsl:template>

</xsl:stylesheet>
