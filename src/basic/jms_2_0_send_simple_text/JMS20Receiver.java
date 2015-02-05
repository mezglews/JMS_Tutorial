package basic.jms_2_0_send_simple_text;

import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;

/**
 * User: Szymon Mezglewski
 * Date: 01.02.15
 */
public class JMS20Receiver {
    public static void main(String[] args) throws JMSException {
        //this example can be run on OpenMQ only - it does support JMS 2.0 (ActiveMQ does not)

        ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();

        try(JMSContext jmsContext = connectionFactory.createContext();) {
            Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
            Message message = jmsContext.createConsumer(queue).receive();
            String body = message.getBody(String.class);
            String trader = message.getStringProperty("TraderName");

            System.out.println("Message received: "+body+" from trader: "+trader);
        }

    }
}
