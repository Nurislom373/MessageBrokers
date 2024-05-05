package org.khasanof.springkafkaproducer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nurislom
 * @see org.khasanof.springkafkaproducer
 * @since 5/5/2024 6:48 AM
 */
@RestController
@RequestMapping("/api/kafka")
public class KafkaSendMessageController {

    private final KafkaSendMessage kafkaSendMessage;

    public KafkaSendMessageController(KafkaSendMessage kafkaSendMessage) {
        this.kafkaSendMessage = kafkaSendMessage;
    }

    @RequestMapping(value = "/message/{message}")
    public ResponseEntity<Void> sendMessage(@PathVariable String message) {
        kafkaSendMessage.send(message);
        return ResponseEntity.ok()
                .build();
    }
}
