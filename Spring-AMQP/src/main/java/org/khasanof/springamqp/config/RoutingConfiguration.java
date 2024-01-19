package org.khasanof.springamqp.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nurislom
 * @see org.khasanof.springamqp.config
 * @since 1/20/2024 12:44 AM
 */
@Configuration
public class RoutingConfiguration {

    @Bean
    public Queue queue() {
        return new Queue(RabbitConstants.DEFAULT_QUEUE_NAME);
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange(RabbitConstants.DEFAULT_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(RabbitConstants.DEFAULT_ROUTING_KEY)
                .noargs();
    }

}
