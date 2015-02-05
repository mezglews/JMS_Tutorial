package advanced.jms_advanced.point2point.correlationId;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * User: Szymon Mezglewski
 * Date: 04.02.15
 */
public class JMSSender {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //if true (session transacted), acknowledge type is ignored

        Queue requestQueue = session.createQueue("EM_TRADE_REQ.Q");
        Queue responseQueue = session.createQueue("EM_TRADE_RES.Q");

        TextMessage msg = session.createTextMessage("BUY AAPL 1000 SHARES");
        msg.setJMSReplyTo(responseQueue);

        MessageProducer sender = session.createProducer(requestQueue);

        sender.send(msg);

        System.out.println("Message sent.");

        String filter = "JMSCorrelationID = '" + msg.getJMSMessageID() + "'";
        MessageConsumer receiver = session.createConsumer(responseQueue, filter);
        TextMessage msgResp = (TextMessage)receiver.receive();
        System.out.println("Confirmation response = "+msgResp.getText());

        connection.close();
    }
}
