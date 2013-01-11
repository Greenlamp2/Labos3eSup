/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BalisePerso;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Greenlamp
 */
public class BaliseDateHeure extends SimpleTagSupport {
    private String langue;

    /**
     * Called by the container to invoke this tag. The implementation of this
     * method is provided by the tag library developer, and handles all tag
     * processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();

        try {
            // TODO: insert code to write html before writing the body content.
            // e.g.:
            //
            // out.println("<strong>" + attribute_1 + "</strong>");
            // out.println("    <blockquote>");

            JspFragment f = getJspBody();
            if (f != null) {
                f.invoke(out);
            }

            // TODO: insert code to write html after writing the body content.
            // e.g.:
            //
            // out.println("    </blockquote>");

            String date = null;
            Date now = new Date();

            if (langue.equals("English"))
            {
                date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT,Locale.UK).format(now);
            }
            else if (langue.equals("Italiano"))
            {
                date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT,Locale.ITALY).format(now);
            }
            else if (langue.equals("Français"))
            {
                date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT,Locale.FRANCE).format(now);
            }
            else if (langue.equals("Deutsch"))
            {
                date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT,Locale.GERMANY).format(now);
            }

            out.println(date);

        } catch (java.io.IOException ex) {
            throw new JspException("Error in BaliseDateHeure tag", ex);
        }
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }
}
