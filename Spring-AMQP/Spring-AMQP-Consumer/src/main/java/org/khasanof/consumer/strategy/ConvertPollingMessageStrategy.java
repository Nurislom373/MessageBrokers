package org.khasanof.consumer.strategy;

import lombok.extern.slf4j.Slf4j;
import org.khasanof.consumer.PollingMessageStrategy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Objects;

import static org.khasanof.springamqp.config.RabbitConstants.DEFAULT_QUEUE_NAME;

/**
 * @author Nurislom
 * @see org.khasanof.consumer.strategy
 * @since 1/20/2024 11:02 PM
 */
@Slf4j
public class ConvertPollingMessageStrategy implements PollingMessageStrategy {

    @Override
    public void receive(RabbitTemplate rabbitTemplate) {
        String message = rabbitTemplate.receiveAndConvert(DEFAULT_QUEUE_NAME, getTypeReference());
        logger(message);
    }

    private void logger(String message) {
        if (Objects.nonNull(message)) {
            log.info(" [X] [Convert-Strategy] received message - {}", message);
        }
    }

    private ParameterizedTypeReference<String> getTypeReference() {
        return new ParameterizedTypeReference<>() {
        };
    }
}
