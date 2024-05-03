package org.khasanof.springkafkaproducer.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.khasanof.Notification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nurislom
 * @see org.khasanof.springkafkaproducer.config
 * @since 5/2/2024 9:26 PM
 */
@Configuration
public class KafkaProducerConfig {

    /**
     *
     * @return
     */
    @Bean
    public KafkaTemplate<String, Notification> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     *
     * @return
     */
    @Bean
    public ProducerFactory<String, Notification> producerFactory() {
        return new DefaultKafkaProducerFactory<>(getProps());
    }

    private Map<String, Object> getProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaAdminConfig.BOOTSTRAP_SERVER);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }
}
