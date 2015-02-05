package advanced.jms_advanced.point2point.correlationId;

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
    public static void main(String[] args) throws JMSException, InterruptedException {
        //this example can be run on OpenMQ only - it does support JMS 2.0 (ActiveMQ does not)

        ConnectionFactory connectionFactory = new ConnectionFactory();

        try(JMSContext jmsContext = connectionFactory.createContext();) {
            Queue queue = jmsContext.createQueue("EM_JMS2_TRADE_REQ.Q");
            Message message = jmsContext.createConsumer(queue).receive();
            String body = message.getBody(String.class);
            System.out.println("Processing trade: "+body);
            Thread.sleep(4000);
            String confirmation = "DE12345G";
            System.out.println("Trade confirmation: "+confirmation);

            jmsContext.createProducer()
                    .setProperty("MessageLink", message.getStringProperty("Uuid"))
                    .send(message.getJMSReplyTo(), confirmation);
        }

    }
}
