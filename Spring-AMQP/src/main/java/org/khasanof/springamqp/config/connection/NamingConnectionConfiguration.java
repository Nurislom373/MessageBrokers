package org.khasanof.springamqp.config.connection;

import org.springframework.amqp.rabbit.connection.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nurislom
 * @see org.khasanof.springamqp
 * @since 1/19/2024 11:16 PM
 */
public class NamingConnectionConfiguration {

    @Bean
    public SimplePropertyValueConnectionNameStrategy connectionNameStrategy() {
        return new SimplePropertyValueConnectionNameStrategy("spring.application.name");
    }

    @Bean
    public ConnectionFactory rabbitConnectionFactory(ConnectionNameStrategy cns) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setConnectionNameStrategy(cns);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        final com.rabbitmq.client.ConnectionFactory cf = new com.rabbitmq.client.ConnectionFactory();
        cf.setHost("localhost");
        cf.setPort(5672);

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(cf);
        cachingConnectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);

        PooledChannelConnectionFactory pooledChannelConnectionFactory = new PooledChannelConnectionFactory(cf);

        final Map<Object, ConnectionFactory> connectionFactoryMap = new HashMap<>(2);
        connectionFactoryMap.put("true", cachingConnectionFactory);
        connectionFactoryMap.put("false", pooledChannelConnectionFactory);

        final AbstractRoutingConnectionFactory routingConnectionFactory = new SimpleRoutingConnectionFactory();
        routingConnectionFactory.setConsistentConfirmsReturns(false);
        routingConnectionFactory.setDefaultTargetConnectionFactory(pooledChannelConnectionFactory);
        routingConnectionFactory.setTargetConnectionFactories(connectionFactoryMap);

        final RabbitTemplate rabbitTemplate = new RabbitTemplate(routingConnectionFactory);

        final Expression sendExpression = new SpelExpressionParser().parseExpression(
                "messageProperties.headers['x-use-publisher-confirms'] ?: false");
        rabbitTemplate.setSendConnectionFactorySelectorExpression(sendExpression);
        return rabbitTemplate;
    }

}
