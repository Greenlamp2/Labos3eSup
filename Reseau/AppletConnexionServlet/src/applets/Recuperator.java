/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applets;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;


public class Recuperator implements Runnable{
    private JLabel label;
    private URL urlServletNombre;
    public Recuperator(JLabel numero, URL url){
        label = numero;
        urlServletNombre = url;
    }

    @Override
    public void run() {
        while(true){
            System.out.println("Thread Recuperator lancé");
            try {
                tunnelHttp();
                Thread.sleep(1000 * 60 * 30);
            } catch (IOException ex) {
                Logger.getLogger(Recuperator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Recuperator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void tunnelHttp() throws IOException {
        URLConnection connection = urlServletNombre.openConnection();
        connection.setUseCaches(false);
        connection.setDefaultUseCaches(false);
        connection.setDoOutput(true);

        String infos = "action=nombre";

        send(connection, infos);

        String reponse = null;
        while((reponse = receive(connection)) != null){
            label.setText(reponse);
        }



    }

    private void send(URLConnection connection, String infos) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        PrintWriter pw = new PrintWriter(baos, true);
        pw.print(infos);
        pw.flush();

        String longueur = String.valueOf(baos.size());
        connection.setRequestProperty("Content-Length", longueur);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        try {
            baos.writeTo(connection.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Recuperator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String receive(URLConnection connection) {
        String reponse = null;
        try {
            BufferedReader reception = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            reponse = reception.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Recuperator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reponse;
    }

}
