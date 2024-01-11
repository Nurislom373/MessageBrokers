package org.khasanof;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 1/11/2024 11:12 PM
 */
public class DefaultChannelFactory {

    private final DefaultConnectionFactory factory;

    public DefaultChannelFactory(DefaultConnectionFactory factory) {
        this.factory = factory;
    }

    public Channel create() {
        try (Connection connection = createConnection()) {
            return createInternal(connection);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private Channel createInternal(Connection connection) throws IOException {
        return connection.createChannel();
    }

    private Connection createConnection() throws IOException, TimeoutException {
        return factory.getConnectionFactory().newConnection();
    }
}
