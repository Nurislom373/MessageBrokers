package org.khasanof.springamqp.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.AbstractJackson2MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import static org.khasanof.springamqp.config.RabbitConstants.*;

/**
 * @author Nurislom
 * @see org.khasanof.springamqp.config
 * @since 1/18/2024 10:16 PM
 */
@Configuration
@Import({FakerConfiguration.class})
public class RabbitConfiguration {

    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        // convert config
        rabbitTemplate.setMessageConverter(messageConverter());
        // request/reply config
//        rabbitTemplate.setMandatory(true);
//        rabbitTemplate.setUserCorrelationId(true);
        rabbitTemplate.setReplyAddress(DEFAULT_REPLY_QUEUE_NAME);
        rabbitTemplate.setReplyTimeout(60_000);
        return rabbitTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer replyListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(new Queue(DEFAULT_REPLY_QUEUE_NAME));
        container.setMessageListener(rabbitTemplate());
        return container;
    }

    @Bean
    public AbstractJackson2MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
