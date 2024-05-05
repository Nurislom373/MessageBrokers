# Configuring Topics

If you define a KafkaAdmin bean in your application context, it can automatically add topics to the broker. To do so,
you can add a NewTopic @Bean for each topic to the application context. Version 2.3 introduced a new class TopicBuilder
to make creation of such beans more convenient. The following example shows how to do so: