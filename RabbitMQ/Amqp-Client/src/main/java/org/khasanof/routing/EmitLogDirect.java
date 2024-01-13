package org.khasanof.routing;

import com.github.javafaker.Faker;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.khasanof.DefaultConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Nurislom
 * @see org.khasanof.routing
 * @since 1/13/2024 6:18 PM
 */
public class EmitLogDirect {

    private static final String EXCHANGE_NAME = "direct_logs";
    private static final DefaultConnectionFactory defaultConnectionFactory = new DefaultConnectionFactory();
    private static final Faker FAKER = Faker.instance();
    private static final List<String> severities = List.of("debug", "info", "warning", "error");

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = defaultConnectionFactory.getConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        for (int i = 0; i < 10000; i++) {

            String randomMessage = getRandomMessage();
            String randomSeverity = getRandomSeverity();
            channel.basicPublish(EXCHANGE_NAME, randomSeverity, null, randomMessage.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + randomSeverity + "':'" + randomMessage + "'");

            Thread.sleep(500);
        }

        channel.close();
        connection.close();
    }

    private static String getRandomMessage() {
        return FAKER.programmingLanguage()
                .creator();
    }

    private static String getRandomSeverity() {
        return severities.get(RandomUtils.nextInt(0, severities.size()));
    }

}
