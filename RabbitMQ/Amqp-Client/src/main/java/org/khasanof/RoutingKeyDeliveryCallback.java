package org.khasanof;

import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.nio.charset.StandardCharsets;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 1/14/2024 4:24 PM
 */
public class RoutingKeyDeliveryCallback implements DeliverCallback {

    @Override
    public void handle(String consumerTag, Delivery delivery) {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.out.println(" [x] Received '" +
                delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
    }
}
