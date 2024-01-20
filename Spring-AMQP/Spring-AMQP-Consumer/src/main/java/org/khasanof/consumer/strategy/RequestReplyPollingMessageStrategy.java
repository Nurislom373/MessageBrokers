package org.khasanof.consumer.strategy;

import lombok.extern.slf4j.Slf4j;
import org.khasanof.consumer.PollingMessageStrategy;
import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.khasanof.springamqp.config.RabbitConstants.*;

/**
 * @author Nurislom
 * @see org.khasanof.consumer.strategy
 * @since 1/20/2024 11:26 PM
 */
@Slf4j
public class RequestReplyPollingMessageStrategy implements PollingMessageStrategy {

    @Override
    public void receive(RabbitTemplate rabbitTemplate) {
        rabbitTemplate.receiveAndReply(DEFAULT_QUEUE_NAME, payload -> {
            log.info(" [X] [Request/Reply-Strategy] receive message - {}", payload);
            return payload;
        }, (request, reply) -> new Address(DEFAULT_EXCHANGE_NAME, DEFAULT_REPLY_ROUTING_KEY));
    }
}
