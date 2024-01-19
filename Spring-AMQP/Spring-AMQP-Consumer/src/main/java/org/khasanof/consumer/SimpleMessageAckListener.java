package org.khasanof.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.MessageAckListener;

/**
 * @author Nurislom
 * @see org.khasanof.consumer
 * @since 1/20/2024 1:19 AM
 */
@Slf4j
public class SimpleMessageAckListener implements MessageAckListener {

    @Override
    public void onComplete(boolean success, long deliveryTag, Throwable cause) {
        log.info("message complete success - {}, deliveryTag - {}", success, deliveryTag);
    }
}
