package org.khasanof.message;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;

import java.nio.charset.StandardCharsets;

/**
 * @author Nurislom
 * @see org.khasanof.message
 * @since 1/20/2024 10:34 PM
 */
public class MessageBuilderSample {

    public Message message() {
        return MessageBuilder.withBody("758463".getBytes(StandardCharsets.UTF_8))
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setMessageId("57436")
                .setHeader("bar", "foo")
                .build();
    }

    public Message withProperties() {
        return MessageBuilder.withBody("foo".getBytes())
                .andProperties(getMessageProperties())
                .build();
    }

    private MessageProperties getMessageProperties() {
        return MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setMessageId("123")
                .setHeader("bar", "baz")
                .build();
    }

}
