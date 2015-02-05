package basic.jms_1_1_mq_jndi_simple_text_msg;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * User: Szymon Mezglewski
 * Date: 31.01.15
 */
public class JMSSender {
    public static void main(String[] args) throws Exception{
        Context context = new InitialContext();
        //look at jndi.properties in resource folder
        // need to add to activeMQ.xml below destination policy this code: (administered destination)

        // <destinations>
        //  <queue name="EM_TRADE.Q" physicalName = "EM_TRADE.Q"/>
        // </destinations>

        Connection connection = ((ConnectionFactory) context.lookup("ConnectionFactory")).createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue tradeQueue = (Queue)context.lookup("EM_TRADE.Q");

        MessageProducer producer = session.createProducer(tradeQueue);

        TextMessage textMessage = session.createTextMessage("BUY AAPL 1000 SHARES");
        textMessage.setStringProperty("TraderName","Szymon");
        // property names should have a naming convention, either camel case or Capital


        producer.send(textMessage);
        System.out.println("Message is sent.");

        connection.close();
    }
}
