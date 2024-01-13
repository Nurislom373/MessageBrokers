package org.khasanof;

import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 1/13/2024 4:27 PM
 */
public class DefaultDeliveryCallback implements DeliverCallback {

    @Override
    public void handle(String consumerTag, Delivery delivery) throws IOException {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.out.println(" [x] Received '" + message + "'");
    }

}
