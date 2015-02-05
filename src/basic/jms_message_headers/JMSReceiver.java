package basic.jms_message_headers;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * User: Szymon Mezglewski
 * Date: 01.02.15
 */
public class JMSReceiver {
    public static void main(String[] args) throws JMSException {
        Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //if true (session transacted), acknowledge type is ignored

        Queue tradeQueue = session.createQueue("EM_TRADE.Q");
        //if EM_TRADE.Q not exists in jms broker - it will be created dynammically

        MessageConsumer consumer = session.createConsumer(tradeQueue);

        TextMessage message = (TextMessage)consumer.receive();
        printHeaders(message);



        System.out.println("Message received: "+message.getText());

        connection.close();
    }

    private static void printHeaders(Message message) throws JMSException {
        System.out.println("Delivery mode: "+message.getJMSDeliveryMode());
        System.out.println("Expiration: "+message.getJMSExpiration());
        System.out.println("Priority: "+message.getJMSPriority());
    }
}
