/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xml;

import Classes.Menu;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MyHandler extends DefaultHandler{
    StringBuilder acc;
    Menu menu;
    boolean inMenu = false;
    boolean inIngredient = false;
    boolean inEntree = false;
    boolean inPlat = false;
    boolean inDessert = false;
    String nom = null;
    String nomIngredient = null;

    public MyHandler(Menu menu){
        acc = new StringBuilder();
        this.menu = menu;
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        if(qName.equals("menu")){
            inMenu = true;
        }else if(qName.equals("entree")){
            inEntree = true;
        }else if(qName.equals("plat")){
            inPlat = true;
        }else if(qName.equals("dessert")){
            inDessert = true;
        }else if(qName.equals("ingredient")){
            inIngredient = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if(qName.equals("nom") && inMenu == true){
            menu.setNomMenu(acc.toString());
            inMenu = false;
        }else if(qName.equals("vedette")){
            menu.setVedetteJour(acc.toString());
        }else if(qName.equals("nom") && inEntree && !inIngredient){
            nom = acc.toString();
            menu.addEntree(nom, false);
        }else if(qName.equals("nom") && inPlat && !inIngredient){
            nom = acc.toString();
            menu.addPlat(nom, false);
        }else if(qName.equals("nom") && inDessert && !inIngredient){
            nom = acc.toString();
            menu.addDessert(nom, false);
        }else if(qName.equals("entree")){
            inEntree = false;
            nom = null;
        }else if(qName.equals("plat")){
            inPlat = false;
            nom = null;
        }else if(qName.equals("dessert")){
            inDessert = false;
            nom = null;
        }else if(qName.equals("ingredient")){
            inIngredient = false;
        }else if(qName.equals("chaud") && inEntree){
            menu.chaudEntree(nom, true);
        }else if(qName.equals("chaud") && inPlat){
            menu.chaudPlat(nom, true);
        }else if(qName.equals("chaud") && inDessert){
            menu.chaudDessert(nom, true);
        }else if(qName.equals("nom") && inIngredient){
            nomIngredient = acc.toString();
        }else if(qName.equals("quantité") && inIngredient && inEntree){
            menu.addIngredientEntree(nom, nomIngredient, Integer.parseInt(acc.toString()));
        }else if(qName.equals("quantité") && inIngredient && inPlat){
            menu.addIngredientPlat(nom, nomIngredient, Integer.parseInt(acc.toString()));
        }else if(qName.equals("quantité") && inIngredient && inDessert){
            menu.addIngredientDessert(nom, nomIngredient, Integer.parseInt(acc.toString()));
        }else if(qName.equals("prix")){
            menu.setPrix(Float.parseFloat(acc.toString()));
        }else if(qName.equals("supplement")){
            menu.setSupplement(Float.parseFloat(acc.toString()));
        }
        acc.setLength(0);
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        acc.append(ch, start, length);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException {
    }
}
