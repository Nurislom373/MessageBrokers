package org.khasanof.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.khasanof.DefaultCancelCallback;
import org.khasanof.DefaultConnectionFactory;
import org.khasanof.DefaultDeliveryCallback;
import org.khasanof.RoutingKeyDeliveryCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Nurislom
 * @see org.khasanof.topic
 * @since 1/14/2024 3:40 PM
 */
public class ReceiveLogsTopicQ1 {

    private static final String EXCHANGE_NAME = "topic_logs";
    private static final DefaultConnectionFactory defaultConnectionFactory = new DefaultConnectionFactory();

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = defaultConnectionFactory.getConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, "*.orange.*");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        while (true) {
            channel.basicConsume(queueName, true, new RoutingKeyDeliveryCallback(), new DefaultCancelCallback());
        }
    }

}
