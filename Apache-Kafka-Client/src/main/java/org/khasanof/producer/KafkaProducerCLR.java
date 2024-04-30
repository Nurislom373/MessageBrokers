package org.khasanof.producer;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.khasanof.KafkaAdminFactory;
import org.khasanof.KafkaTopicCreator;

import static org.khasanof.GlobalConstants.TOPIC_NAME;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 4/30/2024 6:36 PM
 */
public class KafkaProducerCLR {

    public static void main(String[] args) throws InterruptedException {
        try (KafkaProducer<String, String> producer = KafkaProducerFactory.createProducer(); Admin admin = KafkaAdminFactory.create()) {
            KafkaTopicCreator.createTopic(admin, TOPIC_NAME);
            for (int i = 0; i < 1000; i++) {
                Thread.sleep(1000);
                producer.send(new ProducerRecord<>(TOPIC_NAME, "Hello World : " + i));
                System.out.println("Send message!!!");
            }
        }
    }
}
