package basic.ack_modes;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * User: Szymon Mezglewski
 * Date: 01.02.15
 */
public class JMSReceiver {
    public static void main(String[] args) throws JMSException, InterruptedException {
        Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //not wait for msg.acknoledgment. automatically removed from provider

        //Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        //if exception occured - msg will not be acknoledged

        //Session session = connection.createSession(false, Session.DUPS_OK_ACKNOWLEDGE);
        //message is automatically marked by provier as delivered and acknoledged - and automatically removed


        Queue tradeQueue = session.createQueue("EM_TRADE.Q");
        //if EM_TRADE.Q not exists in jms broker - it will be created dynammically

        MessageConsumer consumer = session.createConsumer(tradeQueue);

        TextMessage message = (TextMessage)consumer.receive();


        System.out.println("Message received: "+message.getText()+". Processing...");

        Thread.sleep(6000); //simulate processing

        System.out.println("Message processed");

        if(true) {
            throw new NullPointerException("uups");
        }

        message.acknowledge();

        connection.close();
    }
}
