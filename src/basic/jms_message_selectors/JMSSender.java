package basic.jms_message_selectors;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Date;

/**
 * User: Szymon Mezglewski
 * Date: 01.02.15
 */
public class JMSSender {
    //need to set header property - stage

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue tradeQueue = session.createQueue("EM_TRADE.Q");

        MessageProducer producer = session.createProducer(tradeQueue);
        //destination is queue or topic - queue in this example

        TextMessage textMessage = session.createTextMessage("BUY AAPL 1000 SHARES");
        textMessage.setStringProperty("Stage", "open");


        producer.send(textMessage);
        System.out.println("Message is sent.");

        connection.close();
        //if close connection - it will close all sessions automatically
    }

}
