package org.khasanof.configuringtopics

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.common.config.TopicConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

/**
 * @author Nurislom
 * @see org.khasanof.configuringtopics
 * @since 5/5/2024 9:34 AM
 */
@Configuration
class KafkaTopicsConfigurationKt {

    @Bean
    fun admin() = KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092"))

    @Bean
    fun topic1() =
            TopicBuilder.name("thing1")
                    .partitions(10)
                    .replicas(3)
                    .compact()
                    .build()

    @Bean
    fun topic2() =
            TopicBuilder.name("thing2")
                    .partitions(10)
                    .replicas(3)
                    .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
                    .build()

    @Bean
    fun topic3() =
            TopicBuilder.name("thing3")
                    .assignReplicas(0, listOf(0, 1))
                    .assignReplicas(1, listOf(1, 2))
                    .assignReplicas(2, listOf(2, 0))
                    .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
                    .build()

    /**
     * Starting with version 2.7, you can declare multiple NewTopics in a single KafkaAdmin.NewTopics bean definition:
     */
    @Bean
    fun topics456() = KafkaAdmin.NewTopics(
            TopicBuilder.name("defaultBoth")
                    .build(),
            TopicBuilder.name("defaultPart")
                    .replicas(1)
                    .build(),
            TopicBuilder.name("defaultRepl")
                    .partitions(3)
                    .build()
    )
}