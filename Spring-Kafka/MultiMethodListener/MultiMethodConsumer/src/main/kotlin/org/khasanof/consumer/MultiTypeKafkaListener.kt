package org.khasanof.consumer

import org.khasanof.Notification
import org.khasanof.Person
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@KafkaListener(id = "multiGroup", topics = ["multitype"])
class MultiTypeKafkaListener {

    private var log: Logger = LoggerFactory.getLogger(this.javaClass)

    @KafkaHandler
    fun handleNotification(notification: Notification) {
        log.info("Notification received: {}", notification)
    }

    @KafkaHandler
    fun handlePerson(person: Person) {
        log.info("Person received: {}", person)
    }

    @KafkaHandler(isDefault = true)
    fun unknown(`object`: Any) {
        log.info("Any received: {}", `object`)
    }
}