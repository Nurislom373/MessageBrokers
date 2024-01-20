package org.khasanof.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author Nurislom
 * @see org.khasanof.producer
 * @since 1/20/2024 11:14 PM
 */
public interface PublishMessageStrategy {

    void send(RabbitTemplate rabbitTemplate);

}
