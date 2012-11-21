/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class MyErrorHandler implements ErrorHandler{

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        throw exception;
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        throw exception;
    }

}
