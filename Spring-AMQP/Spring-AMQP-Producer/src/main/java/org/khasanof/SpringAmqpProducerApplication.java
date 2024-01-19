package org.khasanof;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringAmqpProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAmqpProducerApplication.class, args);
    }

}
