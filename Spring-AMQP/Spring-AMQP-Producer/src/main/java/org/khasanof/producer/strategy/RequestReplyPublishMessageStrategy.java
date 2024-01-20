package org.khasanof.producer.strategy;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.khasanof.producer.PublishMessageStrategy;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.khasanof.springamqp.config.RabbitConstants.*;

/**
 * @author Nurislom
 * @see org.khasanof.producer.strategy
 * @since 1/20/2024 11:16 PM
 */
@Slf4j
public class RequestReplyPublishMessageStrategy implements PublishMessageStrategy {

    private final Faker faker = Faker.instance();

    @SneakyThrows
    @Override
    public void send(RabbitTemplate rabbitTemplate) {
        String titleMessage = faker.book().title();
        UUID uuid = UUID.randomUUID();

        CorrelationData correlationData = new CorrelationData(uuid.toString());
        rabbitTemplate.convertSendAndReceive(DEFAULT_QUEUE_NAME, titleMessage.getBytes(StandardCharsets.UTF_8), correlationData);

        while (correlationData.getFuture().isDone()) {
            ReturnedMessage returned = correlationData.getReturned();
            assert returned != null;
            String receivedMessage = new String(returned.getMessage().getBody());
            log.info(" [X] [Request/Reply-Strategy] reply message - {}", receivedMessage);
        }
    }
}
