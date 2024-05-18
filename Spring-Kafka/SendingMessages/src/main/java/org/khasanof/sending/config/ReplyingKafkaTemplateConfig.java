package org.khasanof.sending.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.khasanof.Notification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

/**
 * @author Nurislom
 * @see org.khasanof.sending.config
 * @since 5/18/2024 5:08 AM
 */
@Configuration
public class ReplyingKafkaTemplateConfig {

    @Bean
    public ReplyingKafkaTemplate<String, Object, Notification> replyingKafkaTemplate(ProducerFactory<String, Object> pf,
                                                                                     ConcurrentMessageListenerContainer<String, Notification> repliesContainer) {
        return new ReplyingKafkaTemplate<>(pf, repliesContainer);
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, Notification> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, Notification> containerFactory) {

        ConcurrentMessageListenerContainer<String, Notification> repliesContainer =
                containerFactory.createContainer("kReplies");
        repliesContainer.getContainerProperties().setGroupId("repliesGroup");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

    @Bean
    public NewTopic kRequests() {
        return TopicBuilder.name("kRequests")
                .partitions(10)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic kReplies() {
        return TopicBuilder.name("kReplies")
                .partitions(10)
                .replicas(2)
                .build();
    }
}
