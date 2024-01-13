package org.khasanof.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.khasanof.DefaultCancelCallback;
import org.khasanof.DefaultConnectionFactory;
import org.khasanof.DefaultDeliveryCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Nurislom
 * @see org.khasanof.routing
 * @since 1/13/2024 6:27 PM
 */
public class ReceiveLogsDirect {

    private static final String EXCHANGE_NAME = "direct_logs";
    private static final DefaultConnectionFactory defaultConnectionFactory = new DefaultConnectionFactory();
    private static final List<String> severities = List.of("debug", "info", "warning", "error");

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = defaultConnectionFactory.getConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();

        severities.forEach(severity -> {
            try {
                channel.queueBind(queueName, EXCHANGE_NAME, severity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        while (true) {
            channel.basicConsume(queueName, true, new DefaultDeliveryCallback(), new DefaultCancelCallback());
        }
    }

}
