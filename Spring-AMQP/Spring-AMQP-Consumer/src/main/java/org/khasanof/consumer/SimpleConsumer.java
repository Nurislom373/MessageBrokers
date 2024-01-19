package org.khasanof.consumer;

import lombok.extern.slf4j.Slf4j;
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
//@Component
public class SimpleConsumer {

    private final RabbitTemplate rabbitTemplate;

    public SimpleConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 1000)
    public void listen() {
        String message = rabbitTemplate.receiveAndConvert(RabbitConstants.DEFAULT_QUEUE_NAME, new ParameterizedTypeReference<>() {
        });

        log.info(" [X] received message - {}", message);
    }

}
