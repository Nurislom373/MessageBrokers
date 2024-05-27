package org.khasanof.producer;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.khasanof.KafkaAdminFactory;
import org.khasanof.KafkaTopicCreator;

import java.util.Properties;
import java.util.Random;

import static org.khasanof.GlobalConstants.BOOTSTRAP_SERVER;

/**
 * @author Nurislom
 * @see org.khasanof.producer
 * @since 5/27/2024 11:22 AM
 */
public class WordCountProducer {

    public static final String TOPIC_NAME = "word-input-topic-v2";
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        try (KafkaProducer<String, Long> producer = createProducer(); Admin admin = KafkaAdminFactory.create()) {
            KafkaTopicCreator.createTopic(admin, TOPIC_NAME);
            for (int i = 0; i < 20; i++) {
                Thread.sleep(500);
                long nextMessage = random.nextLong(10, 1000);
                producer.send(new ProducerRecord<>(TOPIC_NAME, "Khasanof", nextMessage));
                System.out.println("Send message: " + nextMessage);
            }
        }
    }

    public static KafkaProducer<String, Long> createProducer() {
        return new KafkaProducer<>(properties());
    }

    private static Properties properties() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        return properties;
    }
}
