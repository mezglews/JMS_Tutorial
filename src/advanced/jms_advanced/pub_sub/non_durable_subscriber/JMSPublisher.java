package advanced.jms_advanced.pub_sub.non_durable_subscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.text.DecimalFormat;

/**
 * User: Szymon Mezglewski
 * Date: 05.02.15
 */
public class JMSPublisher {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic("EM_TRADE.T");

        MessageProducer publisher = session.createProducer(topic);

        TextMessage message = session.createTextMessage("AAPL "+getPrice());

        publisher.send(message);
        System.out.println("Message sent");

        connection.close();
    }

    private static String getPrice() {
        DecimalFormat df = new DecimalFormat("##.00");
        String price = df.format(98.0 + Math.random());
        return price;
    }
}
