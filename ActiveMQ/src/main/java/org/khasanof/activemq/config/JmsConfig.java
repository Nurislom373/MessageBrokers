package org.khasanof.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import java.util.Objects;

@Configuration
@EnableJms
public class JmsConfig {

    @Value("${spring.activemq.broker-url}")
    public String BROKER_URL;
    @Value("${spring.activemq.user}")
    public String BROKER_USERNAME;
    @Value("${spring.activemq.password}")
    public String BROKER_PASSWORD;

//    @Bean
//    public ActiveMQConnectionFactory connectionFactory() {
//        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
//        activeMQConnectionFactory.setTrustAllPackages(true);
//        activeMQConnectionFactory.setBrokerURL(BROKER_URL);
//        activeMQConnectionFactory.setUserName(BROKER_USERNAME);
//        activeMQConnectionFactory.setPassword(BROKER_PASSWORD);
//        return activeMQConnectionFactory;
//    }
//
//    @Bean
//    public MessageConverter messageConverter() {
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setTargetType(MessageType.TEXT);
//        converter.setObjectMapper(objectMapper());
//        return converter;
//    }
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        return mapper;
//    }
//
//    @Bean
//    public JmsTemplate jmsTemplate() {
//        JmsTemplate jmsTemplate = new JmsTemplate();
//        jmsTemplate.setConnectionFactory(connectionFactory());
//        jmsTemplate.setMessageConverter(messageConverter());
//        jmsTemplate.setPubSubDomain(true);
//        jmsTemplate.setDestinationResolver(destinationResolver());
//        jmsTemplate.setDeliveryPersistent(true);
//        return jmsTemplate;
//    }
//
//    @Bean
//    public Queue queue() {
//        return new ActiveMQQueue("khasanof-queue");
//    }
//
//    @Bean
//    public DynamicDestinationResolver destinationResolver() {
//        return new DynamicDestinationResolver() {
//            @Override
//            public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain) throws JMSException {
//                if (destinationName.endsWith("Topic")) {
//                    pubSubDomain = true;
//                } else {
//                    pubSubDomain = false;
//                }
//                return super.resolveDestinationName(session, destinationName, pubSubDomain);
//            }
//        };
//    }

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("khasanof-queue");
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        if (Objects.isNull(BROKER_USERNAME) || Objects.isNull(BROKER_PASSWORD)) {
            activeMQConnectionFactory.setBrokerURL(BROKER_URL);
        } else {
            activeMQConnectionFactory.setPassword(BROKER_PASSWORD);
            activeMQConnectionFactory.setUserName(BROKER_USERNAME);
            activeMQConnectionFactory.setBrokerURL(BROKER_URL);
        }
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(activeMQConnectionFactory());
    }
}
