package basic.jms_2_0_shared_subscriptions;

import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Topic;
import java.text.DecimalFormat;

/**
 * User: Szymon Mezglewski
 * Date: 01.02.15
 */
public class JMS20SharedPublisher {
    public static void main(String[] args) {
        ConnectionFactory cf = new ConnectionFactory();
        try(JMSContext jmsContext = cf.createContext();) {
            Topic topic = jmsContext.createTopic("EM_JMS2_TRADE.T");

            String price = getPrice();
            jmsContext.createProducer().send(topic, price);
            System.out.println("Message published!");
        }
    }

    private static String getPrice() {
        DecimalFormat df = new DecimalFormat("##.00");
        String price = df.format(98.0 + Math.random());
        return price;
    }
}



















