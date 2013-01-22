/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mobile_applic_reservations;

import Constants.Infos;
import Net.Base64;
import Net.NetworkClientMobile;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.microedition.lcdui.*;

/**
 * @author Greenlamp
 */
public class HelloMIDlet extends javax.microedition.midlet.MIDlet implements CommandListener {

    private boolean midletPaused = false;
    String host;
    int port;
    boolean connected = false;
    NetworkClientMobile socket = null;
    String login = null;
    //RSAPrivateCrtKeyParameters privateKey;
    //RSAKeyParameters publicKey;
    SecretKeySpec cleSession;


//<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Command exitCommand;
    private Command GokLogin;
    private Command itemCommand;
    private Command GokMenu;
    private Command exitCommand2;
    private Command exitCommand1;
    private Command GokMenu1;
    private Command exitCommand3;
    private Command backCommand;
    private Command GokBroom;
    private Command GbackMenu;
    private Command backCommand1;
    private Command GokCroom;
    private Command itemCommand2;
    private Command itemCommand1;
    private Command GokProom;
    private Command itemCommand3;
    private Form Connexion;
    private TextField Gpassword;
    private TextField Glogin;
    private Form Broom;
    private TextField GcategorieBroom;
    private TextField GnbNuitBroom;
    private TextField GdateBroom;
    private TextField GtypeBroom;
    private Form Croom;
    private TextField textField8;
    private TextField textField7;
    private Form Proom;
    private TextField textField5;
    private TextField textField4;
    private TextField textField6;
    private List GchoixMenu;
//</editor-fold>//GEN-END:|fields|0|

    /**
     * The HelloMIDlet constructor.
     */
    public HelloMIDlet() {
        this.host = Infos.HOST;
        this.port = Infos.PORT;
        this.socket = null;
    }

//<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
//</editor-fold>//GEN-END:|methods|0|
//<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initializes the application. It is called only once when the MIDlet is
     * started. The method is called before the
     * <code>startMIDlet</code> method.
     */
    private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
    }//GEN-BEGIN:|0-initialize|2|
//</editor-fold>//GEN-END:|0-initialize|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
        switchDisplayable(null, getConnexion());//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here
    }//GEN-BEGIN:|3-startMIDlet|2|
//</editor-fold>//GEN-END:|3-startMIDlet|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
    }//GEN-BEGIN:|4-resumeMIDlet|2|
//</editor-fold>//GEN-END:|4-resumeMIDlet|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The
     * <code>display</code> instance is taken from
     * <code>getDisplay</code> method. This method is used by all actions in the
     * design for switching displayable.
     *
     * @param alert the Alert which is temporarily set to the display;
     * if <code>null</code>, then <code>nextDisplayable</code> is set
     * immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
        Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
    }//GEN-BEGIN:|5-switchDisplayable|2|
//</editor-fold>//GEN-END:|5-switchDisplayable|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a
     * particular displayable.
     *
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
        if (displayable == Broom) {//GEN-BEGIN:|7-commandAction|1|61-preAction
            if (command == GokBroom) {//GEN-END:|7-commandAction|1|61-preAction
                // write pre-action user code here
                actionOkBroom();
//GEN-LINE:|7-commandAction|2|61-postAction
                // write post-action user code here
            } else if (command == itemCommand1) {//GEN-LINE:|7-commandAction|3|74-preAction
                // write pre-action user code here
                switchDisplayable(null, getGchoixMenu());//GEN-LINE:|7-commandAction|4|74-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|5|25-preAction
        } else if (displayable == Connexion) {
            if (command == GokLogin) {//GEN-END:|7-commandAction|5|25-preAction
                // write pre-action user code here
                actionOkLogin();
//GEN-LINE:|7-commandAction|6|25-postAction
                // write post-action user code here
            } else if (command == exitCommand) {//GEN-LINE:|7-commandAction|7|19-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|8|19-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|9|77-preAction
        } else if (displayable == Croom) {
            if (command == GokCroom) {//GEN-END:|7-commandAction|9|77-preAction
                // write pre-action user code here
                actionOkCroom();
//GEN-LINE:|7-commandAction|10|77-postAction
                // write post-action user code here
            } else if (command == itemCommand2) {//GEN-LINE:|7-commandAction|11|79-preAction
                // write pre-action user code here
                switchDisplayable(null, getGchoixMenu());//GEN-LINE:|7-commandAction|12|79-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|13|46-preAction
        } else if (displayable == GchoixMenu) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|13|46-preAction
                // write pre-action user code here
                GchoixMenuAction();//GEN-LINE:|7-commandAction|14|46-postAction
                // write post-action user code here
            } else if (command == exitCommand3) {//GEN-LINE:|7-commandAction|15|54-preAction
                // write pre-action user code here
//GEN-LINE:|7-commandAction|16|54-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|17|81-preAction
        } else if (displayable == Proom) {
            if (command == GokProom) {//GEN-END:|7-commandAction|17|81-preAction
                // write pre-action user code here
                actionOkProom();
//GEN-LINE:|7-commandAction|18|81-postAction
                // write post-action user code here
            } else if (command == itemCommand3) {//GEN-LINE:|7-commandAction|19|83-preAction
                // write pre-action user code here
                switchDisplayable(null, getGchoixMenu());//GEN-LINE:|7-commandAction|20|83-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|21|7-postCommandAction
        }//GEN-END:|7-commandAction|21|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|22|
//</editor-fold>//GEN-END:|7-commandAction|22|





//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|18-getter|0|18-preInit
    /**
     * Returns an initialized instance of exitCommand component.
     *
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {//GEN-END:|18-getter|0|18-preInit
            // write pre-init user code here
            exitCommand = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|18-getter|1|18-postInit
            // write post-init user code here
        }//GEN-BEGIN:|18-getter|2|
        return exitCommand;
    }
//</editor-fold>//GEN-END:|18-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: Connexion ">//GEN-BEGIN:|14-getter|0|14-preInit
    /**
     * Returns an initialized instance of Connexion component.
     *
     * @return the initialized component instance
     */
    public Form getConnexion() {
        if (Connexion == null) {//GEN-END:|14-getter|0|14-preInit
            // write pre-init user code here
            Connexion = new Form("Connexion", new Item[]{getGlogin(), getGpassword()});//GEN-BEGIN:|14-getter|1|14-postInit
            Connexion.addCommand(getExitCommand());
            Connexion.addCommand(getGokLogin());
            Connexion.setCommandListener(this);//GEN-END:|14-getter|1|14-postInit
            // write post-init user code here
        }//GEN-BEGIN:|14-getter|2|
        return Connexion;
    }
//</editor-fold>//GEN-END:|14-getter|2|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: GokLogin ">//GEN-BEGIN:|24-getter|0|24-preInit
    /**
     * Returns an initialized instance of GokLogin component.
     *
     * @return the initialized component instance
     */
    public Command getGokLogin() {
        if (GokLogin == null) {//GEN-END:|24-getter|0|24-preInit
            // write pre-init user code here
            GokLogin = new Command("Ok", Command.OK, 0);//GEN-LINE:|24-getter|1|24-postInit
            // write post-init user code here
        }//GEN-BEGIN:|24-getter|2|
        return GokLogin;
    }
//</editor-fold>//GEN-END:|24-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: Glogin ">//GEN-BEGIN:|22-getter|0|22-preInit
    /**
     * Returns an initialized instance of Glogin component.
     *
     * @return the initialized component instance
     */
    public TextField getGlogin() {
        if (Glogin == null) {//GEN-END:|22-getter|0|22-preInit
            // write pre-init user code here
            Glogin = new TextField("Login", "knuts", 32, TextField.ANY);//GEN-LINE:|22-getter|1|22-postInit
            // write post-init user code here
        }//GEN-BEGIN:|22-getter|2|
        return Glogin;
    }
//</editor-fold>//GEN-END:|22-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: Gpassword ">//GEN-BEGIN:|23-getter|0|23-preInit
    /**
     * Returns an initialized instance of Gpassword component.
     *
     * @return the initialized component instance
     */
    public TextField getGpassword() {
        if (Gpassword == null) {//GEN-END:|23-getter|0|23-preInit
            // write pre-init user code here
            Gpassword = new TextField("Password", "a", 32, TextField.ANY);//GEN-LINE:|23-getter|1|23-postInit
            // write post-init user code here
        }//GEN-BEGIN:|23-getter|2|
        return Gpassword;
    }
//</editor-fold>//GEN-END:|23-getter|2|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: Broom ">//GEN-BEGIN:|27-getter|0|27-preInit
    /**
     * Returns an initialized instance of Broom component.
     *
     * @return the initialized component instance
     */
    public Form getBroom() {
        if (Broom == null) {//GEN-END:|27-getter|0|27-preInit
            // write pre-init user code here
            Broom = new Form("Broom", new Item[]{getGcategorieBroom(), getGtypeBroom(), getGdateBroom(), getGnbNuitBroom()});//GEN-BEGIN:|27-getter|1|27-postInit
            Broom.addCommand(getGokBroom());
            Broom.addCommand(getItemCommand1());
            Broom.setCommandListener(this);//GEN-END:|27-getter|1|27-postInit
            // write post-init user code here
        }//GEN-BEGIN:|27-getter|2|
        return Broom;
    }
//</editor-fold>//GEN-END:|27-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: Proom ">//GEN-BEGIN:|28-getter|0|28-preInit
    /**
     * Returns an initialized instance of Proom component.
     *
     * @return the initialized component instance
     */
    public Form getProom() {
        if (Proom == null) {//GEN-END:|28-getter|0|28-preInit
            // write pre-init user code here
            Proom = new Form("Proom", new Item[]{getTextField4(), getTextField5(), getTextField6()});//GEN-BEGIN:|28-getter|1|28-postInit
            Proom.addCommand(getGokProom());
            Proom.addCommand(getItemCommand3());
            Proom.setCommandListener(this);//GEN-END:|28-getter|1|28-postInit
            // write post-init user code here
        }//GEN-BEGIN:|28-getter|2|
        return Proom;
    }
//</editor-fold>//GEN-END:|28-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: Croom ">//GEN-BEGIN:|29-getter|0|29-preInit
    /**
     * Returns an initialized instance of Croom component.
     *
     * @return the initialized component instance
     */
    public Form getCroom() {
        if (Croom == null) {//GEN-END:|29-getter|0|29-preInit
            // write pre-init user code here
            Croom = new Form("Croom", new Item[]{getTextField7(), getTextField8()});//GEN-BEGIN:|29-getter|1|29-postInit
            Croom.addCommand(getGokCroom());
            Croom.addCommand(getItemCommand2());
            Croom.setCommandListener(this);//GEN-END:|29-getter|1|29-postInit
            // write post-init user code here
        }//GEN-BEGIN:|29-getter|2|
        return Croom;
    }
//</editor-fold>//GEN-END:|29-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: itemCommand ">//GEN-BEGIN:|30-getter|0|30-preInit
    /**
     * Returns an initialized instance of itemCommand component.
     *
     * @return the initialized component instance
     */
    public Command getItemCommand() {
        if (itemCommand == null) {//GEN-END:|30-getter|0|30-preInit
            // write pre-init user code here
            itemCommand = new Command("Item", Command.ITEM, 0);//GEN-LINE:|30-getter|1|30-postInit
            // write post-init user code here
        }//GEN-BEGIN:|30-getter|2|
        return itemCommand;
    }
//</editor-fold>//GEN-END:|30-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: GokMenu ">//GEN-BEGIN:|39-getter|0|39-preInit
    /**
     * Returns an initialized instance of GokMenu component.
     *
     * @return the initialized component instance
     */
    public Command getGokMenu() {
        if (GokMenu == null) {//GEN-END:|39-getter|0|39-preInit
            // write pre-init user code here
            GokMenu = new Command("Ok", Command.OK, 0);//GEN-LINE:|39-getter|1|39-postInit
            // write post-init user code here
        }//GEN-BEGIN:|39-getter|2|
        return GokMenu;
    }
//</editor-fold>//GEN-END:|39-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand1 ">//GEN-BEGIN:|41-getter|0|41-preInit
    /**
     * Returns an initialized instance of exitCommand1 component.
     *
     * @return the initialized component instance
     */
    public Command getExitCommand1() {
        if (exitCommand1 == null) {//GEN-END:|41-getter|0|41-preInit
            // write pre-init user code here
            exitCommand1 = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|41-getter|1|41-postInit
            // write post-init user code here
        }//GEN-BEGIN:|41-getter|2|
        return exitCommand1;
    }
//</editor-fold>//GEN-END:|41-getter|2|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand2 ">//GEN-BEGIN:|43-getter|0|43-preInit
    /**
     * Returns an initialized instance of exitCommand2 component.
     *
     * @return the initialized component instance
     */
    public Command getExitCommand2() {
        if (exitCommand2 == null) {//GEN-END:|43-getter|0|43-preInit
            // write pre-init user code here
            exitCommand2 = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|43-getter|1|43-postInit
            // write post-init user code here
        }//GEN-BEGIN:|43-getter|2|
        return exitCommand2;
    }
//</editor-fold>//GEN-END:|43-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: GchoixMenu ">//GEN-BEGIN:|44-getter|0|44-preInit
    /**
     * Returns an initialized instance of GchoixMenu component.
     *
     * @return the initialized component instance
     */
    public List getGchoixMenu() {
        if (GchoixMenu == null) {//GEN-END:|44-getter|0|44-preInit
            // write pre-init user code here
            GchoixMenu = new List("Menu", Choice.IMPLICIT);//GEN-BEGIN:|44-getter|1|44-postInit
            GchoixMenu.append("Broom", null);
            GchoixMenu.append("Proom", null);
            GchoixMenu.append("Croom", null);
            GchoixMenu.addCommand(getExitCommand3());
            GchoixMenu.setCommandListener(this);
            GchoixMenu.setSelectedFlags(new boolean[]{false, false, false});//GEN-END:|44-getter|1|44-postInit
            // write post-init user code here
        }//GEN-BEGIN:|44-getter|2|
        return GchoixMenu;
    }
//</editor-fold>//GEN-END:|44-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: GchoixMenuAction ">//GEN-BEGIN:|44-action|0|44-preAction
    /**
     * Performs an action assigned to the selected list element in the
     * GchoixMenu component.
     */
    public void GchoixMenuAction() {//GEN-END:|44-action|0|44-preAction
        // enter pre-action user code here
        String __selectedString = getGchoixMenu().getString(getGchoixMenu().getSelectedIndex());//GEN-BEGIN:|44-action|1|48-preAction
        if (__selectedString != null) {
            if (__selectedString.equals("Broom")) {//GEN-END:|44-action|1|48-preAction
                // write pre-action user code here
                //switchDisplayable(null, getBroom());
                switchDisplayable(null, getBroom());//GEN-LINE:|44-action|2|48-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Proom")) {//GEN-LINE:|44-action|3|49-preAction
                // write pre-action user code here
                //switchDisplayable(null, getProom());
                switchDisplayable(null, getCroom());//GEN-LINE:|44-action|4|49-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Croom")) {//GEN-LINE:|44-action|5|50-preAction
                // write pre-action user code here
                //switchDisplayable(null, getCroom());
                switchDisplayable(null, getProom());//GEN-LINE:|44-action|6|50-postAction
                // write post-action user code here
            }//GEN-BEGIN:|44-action|7|44-postAction
        }//GEN-END:|44-action|7|44-postAction
        // enter post-action user code here
    }//GEN-BEGIN:|44-action|8|
//</editor-fold>//GEN-END:|44-action|8|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: GokMenu1 ">//GEN-BEGIN:|51-getter|0|51-preInit
    /**
     * Returns an initialized instance of GokMenu1 component.
     *
     * @return the initialized component instance
     */
    public Command getGokMenu1() {
        if (GokMenu1 == null) {//GEN-END:|51-getter|0|51-preInit
            // write pre-init user code here
            GokMenu1 = new Command("Ok", Command.OK, 0);//GEN-LINE:|51-getter|1|51-postInit
            // write post-init user code here
        }//GEN-BEGIN:|51-getter|2|
        return GokMenu1;
    }
//</editor-fold>//GEN-END:|51-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand3 ">//GEN-BEGIN:|53-getter|0|53-preInit
    /**
     * Returns an initialized instance of exitCommand3 component.
     *
     * @return the initialized component instance
     */
    public Command getExitCommand3() {
        if (exitCommand3 == null) {//GEN-END:|53-getter|0|53-preInit
            // write pre-init user code here
            exitCommand3 = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|53-getter|1|53-postInit
            // write post-init user code here
        }//GEN-BEGIN:|53-getter|2|
        return exitCommand3;
    }
//</editor-fold>//GEN-END:|53-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: GcategorieBroom ">//GEN-BEGIN:|55-getter|0|55-preInit
    /**
     * Returns an initialized instance of GcategorieBroom component.
     *
     * @return the initialized component instance
     */
    public TextField getGcategorieBroom() {
        if (GcategorieBroom == null) {//GEN-END:|55-getter|0|55-preInit
            // write pre-init user code here
            GcategorieBroom = new TextField("Cat\u00E9gorie", null, 32, TextField.ANY);//GEN-LINE:|55-getter|1|55-postInit
            // write post-init user code here
        }//GEN-BEGIN:|55-getter|2|
        return GcategorieBroom;
    }
//</editor-fold>//GEN-END:|55-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: GtypeBroom ">//GEN-BEGIN:|56-getter|0|56-preInit
    /**
     * Returns an initialized instance of GtypeBroom component.
     *
     * @return the initialized component instance
     */
    public TextField getGtypeBroom() {
        if (GtypeBroom == null) {//GEN-END:|56-getter|0|56-preInit
            // write pre-init user code here
            GtypeBroom = new TextField("Type", null, 32, TextField.ANY);//GEN-LINE:|56-getter|1|56-postInit
            // write post-init user code here
        }//GEN-BEGIN:|56-getter|2|
        return GtypeBroom;
    }
//</editor-fold>//GEN-END:|56-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: GdateBroom ">//GEN-BEGIN:|57-getter|0|57-preInit
    /**
     * Returns an initialized instance of GdateBroom component.
     *
     * @return the initialized component instance
     */
    public TextField getGdateBroom() {
        if (GdateBroom == null) {//GEN-END:|57-getter|0|57-preInit
            // write pre-init user code here
            GdateBroom = new TextField("Date d\'arriv\u00E9e", null, 32, TextField.ANY);//GEN-LINE:|57-getter|1|57-postInit
            // write post-init user code here
        }//GEN-BEGIN:|57-getter|2|
        return GdateBroom;
    }
//</editor-fold>//GEN-END:|57-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: GnbNuitBroom ">//GEN-BEGIN:|58-getter|0|58-preInit
    /**
     * Returns an initialized instance of GnbNuitBroom component.
     *
     * @return the initialized component instance
     */
    public TextField getGnbNuitBroom() {
        if (GnbNuitBroom == null) {//GEN-END:|58-getter|0|58-preInit
            // write pre-init user code here
            GnbNuitBroom = new TextField("Nombre de nuit", null, 32, TextField.ANY);//GEN-LINE:|58-getter|1|58-postInit
            // write post-init user code here
        }//GEN-BEGIN:|58-getter|2|
        return GnbNuitBroom;
    }
//</editor-fold>//GEN-END:|58-getter|2|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: GokBroom ">//GEN-BEGIN:|60-getter|0|60-preInit
    /**
     * Returns an initialized instance of GokBroom component.
     *
     * @return the initialized component instance
     */
    public Command getGokBroom() {
        if (GokBroom == null) {//GEN-END:|60-getter|0|60-preInit
            // write pre-init user code here
            GokBroom = new Command("Ok", Command.OK, 0);//GEN-LINE:|60-getter|1|60-postInit
            // write post-init user code here
        }//GEN-BEGIN:|60-getter|2|
        return GokBroom;
    }
//</editor-fold>//GEN-END:|60-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand ">//GEN-BEGIN:|62-getter|0|62-preInit
    /**
     * Returns an initialized instance of backCommand component.
     *
     * @return the initialized component instance
     */
    public Command getBackCommand() {
        if (backCommand == null) {//GEN-END:|62-getter|0|62-preInit
            // write pre-init user code here
            backCommand = new Command("Back", Command.BACK, 0);//GEN-LINE:|62-getter|1|62-postInit
            // write post-init user code here
        }//GEN-BEGIN:|62-getter|2|
        return backCommand;
    }
//</editor-fold>//GEN-END:|62-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: GbackMenu ">//GEN-BEGIN:|64-getter|0|64-preInit
    /**
     * Returns an initialized instance of GbackMenu component.
     *
     * @return the initialized component instance
     */
    public Command getGbackMenu() {
        if (GbackMenu == null) {//GEN-END:|64-getter|0|64-preInit
            // write pre-init user code here
            GbackMenu = new Command("Retour Menu", Command.ITEM, 1);//GEN-LINE:|64-getter|1|64-postInit
            // write post-init user code here
        }//GEN-BEGIN:|64-getter|2|
        return GbackMenu;
    }
//</editor-fold>//GEN-END:|64-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand1 ">//GEN-BEGIN:|67-getter|0|67-preInit
    /**
     * Returns an initialized instance of backCommand1 component.
     *
     * @return the initialized component instance
     */
    public Command getBackCommand1() {
        if (backCommand1 == null) {//GEN-END:|67-getter|0|67-preInit
            // write pre-init user code here
            backCommand1 = new Command("Back", Command.BACK, 0);//GEN-LINE:|67-getter|1|67-postInit
            // write post-init user code here
        }//GEN-BEGIN:|67-getter|2|
        return backCommand1;
    }
//</editor-fold>//GEN-END:|67-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: itemCommand1 ">//GEN-BEGIN:|73-getter|0|73-preInit
    /**
     * Returns an initialized instance of itemCommand1 component.
     *
     * @return the initialized component instance
     */
    public Command getItemCommand1() {
        if (itemCommand1 == null) {//GEN-END:|73-getter|0|73-preInit
            // write pre-init user code here
            itemCommand1 = new Command("Retour", Command.ITEM, 0);//GEN-LINE:|73-getter|1|73-postInit
            // write post-init user code here
        }//GEN-BEGIN:|73-getter|2|
        return itemCommand1;
    }
//</editor-fold>//GEN-END:|73-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: GokCroom ">//GEN-BEGIN:|76-getter|0|76-preInit
    /**
     * Returns an initialized instance of GokCroom component.
     *
     * @return the initialized component instance
     */
    public Command getGokCroom() {
        if (GokCroom == null) {//GEN-END:|76-getter|0|76-preInit
            // write pre-init user code here
            GokCroom = new Command("Ok", Command.OK, 0);//GEN-LINE:|76-getter|1|76-postInit
            // write post-init user code here
        }//GEN-BEGIN:|76-getter|2|
        return GokCroom;
    }
//</editor-fold>//GEN-END:|76-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: itemCommand2 ">//GEN-BEGIN:|78-getter|0|78-preInit
    /**
     * Returns an initialized instance of itemCommand2 component.
     *
     * @return the initialized component instance
     */
    public Command getItemCommand2() {
        if (itemCommand2 == null) {//GEN-END:|78-getter|0|78-preInit
            // write pre-init user code here
            itemCommand2 = new Command("Retour", Command.ITEM, 0);//GEN-LINE:|78-getter|1|78-postInit
            // write post-init user code here
        }//GEN-BEGIN:|78-getter|2|
        return itemCommand2;
    }
//</editor-fold>//GEN-END:|78-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: GokProom ">//GEN-BEGIN:|80-getter|0|80-preInit
    /**
     * Returns an initialized instance of GokProom component.
     *
     * @return the initialized component instance
     */
    public Command getGokProom() {
        if (GokProom == null) {//GEN-END:|80-getter|0|80-preInit
            // write pre-init user code here
            GokProom = new Command("Ok", Command.OK, 0);//GEN-LINE:|80-getter|1|80-postInit
            // write post-init user code here
        }//GEN-BEGIN:|80-getter|2|
        return GokProom;
    }
//</editor-fold>//GEN-END:|80-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: itemCommand3 ">//GEN-BEGIN:|82-getter|0|82-preInit
    /**
     * Returns an initialized instance of itemCommand3 component.
     *
     * @return the initialized component instance
     */
    public Command getItemCommand3() {
        if (itemCommand3 == null) {//GEN-END:|82-getter|0|82-preInit
            // write pre-init user code here
            itemCommand3 = new Command("Retour", Command.ITEM, 0);//GEN-LINE:|82-getter|1|82-postInit
            // write post-init user code here
        }//GEN-BEGIN:|82-getter|2|
        return itemCommand3;
    }
//</editor-fold>//GEN-END:|82-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField4 ">//GEN-BEGIN:|88-getter|0|88-preInit
    /**
     * Returns an initialized instance of textField4 component.
     *
     * @return the initialized component instance
     */
    public TextField getTextField4() {
        if (textField4 == null) {//GEN-END:|88-getter|0|88-preInit
            // write pre-init user code here
            textField4 = new TextField("Num\u00E9ro de chambre", null, 32, TextField.ANY);//GEN-LINE:|88-getter|1|88-postInit
            // write post-init user code here
        }//GEN-BEGIN:|88-getter|2|
        return textField4;
    }
//</editor-fold>//GEN-END:|88-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField5 ">//GEN-BEGIN:|89-getter|0|89-preInit
    /**
     * Returns an initialized instance of textField5 component.
     *
     * @return the initialized component instance
     */
    public TextField getTextField5() {
        if (textField5 == null) {//GEN-END:|89-getter|0|89-preInit
            // write pre-init user code here
            textField5 = new TextField("Num\u00E9ro carte de cr\u00E9dit", null, 32, TextField.ANY);//GEN-LINE:|89-getter|1|89-postInit
            // write post-init user code here
        }//GEN-BEGIN:|89-getter|2|
        return textField5;
    }
//</editor-fold>//GEN-END:|89-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField6 ">//GEN-BEGIN:|90-getter|0|90-preInit
    /**
     * Returns an initialized instance of textField6 component.
     *
     * @return the initialized component instance
     */
    public TextField getTextField6() {
        if (textField6 == null) {//GEN-END:|90-getter|0|90-preInit
            // write pre-init user code here
            textField6 = new TextField("Date de r\u00E9servation", null, 32, TextField.ANY);//GEN-LINE:|90-getter|1|90-postInit
            // write post-init user code here
        }//GEN-BEGIN:|90-getter|2|
        return textField6;
    }
//</editor-fold>//GEN-END:|90-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField7 ">//GEN-BEGIN:|91-getter|0|91-preInit
    /**
     * Returns an initialized instance of textField7 component.
     *
     * @return the initialized component instance
     */
    public TextField getTextField7() {
        if (textField7 == null) {//GEN-END:|91-getter|0|91-preInit
            // write pre-init user code here
            textField7 = new TextField("Num\u00E9ro chambre", null, 32, TextField.ANY);//GEN-LINE:|91-getter|1|91-postInit
            // write post-init user code here
        }//GEN-BEGIN:|91-getter|2|
        return textField7;
    }
//</editor-fold>//GEN-END:|91-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField8 ">//GEN-BEGIN:|92-getter|0|92-preInit
    /**
     * Returns an initialized instance of textField8 component.
     *
     * @return the initialized component instance
     */
    public TextField getTextField8() {
        if (textField8 == null) {//GEN-END:|92-getter|0|92-preInit
            // write pre-init user code here
            textField8 = new TextField("Date de r\u00E9servation", null, 32, TextField.ANY);//GEN-LINE:|92-getter|1|92-postInit
            // write post-init user code here
        }//GEN-BEGIN:|92-getter|2|
        return textField8;
    }
//</editor-fold>//GEN-END:|92-getter|2|

    /**
     * Returns a display instance.
     *
     * @return the display instance.
     */
    public Display getDisplay() {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable(null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started. Checks whether the MIDlet have been
     * already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet();
        } else {
            initialize();
            startMIDlet();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     *
     * @param unconditional if true, then the MIDlet has to be unconditionally
     * terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }


    /********************************************************************************/
    //Helper
    /********************************************************************************/
    public Alert doAlert(String message){
        Alert alert = new Alert("Erreur", message, null, AlertType.ALARM);
        alert.setTimeout(Alert.FOREVER);
        return alert;
    }

    private void alert(String message) {
        switchDisplayable(null, doAlert(message));
    }

    /********************************************************************************/
    //Logique métier
    /********************************************************************************/
    private void actionOkLogin() {
        if(this.socket ==null){
            this.socket = new NetworkClientMobile(host, port);
        }
        if(Glogin.getString().length() == 0 || Gpassword.getString().length() == 0){
            alert("un champ est vide");
        }else{
            if(this.socket.isConnected()){
                this.socket.send("LOGIN");
                String message = this.socket.receive();
                traitementPacket(message);
            }else{
                alert("Erreur de connection réseau");
            }
        }
    }

    private void traitementPacket(String messageReponse) {
        String[] split = split(messageReponse, "#");
        String type = split[0];
        if(type.equals(Infos.NONCE)){
            String contenu = split(split[1], ";")[0];
            int number1 = Integer.parseInt(contenu);
            int number2 = new Random().nextInt(10000);
            this.login = Glogin.getString();
            byte[] digest = generateDigest(Glogin.getString(), Gpassword.getString(), number1, number2);
            String messageDigest = Base64.encode(digest, 0, 20);
            String packet = Infos.LOGIN_VOY_NEXT_STEP;
            packet += "#" + Glogin.getString() + ";" + messageDigest + ";" + number2 + ";";
            this.socket.send(packet);
            String message = this.socket.receive();
            traitementPacket(message);
        }else if(type.equals(Infos.LOGIN_OUI)){
            initKeyExchange();
            switchDisplayable(null, getGchoixMenu());
        }else if(type.equals("GET_SESSION_KEY_OUI")){
            String contenu = split[1];
            byte[] cleByte = Base64.decode(contenu);
            SecretKeySpec sks = new SecretKeySpec(cleByte, 0, cleByte.length, "DES");
            cleSession = sks;
            //switchDisplayable(null, getGchoixMenu());
        }else{
            String message = split[1];
            alert(message);
        }
        /*String type = packetRetour.getType();
        Object contenu = packetRetour.getContenu();
        if(type.equals(Infos.NONCE)){
        Object[] infos = (Object[]) contenu;
        Integer number1Integer = (Integer)infos[0];
        int number1 = number1Integer.intValue();
        int number2 = new Random().nextInt(10000);
        byte[] digest = generateDigest(Glogin.getString(), Gpassword.getString(), number1, number2);

        }else if(type.equals(Infos.LOGIN_OUI)){
        }else if(type.equals(Infos.LOGIN_NON)){
        }else{
        Object[] infos = (Object[]) contenu;
        String msg = (String)infos[0];
        alert(msg);
        }*/
    }

    private void initKeyExchange() {
        /*RSAKeyPairGenerator keyPairGenerator = new RSAKeyPairGenerator();
        KeyGenerationParameters myKeyGenerationParameters = new KeyGenerationParameters(null, 1024);
        keyPairGenerator.init(myKeyGenerationParameters);
        AsymmetricCipherKeyPair keys = keyPairGenerator.generateKeyPair();
        privateKey = (RSAPrivateCrtKeyParameters)keys.getPrivate();
        publicKey = (RSAKeyParameters)keys.getPublic();*/
        this.socket.send("GET_SESSION_KEY#GET_SESSION_KEY");
        String retour = this.socket.receive();
        traitementPacket(retour);
    }

    private byte[] generateDigest(String login, String password, int number1, int number2) {
        MessageDigest md;
        byte[] buffer = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(String.valueOf(number1).getBytes(), 0, String.valueOf(number1).getBytes().length);
            md.update(login.getBytes(), 0, login.getBytes().length);
            md.update(password.getBytes(), 0, password.getBytes().length);
            md.update(String.valueOf(number2).getBytes(), 0, String.valueOf(number2).getBytes().length);
            buffer = new byte[20];
            md.digest(buffer, 0, 255);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (DigestException ex) {
            ex.printStackTrace();
        }
        return buffer;
    }

    private static String[] split(String original, String delimiter) {
        Vector nodes = new Vector();
        String separator = delimiter;
        // Parse nodes into vector
        int index = original.indexOf(separator);
        while(index>=0) {
            nodes.addElement( original.substring(0, index) );
            original = original.substring(index+separator.length());
            index = original.indexOf(separator);
        }
        nodes.addElement( original );

        String[] result = new String[ nodes.size() ];
        if( nodes.size()>0 ) {
            for(int loop=0; loop<nodes.size(); loop++){
                result[loop] = (String)nodes.elementAt(loop);
                System.out.println(result[loop]);
            }
        }
        return result;
    }

    private void actionOkBroom() {
        String categorie = GcategorieBroom.getString();
        String type = GtypeBroom.getString();
        String date = GdateBroom.getString();
        String nbNuit = GnbNuitBroom.getString();

        byte[] categorieCrypted = encrypt(categorie.getBytes());
        byte[] typeCrypted = encrypt(type.getBytes());

        String message = Infos.BROOM;
        message += "#";
        message += categorie;
        message += ";";
        message += type;
        message += ";";
        message += date;
        message += ";";
        message += nbNuit;
        message += ";";

        this.socket.send(message);
        String retour = this.socket.receive();
        traitementPacket(message);

    }

    private void actionOkCroom() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void actionOkProom() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /*public byte[] encryptRSA(byte[] data){
        byte[] retour = null;
        AsymmetricBlockCipher eng = new RSAEngine();
        eng = new PKCS1Encoding(eng);
        eng.init(true, this.publicKey);
        try {
            retour = eng.processBlock(data, 0, data.length);
        } catch (InvalidCipherTextException ex) {
            ex.printStackTrace();
        }
        return retour;
    }

    public byte[] decryptRSA(byte[] data){
        byte[] retour = null;
        AsymmetricBlockCipher eng = new RSAEngine();
        eng = new PKCS1Encoding(eng);
        eng.init(true, this.privateKey);
        try {
            retour = eng.processBlock(data, 0, data.length);
        } catch (InvalidCipherTextException ex) {
            ex.printStackTrace();
        }
        return retour;
    }*/

    public byte[] encrypt(byte[] data){
        Cipher cipher = null;
        byte[] outputByte = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, cleSession);
            outputByte = new byte[255];
            cipher.doFinal(data, 0, data.length, outputByte, 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return outputByte;
    }

    public byte[] decrypt(byte[] data){
        Cipher cipher = null;
        byte[] outputByte = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, cleSession);
            outputByte = new byte[255];
            cipher.doFinal(data, 0, data.length, outputByte, 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return outputByte;
    }
}
