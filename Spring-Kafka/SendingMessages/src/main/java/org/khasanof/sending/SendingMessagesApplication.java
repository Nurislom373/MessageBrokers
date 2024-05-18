package org.khasanof.sending;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class SendingMessagesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendingMessagesApplication.class, args);
	}

}
