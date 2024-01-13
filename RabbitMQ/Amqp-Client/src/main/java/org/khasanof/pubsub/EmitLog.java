package org.khasanof.pubsub;

import com.github.javafaker.Faker;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.khasanof.DefaultConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author Nurislom
 * @see org.khasanof.pubsub
 * @since 1/13/2024 4:18 PM
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";
    private static final DefaultConnectionFactory defaultConnectionFactory = new DefaultConnectionFactory();
    private static final Faker FAKER = Faker.instance();

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = defaultConnectionFactory.getConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        for (int i = 0; i < 1000; i++) {

            String randomMessage = FAKER.programmingLanguage().name();
            channel.basicPublish(EXCHANGE_NAME, "", null, randomMessage.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [X] Sent Message '" + randomMessage + "'");

            Thread.sleep(500);
        }

        channel.close();
        connection.close();
    }

}
