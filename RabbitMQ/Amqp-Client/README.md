# RabbitMQ

RabbitMQ - bu message broker. xabarlarni qabul qiluvchi va yo'naltiruvchi. RabbitMQ xabar almashish jargonlaridan 
foydalanadi.

Producer - xabar yuborishdan boshqa narsani anglatmaydi. Xabarlarni yuboruvchi daster Producer deb ataladi.
Queue - xabar yuborilgan so'ng xabarlarni queueda saqlanadi. 
Consumer - qabul qilish bilan o'xshash manoga ega. Consumer asosan xabarlarni olishni kutadigan dastur.

## Round Robin Dispatching

RabbitMq bizga biri nechta ishchilar bilan parallel ishlash imkoni beradi yani producerlar tomondan kelayotgan xabarlarni
qayta ishlashga ulgurmayotgan Consumer bo'lsa unga qo'shimcha yana bitta yoki bir nechta Consumer ishga tushirish orqali
parallel ishlashimiz mumkin. RabbitMQ har bir xabarni Consumerga yuboradi. ketma - ketlikda. O'rtacha har bir consumer
bir xil miqdorda oladi. 

## Message acknowledgment

RabbitMQ acknowledgment xabar tasdiqlashni bildiradi yani xabar qabul qilinishi bilan tasdiqlanadi, qayta ishlanayotgan 
bo'lsa ham bu degani xabar tasdiqlanishi bilan queuedan ochirib tashlanadi. Bunday holatda consumer o'chib qolsa xabar yoqoladi.

```java
channel.basicQos(1); // accept only one unack-ed message at a time (see below)

DeliverCallback deliverCallback = (consumerTag, delivery) -> {
  String message = new String(delivery.getBody(), "UTF-8");

  System.out.println(" [x] Received '" + message + "'");
  try {
    doWork(message);
  } finally {
    System.out.println(" [x] Done");
    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
  }
};
boolean autoAck = false;
channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
```

## Message Durability

RabbitMQ ishdan chiqanidi Queuelar va Xabarlarni unitadi Agar buni qilmaslikni aytmagunimizcha. Bunga ishonch hosil qilish 
uchun 2ta narsa talab qilinadi. Biz queue ni ham, xabarni ham durable qilib belgilashimiz kerak.

## Fair dispatch

RabbitMQ bir nechta consumer orqali xabarlarni qabul qilib ko'rgan bo'lsangiz korgan bolishingiz mumkin ularni 1ta doim
ishlaydi qolganlari esa yoq. Bu narsa RabbitMQ tasdiqlanmaganlarni soniga qaramaganligi uchun shunday boladi.

```java
int prefetchCount = 1; 
channel.basicQos(prefetchCount);
```

Buni oldini olish uchun `basicQos` methodidan foydalanishimiz mumkin. Tepadagi kodni ko'rgan bo'lsangiz ushbu sozlama
Consumerga 1tadan ortiq tasdiqlanmagan xabar bermasligini bildiradi yani bir vaqtning o'zida faqat bitta xabar. Xabar 
tasdiqlanmaguncha rabbitMq unga yangi xabarni bermaydi. Buning o'rniga band bo'lmagan boshqa consumerlarga xabar jo'natadi.