## Most popular message brokers integration with spring boot 2.7.2 version

1. Apache ActiveMQ
2. Apache Kafka
3. RabbitMQ
4. Redis Broker

# What is Message Broker

message broker is an intermediary program that applications and services use to communicate with each other to exchange information. Message brokers can be used to validate, store, route and deliver messages to the required destinations. Not only applications can communicate information, even if they are implemented in different programming languages. Since message brokers act as an intermediary program, the sender has no idea how many recipients there are if they are online.

Most importantly brokers ensure that recipients receive the message even if they are not online or active. (It’s kinda like email. You don’t have to be online but you still receive the message).

Message brokers use a message queue for this and it’s saved in memory or a hard disk. They are used to store messages and deliver them. (I hope you are familiar with the data structure “Queue”. This is the same as this, First In First Out).

Message queue stores messages in the exact order they are received and sends to the receiver in the same order. If somehow a message was unable to be delivered (problem with the network) the message will be rescheduled for later in the queue. Also in a message queue, messages are stored in the exact order in which they were transmitted and remain in the queue until receipt is confirmed.
