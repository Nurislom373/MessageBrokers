package org.khasanof.springamqp.connection;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ShutdownSignalException;
import org.springframework.amqp.rabbit.connection.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Nurislom
 * @see org.khasanof.springamqp.connection
 * @since 1/18/2024 10:27 PM
 */
@Configuration
public class ConnectionFactoryConfiguration {

    @Bean
    PooledChannelConnectionFactory connectionFactory() {
        ConnectionFactory rabbitConnectionFactory = new ConnectionFactory(); // this rabbit client connection factory
        rabbitConnectionFactory.setHost("localhost");
        return new PooledChannelConnectionFactory(rabbitConnectionFactory);
    }

    @Bean
    public CachingConnectionFactory ccf() {
        CachingConnectionFactory ccf = new CachingConnectionFactory();
        ccf.setAddresses("host1:5672,host2:5672,host3:5672");
        ccf.setAddressShuffleMode(AbstractConnectionFactory.AddressShuffleMode.INORDER);
        ccf.setConnectionListeners(List.of(connectionListener()));
        return ccf;
    }

    private ConnectionListener connectionListener() {
        return new ConnectionListener() {

            @Override
            public void onCreate(Connection connection) {
                System.out.println("[X] Connection created! - " + connection.isOpen());
            }

            @Override
            public void onClose(Connection connection) {
                ConnectionListener.super.onClose(connection);
            }

            @Override
            public void onShutDown(ShutdownSignalException signal) {
                ConnectionListener.super.onShutDown(signal);
            }

            @Override
            public void onFailed(Exception exception) {
                ConnectionListener.super.onFailed(exception);
            }
        };
    }

}
