/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Constants;


public class Infos {
    public static String HOST = "127.0.0.1";
    public static int PORT = 8501;

    public static String LOGIN = "LOGIN";
    public static String LOGIN_OUI = "LOGIN_OUI";
    public static String LOGIN_NON = "LOGIN_NON";
    public static String NONCE = "NONCE";
    public static String LOGIN_NEXT_STEP = "LOGIN_NEXT_STEP";
    public static String LOGIN_VOY_NEXT_STEP = "LOGIN_VOY_NEXT_STEP";
    public static String KEY_EXCHANGE = "KEY_EXCHANGE";
    public static String ERROR = "ERROR";
    public static String BROOM = "BROOM";
    public static String EOC = "EOC";

    public static String getUrl(String host, int port){
        return "socket://" + host + ":" + port;
    }
}
