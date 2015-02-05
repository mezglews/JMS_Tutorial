package advanced.jms_advanced.pub_sub.non_durable_shared_subscriber;

import javax.jms.*;

/**
 * User: Szymon Mezglewski
 * Date: 05.02.15
 */
public class JMS2Subscriber implements MessageListener{

    public JMS2Subscriber() {
        try {
            ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
            JMSContext jmsContext = cf.createContext();
            Topic topic = jmsContext.createTopic("EM_TRADE_JMS2_T");

            String groupId = "sub:abc123";
            jmsContext
                    .createSharedConsumer(topic, groupId)
                    .setMessageListener(this);

            System.out.println("JMS2SharedSubscriber: Waiting on messages");

        } catch (Exception e) {

        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("Received: "+message.getBody(String.class));
        } catch (JMSException e) {
        }
    }

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                new JMS2Subscriber();
            }
        }.start();
    }
}
