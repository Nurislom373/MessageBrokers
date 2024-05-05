package org.khasanof.consumer;

import lombok.extern.slf4j.Slf4j;
import org.khasanof.Notification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static org.khasanof.KafkaConstants.DEFAULT_TOPIC;

/**
 * @author Nurislom
 * @see org.khasanof.consumer
 * @since 5/5/2024 6:44 AM
 */
@Slf4j
@Component
public class SimpleKafkaListener {

    /**
     *
     * @param notification
     * @param partition
     */
    @KafkaListener(topics = {DEFAULT_TOPIC}, containerFactory = "notificationListenerContainerFactory")
    public void listen(Notification notification, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.info("Received message : {}, partition : {}", notification, partition);
    }
}
