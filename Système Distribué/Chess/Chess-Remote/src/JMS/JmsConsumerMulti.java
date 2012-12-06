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
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class JmsConsumerMulti {
    Session session;
    Connection connection;
    MessageConsumer consumer;
    Topic topic;
    public static enum Types{SYNC, ASYNC};

    public JmsConsumerMulti(Types type, String messageSelector){
        Context jndiContext;
        try {
            jndiContext = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory)jndiContext.lookup("jms/javaee6/ConnectionFactory");
            topic = (Topic)jndiContext.lookup("jms/javaee6/Topic");
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            if(messageSelector == null){
                consumer = session.createConsumer(topic);
            }else{
                consumer = session.createConsumer(topic, "id like'" + messageSelector + "'");
            }
            if(type == Types.SYNC){
                connection.start();
            }
        } catch (NamingException ex) {
            Logger.getLogger(JmsProducerP2P.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(JmsProducerP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addSelector(String messageSelector){
        try {
            consumer = session.createConsumer(topic, messageSelector);
        } catch (JMSException ex) {
            Logger.getLogger(JmsConsumerMulti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String receiveMessage(){
        while(true){
            try {
                TextMessage textMessage = (TextMessage)consumer.receive();
                return textMessage.getText();
            } catch (JMSException ex) {
                Logger.getLogger(JmsConsumerMulti.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addListener(MessageListener listener){
        try {
            consumer.setMessageListener(listener);
            connection.start();
        } catch (JMSException ex) {
            Logger.getLogger(JmsConsumerMulti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close(){
        try {
            connection.close();
        } catch (JMSException ex) {
            Logger.getLogger(JmsConsumerMulti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
