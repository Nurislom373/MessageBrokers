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

## Spring AMQP

Spring AMQP 2ta moduldan iborat: spring-amqp va spring-rabbit. Bu modullar birgalikda quyidagilar uchun abstracksiyalarni
taqdim etadi:

- `AMQP entities` – we create entities with the Message, Queue, Binding, and Exchange classes
- `Connection Management` – we connect to our RabbitMQ broker by using a CachingConnectionFactory
- `Message Publishing` – we use a RabbitTemplate to send messages
- `Message Consumption` – we use a @RabbitListener to read messages from a queue

## AmqpTemplate

Spring Framework va tegishli loyihalar tomonidan taqdim etilgan boshqa ko'plab yuqori darajadagi abstractsiyalar
singari, Spring AMQP da asosiy rol o'ynaydigan "template" ni taqdim qiladi. Asosiy operatsiyalarni belgilovchi interface
`AmqpTemplate` deb ataladi. Ushbu operatsiyalar xabarlarni jo'natish va qabul qilishning umumiy xatti-harakatlarni qamrab
oladi.

## Reference

- [AMQP Concepts](https://www.rabbitmq.com/tutorials/amqp-concepts.html)