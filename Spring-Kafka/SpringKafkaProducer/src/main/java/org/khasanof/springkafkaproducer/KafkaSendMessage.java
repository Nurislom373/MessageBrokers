package org.khasanof.springkafkaproducer;

import lombok.extern.slf4j.Slf4j;
import org.khasanof.Notification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static org.khasanof.KafkaConstants.DEFAULT_TOPIC;

/**
 * @author Nurislom
 * @see org.khasanof.springkafkaproducer
 * @since 5/2/2024 9:29 PM
 */
@Slf4j
@Service
public class KafkaSendMessage {

    private final KafkaTemplate<String, Notification> kafkaTemplate;

    public KafkaSendMessage(KafkaTemplate<String, Notification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setFrom("Khasanof");
        notification.setTo("John");

        send(notification);
    }

    public void send(Notification notification) {
        var future = kafkaTemplate.send(DEFAULT_TOPIC, notification);
        checkComplete(notification, future);
    }

    private void checkComplete(Notification notification, CompletableFuture<SendResult<String, Notification>> future) {
        future.whenComplete((sendResult, throwable) -> {
            if (Objects.nonNull(sendResult)) {
                log.info("Message [{}] delivered with offset {}", notification, sendResult.getRecordMetadata().offset());
                return;
            }
            log.warn("Unable to deliver message [{}]. {}", notification, throwable.getMessage());
        });
    }
}
