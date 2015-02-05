package advanced.jms_advanced.pub_sub.non_durable_shared_subscriber;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Topic;
import java.text.DecimalFormat;

/**
 * User: Szymon Mezglewski
 * Date: 05.02.15
 */
public class JMS2Publisher {
    public static void main(String[] args) throws JMSException {
        com.sun.messaging.ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
        try (JMSContext jmsContext = cf.createContext();){
            Topic topic = jmsContext.createTopic("EM_TRADE_JMS2_T");


            jmsContext.createProducer().send(topic, getPrice());
        }

        System.out.println("Message sent");
    }

    private static String getPrice() {
        DecimalFormat df = new DecimalFormat("##.00");
        String price = df.format(98.0 + Math.random());
        return price;
    }
}
