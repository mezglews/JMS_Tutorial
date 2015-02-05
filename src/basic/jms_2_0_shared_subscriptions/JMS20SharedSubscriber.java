package basic.jms_2_0_shared_subscriptions;

import com.sun.messaging.ConnectionFactory;

import javax.jms.*;

/**
 * User: Szymon Mezglewski
 * Date: 01.02.15
 */
public class JMS20SharedSubscriber implements MessageListener {
    //run many (3) subscriber at the same time
    // just one subscriber from subscription gets a message - load balancing

    public JMS20SharedSubscriber() {
        try {
            ConnectionFactory cf = new ConnectionFactory();
            JMSContext jmsContext = cf.createContext();
            Topic topic = jmsContext.createTopic("EM_JMS2_TRADE.T");

            String subscriptionId = "sub:3e"; //just random but meaningful string
            JMSConsumer jmsConsumer = jmsContext.createSharedConsumer(topic, subscriptionId);
            // ^ createSharedConsumer creates shared consumer by given subscriptionId


            jmsConsumer.setMessageListener(this);
        } catch (Exception e) {}
    }

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                new JMS20SharedSubscriber();
                System.out.println("Shared subscriber started: "+Thread.currentThread().getName());
            }
        }.start();
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("On message: "+message.getBody(String.class));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
