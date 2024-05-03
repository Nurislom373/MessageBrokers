package org.khasanof.springkafkaproducer.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nurislom
 * @see org.khasanof.springkafkaproducer
 * @since 5/2/2024 9:23 PM
 */
@EnableKafka
@Configuration
public class KafkaAdminConfig {

    public static final String BOOTSTRAP_SERVER = "localhost:9092";

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        return new KafkaAdmin(configs);
    }
}
