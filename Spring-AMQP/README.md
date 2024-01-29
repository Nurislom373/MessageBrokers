# Spring AMQP

The Spring AMQP project applies core Spring concepts to the development of AMQP-based messaging solutions. We provide a 
“template” as a high-level abstraction for sending and receiving messages. We also provide support for message-driven 
POJOs. These libraries facilitate management of AMQP resources while promoting the use of dependency injection and 
declarative configuration. In all of these cases, you can see similarities to the JMS support in the Spring Framework.

## Message-Based Communication

Xabarlar - bu ilovalar o'rtasidagi muloqot qilish texnikasi. U sinxron request responsega asoslangan arxitektura o'rniga
asinxron xabar uzatishga tayanadi. Xabarlarni ishlab chiquvchilar(Producer) va iste'molchilari(Consumer) message brokeri
deb nomlanuvchi oraliq xabar almashish qatlami tomonidan ajratiladi. Message Broker xabarlarni doimiy saqlash, xabarlarni
filterlash va xabarlarni o'zgartirish kabi xususiyatlarni taqdim etadi.

Java-da yozilgan ilovalar o'rtasida xabar almashishda odatda JMS (Java Message Service) API ishlatiladi. Turli ishlab 
chiqaruvchilar va platformalar oʻrtasida oʻzaro ishlash uchun biz JMS mijozlari va brokerlaridan foydalana olmaymiz. 
Bu yerda AMQP yordam beradi.

## How Amqp Is Different from Jms

[Linkedin Post](https://www.linkedin.com/pulse/jms-vs-amqp-eran-shaham/)

## AMQP Entities

Qisqacha aytganda, AMQP Exchanges, Queues va Bindings lardan iborat.

- `Exchanges` - pochta bo'limlari yoki pochta qutilariga o'xshaydi va clientlar AMQP Exchange ga xabarlani yuborishadi.
  To'rtta Exchange turlari mavjud.
  - `Direct` - routing_key Binding_key ga mos kelgan queuelargagina xabarlarni yuboradi.
  - `Fanout` - Xabarlarni unga ulangan barcha queuelarga yuboradi.
  - `Topic` - routing keyni patternga moslashtirish orqali xabarlarni bir nechta queuelarga yuboradi.
  - `Headers` - Xabarlar headerlar asosida queuelarga yo'naltiriladi.
- `Queue` - lar routing key orqali Exchangelarga bog'lanadi.
- `Message` - lar routing key orqali Exchange yuboriladi. Keyin Exchange xabarlarni nusxalarini queuelarga tarqatadi.

# Spring AMQP

Spring AMQP 2ta moduldan iborat: spring-amqp va spring-rabbit. Bu modullar birgalikda quyidagilar uchun abstracksiyalarni
taqdim etadi:

- `AMQP entities` – we create entities with the Message, Queue, Binding, and Exchange classes
- `Connection Management` – we connect to our RabbitMQ broker by using a CachingConnectionFactory
- `Message Publishing` – we use a RabbitTemplate to send messages
- `Message Consumption` – we use a @RabbitListener to read messages from a queue

# AmqpTemplate

Spring Framework va tegishli loyihalar tomonidan taqdim etilgan boshqa ko'plab yuqori darajadagi abstractsiyalar
singari, Spring AMQP da asosiy rol o'ynaydigan "template" ni taqdim qiladi. Asosiy operatsiyalarni belgilovchi interface
`AmqpTemplate` deb ataladi. Ushbu operatsiyalar xabarlarni jo'natish va qabul qilishning umumiy xatti-harakatlarni qamrab
oladi.

# Send Messages

Xabar yuborishda siz quyidagi usullardan birini qo'llashingiz mumkin:

```java
void send(Message message) throws AmqpException;

void send(String routingKey, Message message) throws AmqpException;

void send(String exchange, String routingKey, Message message) throws AmqpException;
```

Quyidagi misolda xabar yuborish uchun `send` methodidan qanday foydalanish ko'rsatilagan.

```java
amqpTemplate.send("marketData.topic", "quotes.nasdaq.THING1", new Message("12.34".getBytes(), someProperties));
```

Agar siz ushbu `AmqpTemplate` dan ko'p yoki doim bir xil almashinuvga xabar yuborish uchun foydalanmoqchi bo'lsangiz.
`AmqpTemplate.setExchange()` methodidan foydalanib exchange nomini o'rnatishingiz mumkin.

```java
amqpTemplate.send("marketData.topic", "quotes.nasdaq.THING1", new Message("12.34".getBytes(), someProperties));
```

Agar `exchange` va `routingKey` larni Templatega o'rnagan bo'lsangiz, siz faqat message yuborishingiz mumkin.

```java
amqpTemplate.setExchange("marketData.topic");
amqpTemplate.setRoutingKey("quotes.nasdaq.FOO");
amqpTemplate.send(new Message("12.34".getBytes(), someProperties));
```

Exchange va RoutingKey xususiyatlari default holatda bo'sh bo'ladi.

### Message Builder API

1.3 versiyadan boshlab xabarlarni yaratish uchun `MessageBuilder` va `MessagePropertiesBuilder` API lari qo'shildi.

```java
Message message = MessageBuilder.withBody("foo".getBytes())
    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
    .setMessageId("123")
    .setHeader("bar", "baz")
    .build();
```

```java
MessageProperties props = MessagePropertiesBuilder.newInstance()
    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
    .setMessageId("123")
    .setHeader("bar", "baz")
    .build();
Message message = MessageBuilder.withBody("foo".getBytes())
    .andProperties(props)
    .build();
```

# Receiving Messages

Message reception is always a little more complicated than sending. There are two ways to receive a Message. The simpler 
option is to poll for one Message at a time with a polling method call. The more complicated yet more common approach is
to register a listener that receives Messages on-demand, asynchronously. 

---

Xabarlarni qabul qilish har doim yuborishdan biroz murakkabroq. `Message` ni olishning 2ta yo'li mavjud. Eng oddiy usuli
`AmqpTemplate` receive nomi bilan boshlangan methodlaridan foydalanish. Ushbu methodlar bir vaqtning o'zida faqat bitta 
xabarni olish imkonini beradi. Ikkinchi usuli esa murakkab, ammo keng tarqalgan yondashuvlar bu `Messages` talab bo'yicha,
asinxron qabul qiluvchini ro'yxatdan o'tkazishdir. 

## Polling Consumer

`AmqpTemplate` ni xabarlarni qabul qilish uchun ham ishlatishimiz mumkin. Xabarni qabul qilish method chaqirganimizda 
agar xabar mavjud bo'lmasa `null` qaytariladi aks bo'lsa xabarni o'zi. Hech qanday blokirovka yo'q. 1.5 versiyadan 
boshlab siz `receiveTimeout` ni millisekundlarda o'rnatishingiz va qabul qilish methodlarini shu qadar uzoq vaqt davomida
blokirovka qilishingiz mumkin. 

4ta oddiy `receive` methodlari mavjud. 

```java
Message receive() throws AmqpException;

Message receive(String queueName) throws AmqpException;

Message receive(long timeoutMillis) throws AmqpException;

Message receive(String queueName, long timeoutMillis) throws AmqpException;
```

Xabarlarni jo'natishda bo'lgani kabi, xabarlarni qabul qilishda ham Message larni o'rniga POJO qabul qilishning ba'zi qulay
methodlari mavjud. `MessageConverter` tomonidan yaratilgan object qaytariladi. Quyidagi ro'yxatda ushbu methodlar ko'rsatilgan.

```java
Object receiveAndConvert() throws AmqpException;

Object receiveAndConvert(String queueName) throws AmqpException;

Object receiveAndConvert(long timeoutMillis) throws AmqpException;

Object receiveAndConvert(String queueName, long timeoutMillis) throws AmqpException;
```

Kichik eslatma ushbu methodlardan foydalanish uchun `AmqpTemplate` yaratish jarayonida `MessageConverter` ni `AmqpTemplate`
ga o'rnatishingiz kerak aks holda object convert qilish jarayonida exception sodir bo'ladi. Quyidagi misolni ko'rishingiz
mumkin.

```java
@Bean
public RabbitTemplate rabbitTemplate() {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
    rabbitTemplate.setMessageConverter(messageConverter());
    return rabbitTemplate;
}

@Bean
public AbstractJackson2MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
}
```

## Annotation-Driven Listener Endpoints

Xabarlarni asinxron qabul qilishning eng oson yo'li annotated listener endpoint foydalanishdir. Xulosa qilib aytganda,
bu sizga boshqariladigan bean methodini Rabbit Listeneri sifatida ko'rsatishga imkon beradi. Quyidagi misol `@RabbitListener`
annotationdan qanday foydalanishni ko'rsatadi.

```java
@Component
public class MyService {

    @RabbitListener(queues = "myQueue")
    public void processOrder(String data) {
        ...
    }

}
```

Oldingi misol g'oyasi shundan iboratki, har safar queueda xabar mavjud bo'lganda `processOrder` method chaqiriladi.
Har bir annotatsiya qo'yilgan method sahna ortida message listener containerni yaratadi. `RabbitListenerContainerFactory`

## Reference

- [AMQP Concepts](https://www.rabbitmq.com/tutorials/amqp-concepts.html)
- [Spring AMQP Doc](https://docs.spring.io/spring-amqp/reference/index.html)