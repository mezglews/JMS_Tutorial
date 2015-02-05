package basic.jms_2_0_delivery_delay;

import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.NamingException;

/**
 * User: Szymon Mezglewski
 * Date: 01.02.15
 */
public class JMS20SenderDeliveryDelay {
    public static void main(String[] args) throws NamingException {
        //run sender and receiver -
        // sender will send message
        // receiver needs to wait 10 sek to message be available to consume

        ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();

        try(JMSContext jmsContext = connectionFactory.createContext();) {
            Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
            jmsContext
                    .createProducer()
                    .setDeliveryDelay(10000)
                    .send(queue, "SELL AAPL 1500 SHARES");

            System.out.println("Message sent.");
        }
    }
}
