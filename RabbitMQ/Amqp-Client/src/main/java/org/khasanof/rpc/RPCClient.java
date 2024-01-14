package org.khasanof.rpc;

import com.github.javafaker.Faker;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.khasanof.DefaultConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author Nurislom
 * @see org.khasanof.rpc
 * @since 1/14/2024 4:43 PM
 */
public class RPCClient {

    private static final String RPC_QUEUE_NAME = "rpc_queue";
    private static final DefaultConnectionFactory defaultConnectionFactory = new DefaultConnectionFactory();

    public static void main(String[] args) throws IOException, TimeoutException, ExecutionException, InterruptedException {
        ConnectionFactory connectionFactory = defaultConnectionFactory.getConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        for (int i = 3; i < 25; i++) {
            String corrId = UUID.randomUUID().toString();

            String replyQueueName = channel.queueDeclare().getQueue();
            AMQP.BasicProperties props = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(corrId)
                    .replyTo(replyQueueName)
                    .build();

            channel.basicPublish("", RPC_QUEUE_NAME, props, String.valueOf(i)
                    .getBytes(StandardCharsets.UTF_8));

            final CompletableFuture<String> response = new CompletableFuture<>();

            String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
                if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                    response.complete(new String(delivery.getBody(), StandardCharsets.UTF_8));
                }
            }, consumerTag -> {
            });

            String result = response.get();
            channel.basicCancel(ctag);

            System.out.println(" [.] Got '" + result + "'");

            Thread.sleep(500);
        }

        channel.close();
        connection.close();
    }

}
