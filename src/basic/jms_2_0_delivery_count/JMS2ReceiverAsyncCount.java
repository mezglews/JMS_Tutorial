package basic.jms_2_0_delivery_count;

import com.sun.messaging.ConnectionFactory;
import com.sun.messaging.jms.Session;

import javax.jms.*;

/**
 * User: Szymon Mezglewski
 * Date: 01.02.15
 */
public class JMS2ReceiverAsyncCount  implements MessageListener {
    JMSContext context = null;

    public JMS2ReceiverAsyncCount() {
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            context = connectionFactory.createContext(Session.SESSION_TRANSACTED);

            Queue queue = context.createQueue("EM_JMS2_TRADE.Q");
            JMSConsumer consumer = context.createConsumer(queue);
            consumer.setMessageListener(this);
            System.out.println("Waiting for messages.");
        } catch (Exception e){}
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("Received message: "+message.getBody(String.class));
            Thread.sleep(500);

            int jmsxDeliveryCount = message.getIntProperty("JMSXDeliveryCount");

            if(jmsxDeliveryCount > 2) {
                System.out.println("Cannot process the message: sending to DLQ");
                //DLQ is dead letter queue
                context.commit();
                return;
            }
            //simulated error
            context.rollback();
            System.out.println("Error processing: retries = "+jmsxDeliveryCount);

        } catch (JMSException e) {}
        catch (InterruptedException e) {}
    }

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                new JMS2ReceiverAsyncCount();
            }
        }.start();
    }
}
