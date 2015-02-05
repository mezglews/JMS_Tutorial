package basic.jms_2_0_asynchronous_send;

import com.sun.messaging.ConnectionFactory;

import javax.jms.CompletionListener;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Queue;
import java.util.concurrent.CountDownLatch;

/**
 * User: Szymon Mezglewski
 * Date: 01.02.15
 */
public class JMSAsyncSender {
    public static void main(String[] args) throws InterruptedException {
        ConnectionFactory cf = new ConnectionFactory();
        try (JMSContext jmsContext = cf.createContext();){
            Queue queue = jmsContext.createQueue("EM_JSM2_TRADE.Q");

            CountDownLatch latch = new CountDownLatch(1);
            MyCompletionListener completionListener = new MyCompletionListener(latch);
            jmsContext
                    .createProducer()
                    .setAsync(completionListener)
                    .send(queue, "Text message");
            System.out.println("Message sent!");

            for(int i=0; i<5; i++) {
                System.out.println("processing....");
                Thread.sleep(1000);
            }

            latch.await();

            System.out.println("End processing");
        }
    }

    private static class MyCompletionListener implements CompletionListener {
        public CountDownLatch latch;

        public MyCompletionListener(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onCompletion(Message message) {
            latch.countDown();
            System.out.println("Message acknowledged by server.");
        }

        @Override
        public void onException(Message message, Exception e) {
            latch.countDown();
            System.out.println("Unable to send message: "+ e);
        }
    }
}
