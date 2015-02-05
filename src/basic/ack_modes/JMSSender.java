package basic.ack_modes;

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

        // cannot set properties on message - they will be overrided by producer

        TextMessage textMessage = session.createTextMessage("BUY AAPL 1000 SHARES");

        producer.send(textMessage);
        System.out.println("Message is sent.");

        connection.close();
        //if close connection - it will close all sessions automatically
    }

}
