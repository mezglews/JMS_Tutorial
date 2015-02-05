package advanced.jms_advanced.point2point.queueRequestor;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * User: Szymon Mezglewski
 * Date: 04.02.15
 */
public class JMSSender {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        QueueConnection connection = connectionFactory.createQueueConnection();
        connection.start();

        QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        //if true (session transacted), acknowledge type is ignored

        Queue requestQueue = session.createQueue("EM_TRADE_REQ.Q");
        //there is not queue response definition

        TextMessage msg = session.createTextMessage("BUY AAPL 1000 SHARES");

        QueueRequestor queueRequestor = new QueueRequestor(session, requestQueue);

        Message response = queueRequestor.request(msg);


        System.out.println("Conf = "+response.getBody(String.class));



        connection.close();
    }
}
