package org.khasanof.config;

import org.khasanof.consumer.SimpleMessageAckListener;
import org.khasanof.consumer.SimpleMessageListener;
import org.khasanof.springamqp.config.RabbitConstants;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageAckListener;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.AbstractJackson2MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nurislom
 * @see org.khasanof.config
 * @since 1/20/2024 1:13 AM
 */
@Configuration
public class MessageListenerContainerConfiguration {

    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, MessageListener messageListener) {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory);
        messageListenerContainer.setQueueNames(RabbitConstants.DEFAULT_QUEUE_NAME);
        messageListenerContainer.setMessageListener(messageListener);
        messageListenerContainer.setMessageAckListener(messageAckListener());
        messageListenerContainer.setAfterReceivePostProcessors();
        return messageListenerContainer;
    }

    @Bean
    public MessageListener messageListener(AbstractJackson2MessageConverter messageConverter) {
        return new SimpleMessageListener(messageConverter);
    }

    @Bean
    public MessageAckListener messageAckListener() {
        return new SimpleMessageAckListener();
    }

}
