package org.khasanof.mqtt.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * @author Nurislom
 * @see org.khasanof.mqtt.spring
 * @since 5/3/2026 5:20 PM
 */
@Component
@RequiredArgsConstructor
public class MqttPublisherService implements CommandLineRunner {

    private final MessageChannel mqttOutboundChannel;

    @Override
    public void run(String... args) {
        mqttOutboundChannel.send(
                MessageBuilder.withPayload("Hello World Again")
                        .build()
        );
    }
}
