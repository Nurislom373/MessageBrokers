package org.khasanof.producer;

import lombok.extern.slf4j.Slf4j;
import org.khasanof.producer.strategy.DefaultPublishMessageStrategy;
import org.khasanof.producer.strategy.RequestReplyPublishMessageStrategy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Nurislom
 * @see org.khasanof.producer
 * @since 1/20/2024 12:31 AM
 */
@Slf4j
@Component
public class SimpleProducer {

    private final RabbitTemplate rabbitTemplate;
    private final PublishMessageStrategy publishMessageStrategy = new RequestReplyPublishMessageStrategy();

    public SimpleProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 500)
    public void scheduledProducer() {
        this.publishMessageStrategy.send(rabbitTemplate);
    }

}
