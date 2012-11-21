/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xml;

import Classes.Ingredient;
import Classes.Menu;
import Classes.Recette;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;


public class MyDom {

    public static byte[] serialize(Menu menu) throws Exception{
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        DOMImplementation domImpl = registry.getDOMImplementation("XML 3.0 LS 3.0");
        DOMImplementationLS domImplLS = (DOMImplementationLS)domImpl;
        if (domImpl == null) {
            System.out.println("No DOMImplementation found !");
            System.exit(0);
        }

        DocumentType type = domImpl.createDocumentType("menus", null, "C:\\Users\\Greenlamp\\Documents\\GitHub\\Labos3eSup\\E-Commerce\\Applic_Head_Food\\src\\xml\\exemple.dtd");
        Document doc = domImpl.createDocument(null, "menus", type);

        Element menuRoot = doc.createElement("menu");
        doc.getDocumentElement().appendChild(menuRoot);

        Element nom = doc.createElement("nom");
        menuRoot.appendChild(nom);
        nom.appendChild(doc.createTextNode(menu.getNomMenu()));

        Element vedette = doc.createElement("vedette");
        menuRoot.appendChild(vedette);
        vedette.appendChild(doc.createTextNode(menu.getVedetteJour()));

        int cpt = 1;
        for(Recette entree : menu.getListeEntrees()){
            Element ElmEntree = doc.createElement("entree");
            menuRoot.appendChild(ElmEntree);
            ElmEntree.setAttribute("id", String.valueOf(cpt));

            Element nomEntree = doc.createElement("nom");
            ElmEntree.appendChild(nomEntree);
            nomEntree.appendChild(doc.createTextNode(entree.getNomRecette()));

            Element chaud = doc.createElement("chaud");
            if(entree.isChaud()){
                ElmEntree.appendChild(chaud);
            }

            for(Ingredient ingredient : entree.getListeIngredients()){
                Element ElmIngredient = doc.createElement("ingredient");
                ElmEntree.appendChild(ElmIngredient);

                Element nomIngredient = doc.createElement("nom");
                ElmIngredient.appendChild(nomIngredient);
                nomIngredient.appendChild(doc.createTextNode(ingredient.getNomIngredient()));

                Element qteIngredient = doc.createElement("quantité");
                ElmIngredient.appendChild(qteIngredient);
                qteIngredient.appendChild(doc.createTextNode(String.valueOf(ingredient.getQuantite())));

                Element alergene = doc.createElement("alergene");
                ElmIngredient.appendChild(alergene);
            }
            cpt++;
        }

        cpt = 1;
        for(Recette plat : menu.getListePlats()){
            Element Elmplat = doc.createElement("plat");
            menuRoot.appendChild(Elmplat);
            Elmplat.setAttribute("id", String.valueOf(cpt));

            Element nomPlat = doc.createElement("nom");
            Elmplat.appendChild(nomPlat);
            nomPlat.appendChild(doc.createTextNode(plat.getNomRecette()));

            Element chaud = doc.createElement("chaud");
            if(plat.isChaud()){
                Elmplat.appendChild(chaud);
            }

            for(Ingredient ingredient : plat.getListeIngredients()){
                Element ElmIngredient = doc.createElement("ingredient");
                Elmplat.appendChild(ElmIngredient);

                Element nomIngredient = doc.createElement("nom");
                ElmIngredient.appendChild(nomIngredient);
                nomIngredient.appendChild(doc.createTextNode(ingredient.getNomIngredient()));

                Element qteIngredient = doc.createElement("quantité");
                ElmIngredient.appendChild(qteIngredient);
                qteIngredient.appendChild(doc.createTextNode(String.valueOf(ingredient.getQuantite())));

                Element alergene = doc.createElement("alergene");
                ElmIngredient.appendChild(alergene);
            }
            cpt++;
        }

        cpt = 1;
        for(Recette dessert : menu.getListeDesserts()){
            Element ElmDessert = doc.createElement("dessert");
            menuRoot.appendChild(ElmDessert);
            ElmDessert.setAttribute("id", String.valueOf(cpt));

            Element nomDessert = doc.createElement("nom");
            ElmDessert.appendChild(nomDessert);
            nomDessert.appendChild(doc.createTextNode(dessert.getNomRecette()));

            Element chaud = doc.createElement("chaud");
            if(dessert.isChaud()){
                ElmDessert.appendChild(chaud);
            }

            for(Ingredient ingredient : dessert.getListeIngredients()){
                Element ElmIngredient = doc.createElement("ingredient");
                ElmDessert.appendChild(ElmIngredient);

                Element nomIngredient = doc.createElement("nom");
                ElmIngredient.appendChild(nomIngredient);
                nomIngredient.appendChild(doc.createTextNode(ingredient.getNomIngredient()));

                Element qteIngredient = doc.createElement("quantité");
                ElmIngredient.appendChild(qteIngredient);
                qteIngredient.appendChild(doc.createTextNode(String.valueOf(ingredient.getQuantite())));

                Element alergene = doc.createElement("alergene");
                ElmIngredient.appendChild(alergene);
            }
            cpt++;
        }

        Element prix = doc.createElement("prix");
        menuRoot.appendChild(prix);
        prix.appendChild(doc.createTextNode("0"));

        Element supplement = doc.createElement("supplement");
        menuRoot.appendChild(supplement);
        supplement.appendChild(doc.createTextNode("0"));


        LSSerializer serializer = domImplLS.createLSSerializer();
        DOMConfiguration config = serializer.getDomConfig();
        config.setParameter("format-pretty-print", true);

        LSOutput outputFile = domImplLS.createLSOutput();
        outputFile.setEncoding("UTF-8");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outputFile.setByteStream(baos);
        serializer.write(doc, outputFile);

        outputFile.setByteStream(new FileOutputStream("menu.xml"));
        serializer.write(doc, outputFile);
        byte[] xml = baos.toByteArray();

        return xml;
    }
}
