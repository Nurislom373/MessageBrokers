package org.khasanof.apachekafka.config;

import lombok.RequiredArgsConstructor;
import org.khasanof.apachekafka.transactional.TransactionalEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private static final String TOPIC = "transaction";
    private final KafkaTemplate<String, Object> entityKafkaTemplate;

    public void sendMessage(TransactionalEntity entity) {
        this.entityKafkaTemplate.send(TOPIC, entity);
    }
}
