package org.khasanof.ttl_dlq.listener;

import org.khasanof.ttl_dlq.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author Nurislom
 * @see org.khasanof.ttl_dlq
 * @since 3/13/26
 */
@Component
public class WebhookConsumer {

    @RabbitListener(queues = RabbitConfig.WEBHOOK_QUEUE)
    public void consume(String payload) {
        System.out.println("Webhook received: " + payload);
        System.out.println("Time: " + Instant.now());
    }
}
