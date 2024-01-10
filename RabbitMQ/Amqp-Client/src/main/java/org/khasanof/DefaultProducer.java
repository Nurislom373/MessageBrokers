package org.khasanof;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeoutException;

import static org.khasanof.GlobalConstants.QUEUE_NAME;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 1/11/2024 12:04 AM
 */
public class DefaultProducer {

    private final DefaultConnectionFactory factory;

    public DefaultProducer(DefaultConnectionFactory connectionFactory) {
        this.factory = connectionFactory;
    }

    public void publishQueue() {
        try (Connection connection = createConnection()) {
            tryPublishQueue(connection);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private void tryPublishQueue(Connection connection) throws IOException {
        try (Channel channel = connection.createChannel()) {
            tryBasicPublish(channel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tryBasicPublish(Channel channel) throws IOException {
        channel.queueDeclare(QUEUE_NAME, false, false, false, Collections.emptyMap());
        String message = "Hello RabbitMQ!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [X] Sent '" + message + "'");
    }

    private Connection createConnection() throws IOException, TimeoutException {
        return factory.getConnectionFactory().newConnection();
    }

}
