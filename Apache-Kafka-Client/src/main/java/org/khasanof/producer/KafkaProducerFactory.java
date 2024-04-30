package org.khasanof.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static org.khasanof.GlobalConstants.BOOTSTRAP_SERVER;

/**
 * @author Nurislom
 * @see org.khasanof.producer
 * @since 4/30/2024 6:37 PM
 */
public abstract class KafkaProducerFactory {

    /**
     *
     * @return
     */
    public static KafkaProducer<String, String> createProducer() {
        return new KafkaProducer<>(properties());
    }

    private static Properties properties() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
