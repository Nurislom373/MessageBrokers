package org.khasanof.rabbitmqrpc;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Nurislom
 * <br/>
 * Date: 21.05.2023
 * <br/>
 * Time: 17:25
 * <br/>
 * Package: org.khasanof.rabbitmqrpc
 */
@Configuration
public class Config {

    @Value("${queue.name}")
    private String queueName;

    @Value("${xchange.name}")
    private String directXchangeName;

    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directXchangeName);
    }

    @Bean
    public Binding binding(DirectExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with("roytuts");
    }


}
