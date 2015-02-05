package advanced.jms_advanced.pub_sub.durable_subscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * User: Szymon Mezglewski
 * Date: 05.02.15
 */
public class JMSSubscriber implements MessageListener {
    //need to set a client ID as part of connection factory url ?jms.clientID=client:123"
    // or topicConnection.setClientID("123");

    //need to create DurableSubscriber with subscriber id
    public JMSSubscriber() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616?jms.clientID=123");
            TopicConnection topicConnection = connectionFactory.createTopicConnection();
            topicConnection.start();

            //topicConnection.setClientID("123");
            TopicSession session = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("EM_TRADE.T");

            String subscriberId = "sub:1";
            TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, subscriberId);

            topicSubscriber.setMessageListener(this);
            System.out.println("DurableSubscriber: Waiting for messages");
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
