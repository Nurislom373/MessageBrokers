package org.khasanof.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.khasanof.DefaultConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import static org.khasanof.GlobalConstants.QUEUE_NAME;

/**
 * @author Nurislom
 * @see org.khasanof.queue
 * @since 1/11/2024 11:25 PM
 */
public class QConsumer {

    private static final DefaultConnectionFactory factory = new DefaultConnectionFactory();

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = factory.getConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        while (true) {
            Thread.sleep(1000);
            receiveMessages(channel);
        }
    }

    public static void receiveMessages(Channel channel) throws IOException, TimeoutException {
        DeliverCallback deliverCallback = ((consumerTag, message) -> {
            String text = new String(message.getBody(), StandardCharsets.UTF_8);

            System.out.println(" ########### Received '" + text + "'");
            try {
                doWork(text);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("########### Done");
            }
        });

        boolean autoAck = false; // acknowledgment is covered below // xabar bajarilganligini tasdiqlash faqat tugatilgandan so'ng
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch : task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }
}
