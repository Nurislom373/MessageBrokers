package org.khasanof.producer;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.khasanof.springamqp.config.RabbitConstants.*;

/**
 * @author Nurislom
 * @see org.khasanof.producer
 * @since 1/20/2024 10:14 PM
 */
@Component
public class PublisherConfirm {

    private final RabbitTemplate rabbitTemplate;

    public PublisherConfirm(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     *
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    public void execute() throws ExecutionException, InterruptedException, TimeoutException {
        CorrelationData correlationData = new CorrelationData();
        String message = "Hello World!";
        rabbitTemplate.convertAndSend(DEFAULT_EXCHANGE_NAME, DEFAULT_ROUTING_KEY, message, correlationData);

        if (correlationData.getFuture().get(10, TimeUnit.SECONDS).isAck()) {
            ReturnedMessage returned = correlationData.getReturned();
            //
        }
    }

    /**
     *
     */
    private void waitForConfirmsOrDie() {
        Collection<String> sendMessages = getSendMessages();
        rabbitTemplate.invoke(operations -> {
            sendMessages.forEach(sendMessage -> operations.convertAndSend(DEFAULT_ROUTING_KEY, sendMessage));
            operations.waitForConfirmsOrDie(10_000);
            return true;
        });
    }

    private Collection<String> getSendMessages() {
        return List.of("Hello", "World", "Jecki");
    }

}
