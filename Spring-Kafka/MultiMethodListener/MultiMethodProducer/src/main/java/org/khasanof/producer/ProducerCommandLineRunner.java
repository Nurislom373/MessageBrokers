package org.khasanof.producer;

import lombok.extern.slf4j.Slf4j;
import org.khasanof.Notification;
import org.khasanof.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Nurislom
 * @see org.khasanof.producer
 * @since 5/5/2024 8:02 AM
 */
@Slf4j
@Component
public class ProducerCommandLineRunner implements CommandLineRunner {

    public static final String MULTI_TYPE_TOPIC = "multitype";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProducerCommandLineRunner(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void run(String... args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            kafkaTemplate.send(MULTI_TYPE_TOPIC, new Person("M", "John"));
            kafkaTemplate.send(MULTI_TYPE_TOPIC, new Notification("khasanof", "abdulloh", "hello"));
            log.info("Sent messages!!!");
            Thread.sleep(1000);
        }
    }
}
