package advanced.jms_advanced.point2point.queueRequestor;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * User: Szymon Mezglewski
 * Date: 04.02.15
 */
public class JMSReceiver {
    public static void main(String[] args) throws JMSException, InterruptedException {
        Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //if true (session transacted), acknowledge type is ignored

        Queue tradeQueue = session.createQueue("EM_TRADE_REQ.Q");
        //if EM_TRADE.Q not exists in jms broker - it will be created dynammically

        MessageConsumer consumer = session.createConsumer(tradeQueue);

        TextMessage message = (TextMessage)consumer.receive();
        //this is blocking wait, it's possible to define timeout in ms

        System.out.println("Processing message: "+message.getText());

        Thread.sleep(4000);

        String confirmation = "EQ4345";

        System.out.println("Trade confirmation: "+confirmation);

        TextMessage outMsg = session.createTextMessage(confirmation);

        MessageProducer sender = session.createProducer(message.getJMSReplyTo());
        sender.send(outMsg);

        connection.close();
    }
}
