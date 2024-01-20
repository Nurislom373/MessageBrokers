package org.khasanof.processor;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

/**
 * @author Nurislom
 * @see org.khasanof.processor
 * @since 1/20/2024 10:39 PM
 */
public class CustomMessagePostProcessor implements MessagePostProcessor {

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        // write your logic
        return message;
    }

    @Override
    public Message postProcessMessage(Message message, Correlation correlation) {
        // write your logic
        return MessagePostProcessor.super.postProcessMessage(message, correlation);
    }

    @Override
    public Message postProcessMessage(Message message, Correlation correlation, String exchange, String routingKey) {
        // write your logic
        return MessagePostProcessor.super.postProcessMessage(message, correlation, exchange, routingKey);
    }
}
