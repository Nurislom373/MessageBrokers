package org.khasanof.ttl_dlq.publisher;

import org.khasanof.ttl_dlq.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Nurislom
 * @see org.khasanof.ttl_dlq
 * @since 3/13/26
 */
@Service
public class WebhookProducer {

    private final RabbitTemplate rabbitTemplate;

    public WebhookProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendWithDelay(String payload, long delayMillis) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.DELAY_EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                payload,
                message -> {
                    message.getMessageProperties()
                            .setExpiration(String.valueOf(delayMillis));
                    return message;
                }
        );
    }
}
