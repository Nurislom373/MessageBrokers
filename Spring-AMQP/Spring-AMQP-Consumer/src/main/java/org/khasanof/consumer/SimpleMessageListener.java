package org.khasanof.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.AbstractJackson2MessageConverter;

/**
 * @author Nurislom
 * @see org.khasanof.consumer
 * @since 1/20/2024 1:07 AM
 */
@Slf4j
public class SimpleMessageListener implements MessageListener {

    private final AbstractJackson2MessageConverter messageConverter;

    public SimpleMessageListener(AbstractJackson2MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Override
    public void onMessage(Message message) {
        Object deserializeMessage = messageConverter.fromMessage(message);
        log.info(" [X] received message from message listener - {}", deserializeMessage);
    }
}
