package basic.jms_message_selectors;

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

        String filter = "Stage = 'open'";
        MessageConsumer consumer = session.createConsumer(tradeQueue, filter);

        TextMessage message = (TextMessage)consumer.receive();


        System.out.println("Message received: "+message.getText());
        System.out.println("Filter: "+consumer.getMessageSelector());

        connection.close();
    }
}
