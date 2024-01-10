package org.khasanof;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.concurrent.TimeoutException;

import static org.khasanof.GlobalConstants.QUEUE_NAME;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 1/11/2024 12:25 AM
 */
public class DefaultConsumer {

    private final DefaultConnectionFactory factory;

    public DefaultConsumer(DefaultConnectionFactory factory) {
        this.factory = factory;
    }

    public void consumeQueue() {
        try (Connection connection = createConnection()) {
            tryConsumeQueue(connection);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private void tryConsumeQueue(Connection connection) {
        try (Channel channel = connection.createChannel()) {
            tryConsumeChannel(channel);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private void tryConsumeChannel(Channel channel) throws IOException {
        channel.queueDeclare(QUEUE_NAME, false, false, false, Collections.emptyMap());
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String text = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + text + "'");
        };
        channel.basicConsume(QUEUE_NAME, deliverCallback, consumerTag -> {});
    }

    private Connection createConnection() throws IOException, TimeoutException {
        return factory.getConnectionFactory().newConnection();
    }
}
