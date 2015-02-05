package basic.jms_1_1_mq_sender_simple_text_msg;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * User: Szymon Mezglewski
 * Date: 31.01.15
 */
public class JMSAsyncReceiver implements MessageListener {
    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                new JMSAsyncReceiver();
            }
        }.start();
    }

    public JMSAsyncReceiver() {
        try {
            Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue tradeQueue = session.createQueue("EM_TRADE.Q");
            MessageConsumer receiver = session.createConsumer(tradeQueue);

            receiver.setMessageListener(this);
            System.out.println("Waiting for messages...");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage)message;
            System.out.println("Message received: "+textMessage.getText());
        } catch (JMSException e) {}
    }
}
