package org.khasanof.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;
import java.util.UUID;

import static org.khasanof.GlobalConstants.BOOTSTRAP_SERVER;

/**
 * @author Nurislom
 * @see org.khasanof.consumer
 * @since 4/30/2024 6:44 PM
 */
public abstract class KafkaConsumerFactory {

    /**
     *
     * @return
     */
    public static KafkaConsumer<String, String> createConsumer() {
        return new KafkaConsumer<>(properties());
    }

    private static Properties properties() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        return properties;
    }
}
