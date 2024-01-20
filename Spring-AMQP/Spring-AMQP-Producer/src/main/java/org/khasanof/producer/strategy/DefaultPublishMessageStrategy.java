package org.khasanof.producer.strategy;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.khasanof.producer.PublishMessageStrategy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.khasanof.springamqp.config.RabbitConstants.DEFAULT_EXCHANGE_NAME;
import static org.khasanof.springamqp.config.RabbitConstants.DEFAULT_ROUTING_KEY;

/**
 * @author Nurislom
 * @see org.khasanof.producer.strategy
 * @since 1/20/2024 11:14 PM
 */
@Slf4j
public class DefaultPublishMessageStrategy implements PublishMessageStrategy {

    private final Faker faker = Faker.instance();

    @Override
    public void send(RabbitTemplate rabbitTemplate) {
        String message = faker.book().title();
        log.info(" [X] [Default-Strategy] send message - {}", message);
        rabbitTemplate.convertAndSend(DEFAULT_EXCHANGE_NAME, DEFAULT_ROUTING_KEY, message);
    }
}
