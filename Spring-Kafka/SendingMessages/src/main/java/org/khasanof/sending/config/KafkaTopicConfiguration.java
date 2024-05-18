package org.khasanof.sending.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.khasanof.sending.constants.KafkaConstants.NOTIFICATION_QUEUE;

/**
 * @author Nurislom
 * @see org.khasanof.sending.config
 * @since 5/18/2024 4:53 AM
 */
@Configuration
public class KafkaTopicConfiguration {

    /**
     *
     * @return
     */
    @Bean
    public NewTopic notification() {
        return new NewTopic(NOTIFICATION_QUEUE, 1, (short) 0);
    }
}
