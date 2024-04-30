package org.khasanof.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.List;

import static org.khasanof.GlobalConstants.TOPIC_NAME;

/**
 * @author Nurislom
 * @see org.khasanof.consumer
 * @since 4/30/2024 6:46 PM
 */
public class KafkaConsumerCLR {

    public static void main(String[] args) {
        try (KafkaConsumer<String, String> consumer = KafkaConsumerFactory.createConsumer()) {
            while (true) {
                consumer.subscribe(List.of(TOPIC_NAME));
                consumer.seekToBeginning(consumer.assignment()); // qayta qayta ma'lumotlarni olib turish uchun ishlatiladi.
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("record = " + record);
                }
            }
        }
    }
}
