package basic.jms_2_0_shared_subscriptions;

import com.sun.messaging.ConnectionFactory;

import javax.jms.*;

/**
 * User: Szymon Mezglewski
 * Date: 01.02.15
 */
public class JMS20Subscriber implements MessageListener {
    //run many (3) subscriber at the same time
    //every subscriber gets own copy of message

    public JMS20Subscriber() {
        try {
            ConnectionFactory cf = new ConnectionFactory();
            JMSContext jmsContext = cf.createContext();
            Topic topic = jmsContext.createTopic("EM_JMS2_TRADE.T");
            JMSConsumer jmsConsumer = jmsContext.createConsumer(topic);
            jmsConsumer.setMessageListener(this);
        } catch (Exception e) {}
    }

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                new JMS20Subscriber();
                System.out.println("Subscriber started: "+Thread.currentThread().getName());
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
