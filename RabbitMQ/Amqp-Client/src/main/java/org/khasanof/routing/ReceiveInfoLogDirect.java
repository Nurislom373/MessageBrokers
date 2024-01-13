package org.khasanof.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.khasanof.DefaultCancelCallback;
import org.khasanof.DefaultConnectionFactory;
import org.khasanof.DefaultDeliveryCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Nurislom
 * @see org.khasanof.routing
 * @since 1/13/2024 6:40 PM
 */
public class ReceiveInfoLogDirect {

    private static final String EXCHANGE_NAME = "direct_logs";
    private static final DefaultConnectionFactory defaultConnectionFactory = new DefaultConnectionFactory();

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = defaultConnectionFactory.getConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, "info");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        while (true) {
            channel.basicConsume(queueName, true, new DefaultDeliveryCallback(), new DefaultCancelCallback());
        }
    }

}
