package basic.jms_message_headers;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Date;

/**
 * User: Szymon Mezglewski
 * Date: 01.02.15
 */
public class JMSSender {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //if true (session transacted), acknowledge type is ignored

        Queue tradeQueue = session.createQueue("EM_TRADE.Q");
        //if EM_TRADE.Q not exists in jms broker - it will be created dynammically

        MessageProducer producer = session.createProducer(tradeQueue);
        //destination is queue or topic - queue in this example

        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer.setTimeToLive(new Date().getTime() + 10000);
        producer.setPriority(9);

        // cannot set properties on message - they will be overrided by producer

        TextMessage textMessage = session.createTextMessage("BUY AAPL 1000 SHARES");
        printHeaders(textMessage);

        producer.send(textMessage);
        System.out.println("Message is sent.");
        printHeaders(textMessage);

        connection.close();
        //if close connection - it will close all sessions automatically
    }

    private static void printHeaders(Message message) throws JMSException {
        System.out.println("Delivery mode: "+message.getJMSDeliveryMode());
        System.out.println("Expiration: "+message.getJMSExpiration());
        System.out.println("Priority: "+message.getJMSPriority());
    }
}
