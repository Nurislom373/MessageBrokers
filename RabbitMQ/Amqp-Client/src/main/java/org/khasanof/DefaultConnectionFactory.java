package org.khasanof;

import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 1/11/2024 12:01 AM
 */
public class DefaultConnectionFactory {

    public ConnectionFactory getConnectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("localhost");

        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        return connectionFactory;
    }

}
