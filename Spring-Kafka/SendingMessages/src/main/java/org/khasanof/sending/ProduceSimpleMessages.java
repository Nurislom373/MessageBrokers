package org.khasanof.sending;

import lombok.extern.slf4j.Slf4j;
import org.khasanof.Notification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static org.khasanof.sending.constants.KafkaConstants.NOTIFICATION_QUEUE;

/**
 * @author Nurislom
 * @see org.khasanof.sending
 * @since 5/18/2024 4:49 AM
 */
@Slf4j
@Component
public class ProduceSimpleMessages implements Runnable {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProduceSimpleMessages(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void run() {
        Notification notification = createNotification();
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(NOTIFICATION_QUEUE, notification);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("successfully produce!");
            }
        });
    }

    private Notification createNotification() {
        Notification notification = new Notification();
        notification.setMessage("Hello Java");
        notification.setFrom("Me");
        notification.setTo("Developers");
        return notification;
    }
}
