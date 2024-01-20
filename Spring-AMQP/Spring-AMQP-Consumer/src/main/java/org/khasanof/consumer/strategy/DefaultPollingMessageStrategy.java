package org.khasanof.consumer.strategy;

import lombok.extern.slf4j.Slf4j;
import org.khasanof.consumer.PollingMessageStrategy;
import org.khasanof.springamqp.config.RabbitConstants;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Objects;

/**
 * @author Nurislom
 * @see org.khasanof.consumer.strategy
 * @since 1/20/2024 11:01 PM
 */
@Slf4j
public class DefaultPollingMessageStrategy implements PollingMessageStrategy {

    @Override
    public void receive(RabbitTemplate rabbitTemplate) {
        Message receive = rabbitTemplate.receive(RabbitConstants.DEFAULT_QUEUE_NAME);
        logger(receive);
    }

    private void logger(Message receive) {
        if (Objects.nonNull(receive)) {
            log.info(" [X] [Default-Strategy] received message - {}", new String(receive.getBody()));
        }
    }

}
