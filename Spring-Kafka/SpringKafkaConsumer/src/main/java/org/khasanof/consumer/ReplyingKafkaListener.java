package org.khasanof.consumer;

import lombok.extern.slf4j.Slf4j;
import org.khasanof.Notification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @author Nurislom
 * @see org.khasanof.consumer
 * @since 5/18/2024 5:27 AM
 */
@Slf4j
@Component
public class ReplyingKafkaListener {

    @SendTo
    @KafkaListener(topics = {"kRequests"}, containerFactory = "notificationListenerContainerFactory")
    public Notification listen(Notification notification, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.info("Received message : {}, partition : {}", notification, partition);
        return createNotification();
    }

    private Notification createNotification() {
        Notification notification = new Notification();
        notification.setMessage("Hello Developers Reply");
        notification.setFrom("Me");
        notification.setTo("Developers");
        return notification;
    }
}
