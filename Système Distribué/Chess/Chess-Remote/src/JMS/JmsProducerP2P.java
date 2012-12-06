/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JMS;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class JmsProducerP2P {
    MessageProducer producer;
    Session session;
    Connection connection;

    public JmsProducerP2P(){
        Context jndiContext;
        try {
            jndiContext = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory)jndiContext.lookup("jms/javaee6/ConnectionFactory");
            Queue queue = (Queue)jndiContext.lookup("jms/javaee6/Queue");
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(queue);
        } catch (NamingException ex) {
            Logger.getLogger(JmsProducerP2P.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(JmsProducerP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMessage(String message, String identifiant){
        TextMessage textMessage;
        try {
            textMessage = session.createTextMessage(message);
            if(identifiant != null){
                textMessage.setStringProperty("id", identifiant);
            }
            producer.send(textMessage);
        } catch (JMSException ex) {
            Logger.getLogger(JmsProducerP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close(){
        try {
            connection.close();
        } catch (JMSException ex) {
            Logger.getLogger(JmsProducerP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
