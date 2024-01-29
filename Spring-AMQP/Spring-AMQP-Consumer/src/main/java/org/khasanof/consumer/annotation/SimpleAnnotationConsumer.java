package org.khasanof.consumer.annotation;

import lombok.extern.slf4j.Slf4j;
import org.khasanof.springamqp.config.RabbitConstants;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author Nurislom
 * @see org.khasanof.consumer.annotation
 * @since 1/29/2024 9:08 PM
 */
@Slf4j
@Component
public class SimpleAnnotationConsumer {

    /**
     *
     * @param text
     */
    @RabbitListener(queues = {RabbitConstants.DEFAULT_QUEUE_NAME})
    public void processFakeText(String text) {
        log.info("[X] first annotation listener receive message - {}", text);
    }

    /**
     *
     * @param text
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitConstants.SECOND_QUEUE_NAME, durable = "true"),
            exchange = @Exchange(value = RabbitConstants.DEFAULT_EXCHANGE_NAME, ignoreDeclarationExceptions = "true"),
            key = RabbitConstants.SECOND_ROUTING_KEY)
    )
    public void processSecondFakeText(String text) {
        log.info("[X] second annotation listener receive message - {}", text);
    }

    /**
     *
     * @param text
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(value = "auto.exch"),
            key = "invoiceRoutingKey")
    )
    public void processRandomQueue(String text) {
        log.info("[X] third annotation listener receive message - {}", text);
    }

    /**
     *
     * @param text
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "auto.headers", autoDelete = "true",
                    arguments = @Argument(name = "x-message-ttl", value = "10000",
                            type = "java.lang.Integer")),
            exchange = @Exchange(value = "auto.headers", type = ExchangeTypes.HEADERS, autoDelete = "true"),
            arguments = {
                    @Argument(name = "x-match", value = "all"),
                    @Argument(name = "thing1", value = "somevalue"),
                    @Argument(name = "thing2")
            })
    )
    public void handleWithHeader(String text) {
        log.info("[X] fourth annotation listener receive message - {}", text);
    }
}
