package basic.jms_1_1_mq_sender_simple_text_msg;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * User: Szymon Mezglewski
 * Date: 31.01.15
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
        //this is blocking wait, it's possible to define timeout in ms

        //TextMessage message = (TextMessage)consumer.receiveNoWait();
        // receiveNoWait does not block - return null if no messages

        System.out.println("Message received: "+message.getText());

        connection.close();
    }
}
