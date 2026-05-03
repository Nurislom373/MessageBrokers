package org.khasanof.ttl_dlq.controller;

import org.khasanof.ttl_dlq.publisher.WebhookProducer;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

/**
 * @author Nurislom
 * @see org.khasanof.ttl_dlq
 * @since 3/13/26
 */
@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private final WebhookProducer producer;

    public WebhookController(WebhookProducer producer) {
        this.producer = producer;
    }

    @GetMapping
    public String sendWebhook(
            @RequestParam String payload,
            @RequestParam long delay
    ) {
        System.out.println("Time: " + Instant.now());
        System.out.println("Webhook send: " + payload);
        producer.sendWithDelay(payload, delay);
        return "sent";
    }
}
