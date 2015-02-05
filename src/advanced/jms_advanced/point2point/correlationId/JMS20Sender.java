package advanced.jms_advanced.point2point.correlationId;

import com.sun.messaging.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.NamingException;
import java.util.UUID;

/**
 * User: Szymon Mezglewski
 * Date: 01.02.15
 */
public class JMS20Sender {
    public static void main(String[] args) throws NamingException {
        //this example can be run on OpenMQ only - it does support JMS 2.0 (ActiveMQ does not)

        ConnectionFactory connectionFactory = new ConnectionFactory();

        try(JMSContext jmsContext = connectionFactory.createContext();) {
            Queue queueReq = jmsContext.createQueue("EM_JMS2_TRADE_REQ.Q");
            Queue queueResp = jmsContext.createQueue("EM_JMS2_TRADE_RESP.Q");

            String uuid = UUID.randomUUID().toString();

            jmsContext
                    .createProducer()
                    .setJMSReplyTo(queueResp)
                    .setProperty("Uuid", uuid)
                    .send(queueReq, "SELL AAPL 1500 SHARES");
            System.out.println("Message sent.");

            String filter = "MessageLink = '" + uuid + "'";

            String conf = jmsContext.createConsumer(queueResp, filter)
                    .receiveBody(String.class);
            System.out.println("Confirmation: " + conf);

        }
    }
}
