package org.khasanof.mqtt.basic;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Nurislom
 * @see org.khasanof.mqtt
 * @since 5/3/2026 4:58 PM
 */
public class MqttSubscriber {

    public static void main(String[] args) throws MqttException {
        String broker = "tcp://localhost:1883";
        String clientId = "subscriber-client";

        try (MqttClient client = new MqttClient(broker, clientId)) {
            client.connect();

            client.subscribe("test/topic", (topic, message) -> {
                System.out.println("Received: " + new String(message.getPayload()));
            });

            System.out.println("Subscribed...");
        }
    }
}
