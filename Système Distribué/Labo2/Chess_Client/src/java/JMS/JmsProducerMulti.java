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
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class JmsProducerMulti {
    MessageProducer producer;
    Session session;
    Connection connection;
    Topic topic;

    public JmsProducerMulti(){
        Context jndiContext;
        try {
            jndiContext = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory)jndiContext.lookup("jms/javaee6/ConnectionFactory");
            topic = (Topic)jndiContext.lookup("jms/javaee6/Topic");
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(topic);
        } catch (NamingException ex) {
            Logger.getLogger(JmsProducerMulti.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(JmsProducerMulti.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(JmsProducerMulti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close(){
        try {
            connection.close();
        } catch (JMSException ex) {
            Logger.getLogger(JmsProducerMulti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
