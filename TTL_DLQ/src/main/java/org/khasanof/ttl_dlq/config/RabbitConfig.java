package org.khasanof.ttl_dlq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nurislom
 * @see org.khasanof.ttl_dlq
 * @since 3/13/26
 */
@Configuration
public class RabbitConfig {

    public static final String DELAY_EXCHANGE = "delay.exchange";
    public static final String DLX_EXCHANGE = "dlx.exchange";

    public static final String DELAY_QUEUE = "delay.queue";
    public static final String WEBHOOK_QUEUE = "webhook.queue";

    public static final String ROUTING_KEY = "webhook";

    @Bean
    DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE);
    }

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    @Bean
    public Queue delayQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", ROUTING_KEY);
        return new Queue(DELAY_QUEUE, true, false, false, args);
    }

    @Bean
    public Queue webhookQueue() {
        return new Queue(WEBHOOK_QUEUE);
    }

    @Bean
    public Binding delayBinding() {
        return BindingBuilder
                .bind(delayQueue())
                .to(delayExchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public Binding webhookBinding() {
        return BindingBuilder
                .bind(webhookQueue())
                .to(deadLetterExchange())
                .with(ROUTING_KEY);
    }
}
