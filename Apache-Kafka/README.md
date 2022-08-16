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

