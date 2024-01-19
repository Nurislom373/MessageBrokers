package org.khasanof.producer;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static org.khasanof.springamqp.config.RabbitConstants.*;

/**
 * @author Nurislom
 * @see org.khasanof.producer
 * @since 1/20/2024 12:31 AM
 */
@Slf4j
@Component
public class SimpleProducer {

    private final Faker faker;
    private final RabbitTemplate rabbitTemplate;

    public SimpleProducer(Faker faker, RabbitTemplate rabbitTemplate) {
        this.faker = faker;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 500)
    public void scheduledProducer() {
        String message = faker.book().title();
        log.info(" [X] send message - {}", message);
        rabbitTemplate.convertAndSend(DEFAULT_EXCHANGE_NAME, DEFAULT_ROUTING_KEY, message);
    }

}
