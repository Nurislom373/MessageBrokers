package org.khasanof.apachekafka.config;

import org.khasanof.apachekafka.transactional.TransactionalEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @KafkaListener(topics = "transaction", groupId = "0", containerFactory = "transactionKafkaListenerContainerFactory")
    public void consume(TransactionalEntity entity) {
        System.out.println("entity = " + entity);
    }
}
