package org.khasanof.consumer

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.khasanof.Notification
import org.khasanof.Person
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.converter.RecordMessageConverter
import org.springframework.kafka.support.converter.StringJsonMessageConverter
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration
class MultiMethodListenerConfiguration {

    private val bootstrapServers: String = "localhost:9092"

    /**
     * ConsumerFactory
     */
    @Bean
    fun multiTypeConsumerFactory(): ConsumerFactory<String, Any> {
        val props = HashMap<String, Any>()

        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java

        return DefaultKafkaConsumerFactory(props)
    }

    /**
     * RecordMessageConverter
     */
    @Bean
    fun multiTypeConverter(): RecordMessageConverter {
        val converter = StringJsonMessageConverter()
        val typeMapper = DefaultJackson2JavaTypeMapper()

        typeMapper.typePrecedence = Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID
        typeMapper.addTrustedPackages("*")

        val mappings = mutableMapOf<String, Class<*>>()
        mappings["person"] = Person::class.java
        mappings["notification"] = Notification::class.java

        typeMapper.idClassMapping = mappings
        converter.typeMapper = typeMapper

        return converter
    }

    /**
     * ConcurrentKafkaListenerContainerFactory
     */
    @Bean
    fun multiTypeKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Any>()
        factory.setRecordMessageConverter(multiTypeConverter())
        factory.consumerFactory = multiTypeConsumerFactory()
        return factory
    }
}