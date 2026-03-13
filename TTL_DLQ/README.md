## TTL + DLQ (RabbitMQ, Spring Boot)

Данный пример демонстрирует, как с помощью **TTL** (Time-To-Live) сообщений и **DLQ** (Dead-Letter Queue) реализовать отложенную обработку сообщений в очереди.  
Идея в том, что сообщение сначала попадает во «временную» очередь с TTL, а после истечения времени автоматически перекидывается в основную очередь, откуда уже обрабатывается вашим потребителем.

---

### Основные сущности

- **Delay exchange (`delay.exchange`)**:  
  Прямой (Direct) обменник, в который продюсер отправляет сообщения с указанием TTL.

- **Delay queue (`delay.queue`)**:  
  Очередь, куда приходят сообщения с TTL.  
  У неё настроены параметры:
  - `x-dead-letter-exchange = dlx.exchange` – обменник, куда сообщение перекидывается, когда «умирает» (истекает TTL);
  - `x-dead-letter-routing-key = webhook` – routing key, по которому сообщение попадает в DLQ/основную очередь.

- **DLX exchange (`dlx.exchange`)**:  
  Dead-letter обменник, принимающий «просроченные» сообщения из `delay.queue`.

- **Webhook queue (`webhook.queue`)**:  
  Основная очередь, из которой читает потребитель (`WebhookConsumer`). Сюда попадает сообщение только после истечения TTL в `delay.queue`.

---

### Конфигурация RabbitMQ

Все ключевые настройки находятся в классе `RabbitConfig`:

```20:67:TTL_DLQ/src/main/java/org/khasanof/ttl_dlq/config/RabbitConfig.java
@Configuration
public class RabbitConfig {

    public static final String DELAY_EXCHANGE = "delay.exchange";
    public static final String DLX_EXCHANGE = "dlx.exchange";

    public static final String DELAY_QUEUE = "delay.queue";
    public static final String WEBHOOK_QUEUE = "webhook.queue";

    public static final String ROUTING_KEY = "webhook";

    @Bean
    DirectExchange delayExchange() { ... }

    @Bean
    DirectExchange deadLetterExchange() { ... }

    @Bean
    public Queue delayQueue() { ... }   // очередь с DLX-настройками

    @Bean
    public Queue webhookQueue() { ... } // основная очередь

    @Bean
    public Binding delayBinding() { ... }

    @Bean
    public Binding webhookBinding() { ... }
}
```

Самое важное здесь — `delayQueue()`:

- к очереди привязан **DLX** (`x-dead-letter-exchange`);
- указан **routing key** для переотправки умерших сообщений (`x-dead-letter-routing-key`).

Благодаря этому после истечения TTL сообщение «выпадает» из `delay.queue` и попадает в `dlx.exchange`, который уже маршрутизирует его в `webhook.queue`.

---

### Продюсер: отправка сообщения с задержкой

Отправка выполняется через `WebhookProducer`:

```12:33:TTL_DLQ/src/main/java/org/khasanof/ttl_dlq/publisher/WebhookProducer.java
@Service
public class WebhookProducer {

    private final RabbitTemplate rabbitTemplate;

    public WebhookProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendWithDelay(String payload, long delayMillis) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.DELAY_EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                payload,
                message -> {
                    message.getMessageProperties()
                            .setExpiration(String.valueOf(delayMillis));
                    return message;
                }
        );
    }
}
```

- **`setExpiration(delayMillis)`** — устанавливает TTL для **конкретного сообщения** (в миллисекундах).  
- Сообщение отправляется в `delay.exchange`, попадает в `delay.queue` и «лежит» там до истечения TTL.

---

### Контроллер: HTTP-эндпоинт для теста

Для удобной отправки webhook-сообщений есть REST-контроллер `WebhookController`:

```13:33:TTL_DLQ/src/main/java/org/khasanof/ttl_dlq/controller/WebhookController.java
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
```

- **`GET /webhook?payload=...&delay=...`** – отправляет сообщение `payload` с задержкой `delay` (в миллисекундах).
- Для наглядности в лог выводится текущее время отправки.

---

### Консьюмер: обработка отложенных сообщений

Когда TTL истекает, сообщение попадает в `webhook.queue`, откуда его читает `WebhookConsumer`:

```14:22:TTL_DLQ/src/main/java/org/khasanof/ttl_dlq/listener/WebhookConsumer.java
@Component
public class WebhookConsumer {

    @RabbitListener(queues = RabbitConfig.WEBHOOK_QUEUE)
    public void consume(String payload) {
        System.out.println("Webhook received: " + payload);
        System.out.println("Time: " + Instant.now());
    }
}
```

По логам видно, что:

- время отправки (`WebhookController`) и
- время получения (`WebhookConsumer`)

отличаются примерно на величину указанного TTL.

---

### Логический поток (чертёж / схема)

Ниже упрощённая текстовая диаграмма потока сообщения:

- **1. HTTP-запрос**  
  Клиент вызывает:  
  `GET /webhook?payload=test&delay=10000`

- **2. Контроллер**  
  `WebhookController` → вызывает `WebhookProducer.sendWithDelay("test", 10000)`.

- **3. Отправка в delay.exchange**  
  `RabbitTemplate.convertAndSend("delay.exchange", "webhook", "test", TTL=10000ms)`.

- **4. Delay Queue**  
  Сообщение попадает в `delay.queue` и хранится там 10 секунд.

- **5. Истечение TTL**  
  По истечении TTL сообщение становится «мёртвым» и:
  - переотправляется в `dlx.exchange` (dead-letter exchange),
  - с routing key `webhook`.

- **6. Маршрутизация в webhook.queue**  
  `dlx.exchange` маршрутизирует сообщение в `webhook.queue`.

- **7. Консьюмер**  
  `WebhookConsumer` получает сообщение из `webhook.queue` и выводит его в лог вместе с текущим временем.

Таким образом, очередь с TTL (`delay.queue`) используется как **буфер ожидания**, а DLQ-механизм превращается в удобный способ реализовать **отложенную доставку**.

---

### Как запустить проект

- **Требования**:
  - установленный и запущенный **RabbitMQ** (по умолчанию `localhost:5672`, `guest/guest`);
  - JDK **17+**;
  - Maven.

- **Шаги**:
  1. Собрать и запустить Spring Boot приложение:
     ```bash
     mvn spring-boot:run
     ```
  2. Отправить тестовый запрос (например, задержка 10 секунд):
     ```bash
     curl "http://localhost:8080/webhook?payload=hello&delay=10000"
     ```
  3. В логах приложения вы увидите:
     - время отправки (из контроллера);
     - через ~10 секунд — время получения (из консьюмера).

---

### Краткое резюме

- **TTL** на уровне сообщения (`setExpiration`) позволяет управлять временем его «жизни» в delay-очереди.
- **DLQ (dead-letter queue)** используется не только для ошибок, но и как **механизм отложенной доставки**.
- Такая комбинация TTL + DLQ позволяет элегантно реализовать «подержать сообщение в очереди N миллисекунд, а потом уже обработать».