package org.khasanof.sending;

import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.khasanof.Notification;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Nurislom
 * @see org.khasanof.sending
 * @since 5/18/2024 5:24 AM
 */
@Component
public class ProduceReplyingMessages implements Runnable {

    private final ReplyingKafkaTemplate<String, Object, Notification> replyingKafkaTemplate;

    public ProduceReplyingMessages(ReplyingKafkaTemplate<String, Object, Notification> replyingKafkaTemplate) {
        this.replyingKafkaTemplate = replyingKafkaTemplate;
    }

    @Override
    @SneakyThrows
    public void run() {
        Notification notification = createNotification();

        ProducerRecord<String, Object> record = new ProducerRecord<>("kRequests", notification);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, "kReplies".getBytes()));
        RequestReplyFuture<String, Object, Notification> replyFuture = replyingKafkaTemplate.sendAndReceive(record);
        SendResult<String, Object> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
        System.out.println("Sent ok: " + sendResult.getRecordMetadata());
        ConsumerRecord<String, Notification> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
        System.out.println("Return value: " + consumerRecord.value());
    }

    private Notification createNotification() {
        Notification notification = new Notification();
        notification.setMessage("Hello Java");
        notification.setFrom("Me");
        notification.setTo("Developers");
        return notification;
    }
}
