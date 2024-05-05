package org.khasanof.springkafkaproducer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.khasanof.KafkaConstants.DEFAULT_TOPIC;

/**
 * @author Nurislom
 * @see org.khasanof.springkafkaproducer.config
 * @since 5/2/2024 9:25 PM
 */
@Configuration
public class KafkaTopicsConfig {

    @Bean
    NewTopic newTopic() {
        return new NewTopic(DEFAULT_TOPIC, 3, (short) 0);
    }
}
