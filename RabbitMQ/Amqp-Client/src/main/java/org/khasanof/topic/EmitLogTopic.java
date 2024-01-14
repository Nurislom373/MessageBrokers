package org.khasanof.topic;

import com.github.javafaker.Faker;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.RandomUtils;
import org.khasanof.DefaultConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Nurislom
 * @see org.khasanof.topic
 * @since 1/14/2024 3:39 PM
 */
public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";
    private static final DefaultConnectionFactory defaultConnectionFactory = new DefaultConnectionFactory();
    private static final Faker FAKER = Faker.instance();

    private static final List<String> speeds = List.of("lazy", "quick", "normal");
    private static final List<String> colors = List.of("black", "white", "orange", "brown");
    private static final List<String> animals = List.of("fox", "rabbit", "elephant", "lion");

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = defaultConnectionFactory.getConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        for (int i = 0; i < 10000; i++) {

            String randomMessage = getRandomMessage();
            String randomRoutingKey = randomRoutingKey();
            channel.basicPublish(EXCHANGE_NAME, randomRoutingKey, null, randomMessage.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + randomRoutingKey + "' : '" + randomMessage + "'");

            Thread.sleep(500);
        }

        channel.close();
        connection.close();
    }

    private static String getRandomMessage() {
        return FAKER.programmingLanguage()
                .creator();
    }

    private static String randomRoutingKey() {
        return randomSpeed() + "." + randomColor() + "." + randomAnimal();
    }

    private static String randomSpeed() {
        return speeds.get(RandomUtils.nextInt(0, speeds.size()));
    }

    private static String randomColor() {
        return colors.get(RandomUtils.nextInt(0, colors.size()));
    }

    private static String randomAnimal() {
        return animals.get(RandomUtils.nextInt(0, animals.size()));
    }

}
