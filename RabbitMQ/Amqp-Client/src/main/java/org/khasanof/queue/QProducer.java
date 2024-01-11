package org.khasanof.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.khasanof.DefaultChannelFactory;
import org.khasanof.DefaultConnectionFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeoutException;

import static org.khasanof.GlobalConstants.QUEUE_NAME;

/**
 * @author Nurislom
 * @see org.khasanof.queue
 * @since 1/11/2024 11:19 PM
 */
public class QProducer {

    private static final DefaultConnectionFactory factory = new DefaultConnectionFactory();

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = factory.getConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        for (int i = 0; i < 10000; i++) {
            sendQueue(i, channel);
            Thread.sleep(300);
        }
        channel.close();
        connection.close();
    }

    private static void sendQueue(int count, Channel channel) throws IOException, TimeoutException {
        channel.basicQos(1); // bir vaqtning o'zida faqat bitta taqdiqlanmagan xabar qabul qilishi mumkin.

        channel.queueDeclare(QUEUE_NAME, false, false, false, Collections.emptyMap());
        String message = "Hello Java " + count + " !";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" ########## Sent Message ############ ");
    }

}
