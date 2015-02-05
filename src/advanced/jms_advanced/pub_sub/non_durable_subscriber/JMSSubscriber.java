package advanced.jms_advanced.pub_sub.non_durable_subscriber;

import org.apache.activemq.ActiveMQConnectionFactory;
import sun.print.resources.serviceui;

import javax.jms.*;

/**
 * User: Szymon Mezglewski
 * Date: 05.02.15
 */
public class JMSSubscriber implements MessageListener {
    //run many subscribers - all of them receive messages

    public JMSSubscriber() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            TopicConnection topicConnection = connectionFactory.createTopicConnection();
            topicConnection.start();

            TopicSession session = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("EM_TRADE.T");

            TopicSubscriber topicSubscriber = session.createSubscriber(topic);

            topicSubscriber.setMessageListener(this);
            System.out.println("Non-DurableSubscriber: Waiting for messages");
        } catch (Exception e) {

        }

    }

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage)message;
        try {
            System.out.println("Received: "+textMessage.getText());
        } catch (JMSException e) {
        }
    }

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                new JMSSubscriber();
            }
        }.start();
    }
}
