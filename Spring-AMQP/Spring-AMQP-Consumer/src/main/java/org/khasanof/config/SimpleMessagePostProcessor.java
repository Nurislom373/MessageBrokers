package org.khasanof.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

/**
 * @author Nurislom
 * @see org.khasanof.config
 * @since 1/20/2024 1:27 AM
 */
@Slf4j
public class SimpleMessagePostProcessor implements MessagePostProcessor {

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        return message;
    }
}
