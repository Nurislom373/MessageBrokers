package org.khasanof.consumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MultiMethodConsumerApplication

fun main(args: Array<String>) {
    runApplication<MultiMethodConsumerApplication>(*args)
}
