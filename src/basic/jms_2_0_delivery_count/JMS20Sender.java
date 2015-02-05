package basic.jms_2_0_delivery_count;

import com.sun.messaging.ConnectionFactory;

import javax.jms.DeliveryMode;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.NamingException;

/**
 * User: Szymon Mezglewski
 * Date: 01.02.15
 */
public class JMS20Sender {
    public static void main(String[] args) throws NamingException {
        //this example can be run on OpenMQ only - it does support JMS 2.0 (ActiveMQ does not)

        ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();

        try(JMSContext jmsContext = connectionFactory.createContext();) {
            Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
            jmsContext
                    .createProducer()
                    .setProperty("TraderName", "Mark")
                    .setDeliveryMode(DeliveryMode.NON_PERSISTENT)
                    .send(queue, "SELL AAPL 1500 SHARES");

            System.out.println("Message sent.");
        }
    }
}
