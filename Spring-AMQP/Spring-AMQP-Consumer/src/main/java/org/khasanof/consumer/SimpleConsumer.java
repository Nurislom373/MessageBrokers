package org.khasanof.consumer;

import lombok.extern.slf4j.Slf4j;
import org.khasanof.consumer.strategy.ConvertPollingMessageStrategy;
import org.khasanof.consumer.strategy.RequestReplyPollingMessageStrategy;
import org.khasanof.springamqp.config.RabbitConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Nurislom
 * @see org.khasanof.consumer
 * @since 1/20/2024 12:39 AM
 */
@Slf4j
@Component
public class SimpleConsumer {

    private final RabbitTemplate rabbitTemplate;
    private final PollingMessageStrategy pollingMessageStrategy = new RequestReplyPollingMessageStrategy();

    public SimpleConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 500)
    public void listen() {
        this.pollingMessageStrategy.receive(rabbitTemplate);
    }

}
