package org.khasanof.mqtt.basic;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author Nurislom
 * @see org.khasanof.mqtt
 * @since 5/3/2026 4:59 PM
 */
public class MqttPublisher {

    public static void main(String[] args) throws Exception {
        String broker = "tcp://localhost:1883";
        String clientId = "publisher-client";

        for (int i = 0; i < 10; i++) {
            try (MqttClient client = new MqttClient(broker, clientId)) {
                client.connect();

                String payload = "Hello MQTT!";
                MqttMessage message = new MqttMessage(payload.getBytes());
                message.setQos(1);

                client.publish("test/topic", message);

                System.out.println("Message sent");
                client.disconnect();
            }
        }
    }
}
