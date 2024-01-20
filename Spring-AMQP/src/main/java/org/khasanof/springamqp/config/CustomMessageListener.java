package org.khasanof.springamqp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author Nurislom
 * @see org.khasanof.springamqp.config
 * @since 1/20/2024 11:59 PM
 */
@Slf4j
public class CustomMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        log.info("Receive message!");
    }
}
