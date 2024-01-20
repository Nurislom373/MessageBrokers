package org.khasanof.consumer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author Nurislom
 * @see org.khasanof.consumer
 * @since 1/20/2024 11:00 PM
 */
public interface PollingMessageStrategy {

    void receive(RabbitTemplate rabbitTemplate);

}
