package org.khasanof.apachekafka.transactional;

import lombok.RequiredArgsConstructor;
import org.khasanof.apachekafka.config.ProducerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionalService {

    private final ProducerService producerService;
    public void create(TransactionalEntity entity) {
        producerService.sendMessage(entity);
        System.out.println("Successfully Send Transaction");
    }
}
