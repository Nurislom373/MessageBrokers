# 1. Overview

Apache Kafka is a distributed and fault-tolerant stream processing system.

In this tutorial, we'll cover Spring support for Kafka and the level of abstractions it provides over native Kafka Java client APIs.

Spring Kafka brings the simple and typical Spring template programming model with a KafkaTemplate and Message-driven POJOs via @KafkaListener annotation.

# 2. Installation and Setup
To download and install Kafka, please refer to the official guide here.

We also need to add the spring-kafka dependency to our pom.xml:

```maven
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
    <version>2.7.2</version>
</dependency>
```
The latest version of this artifact can be found here.

Our example application will be a Spring Boot application.

This article assumes that the server is started using the default configuration and that no server ports are changed.

# 3. Apache Kafka Core Concepts

We will discuss following Apache Kafka core concepts:

1. Kafka Cluster
2. Kafka Broker
3. Kafka Producer
4. Kafka Consumer
5. Kafka Topic
6. Kafka Partitions
7. Kafka Offsets
8. Kafka Consumer Group

## 3.1 Kafka Cluster

Since Kafka is a distributed system, it acts as a cluster. A Kafka cluster consists of a set of brokers. A cluster has a minimum of 3 brokers.

The following diagram shows Kafka cluster with three Kafka brockers:

![windows](img/Screenshot%202022-05-05%20at%202.40.02%20PM.png)

## 3.2 Kafka Broker

The broker is the Kafka server. It's just a meaningful name given to the Kafka server. And this name makes sense as well because all that Kafka does is act as a message broker between producer and consumer.

The producer and consumer don't interact directly. They use the Kafka server as an agent or a broker to exchange messages.

The following diagram shows a Kafka broker, it acts as an agent or broker to exchange messages between Producer and Consumer:

![windows](img/Screenshot%202022-05-05%20at%202.42.18%20PM.png)

## 3.3 Kafka Producer

Producer is an application that sends messages. It does not send messages directly to the recipient. It sends messages only to the Kafka server.

The following diagram shows Producer sends messages directly to Kafka broker:

![windows](img/Screenshot%202022-05-05%20at%202.42.18%20PM.png)

## 3.4 Kafka Consumer

Consumer is an application that reads messages from the Kafka server.

If producers are sending data, they must be sending it to someone, right? The consumers are the recipients. But remember that the producers don't send data to a recipient address. They just send it to the Kafka server.

Anyone who is interested in that data can come forward and take it from the Kafka server. So, any application that requests data from a Kafka server is a consumer, and they can ask for data sent by any producer provided they have permission to read it.

The following diagram shows Producer sends messages directly to the Kafka broker and the Consumer consumes or reads messages from the Kafka broker:

![windows](img/Screenshot%202022-05-05%20at%202.42.18%20PM.png)