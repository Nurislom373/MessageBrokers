package org.khasanof.producer;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

@SpringBootApplication
@EnableRabbit
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
}

@RestController
@RequestMapping(value = "/transfer")
@RequiredArgsConstructor
class ProducerController {
    private final TransactionService service;

    @PostMapping
    public Transaction transaction(@RequestBody TransactionCreateVO vo) {
        return service.create(vo);
    }
}


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String pan;
    @Formula("amount > 0")
    private BigInteger amount;
    @CreatedDate
    @CreationTimestamp
    @Column(columnDefinition = "timestamp default current_timestamp")
    private Timestamp createdAt;

}


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class TransactionCreateVO {
    private String pan;
    private BigInteger amount;
}

@Repository
interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}

@Service
@RequiredArgsConstructor
class TransactionService {
    private final TransactionRepository repository;
    private final RabbitMQService rabbitMQService;

    public Transaction create(TransactionCreateVO vo) {
        Transaction transaction = Transaction.builder()
                .pan(vo.getPan())
                .amount(vo.getAmount())
                .build();
        transaction = repository.save(transaction);
        rabbitMQService.send(transaction);
        return transaction;
    }
}

@Configuration
class RabbitConfig {
    public static final String QUEUE = "org_queue";
    public static final String EXCHANGE = "org_exchange";
    public static final String ROUTING_KEY = "org_routing_key";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}

@Service
record RabbitMQService(RabbitTemplate rabbitTemplate) {
    public void send(Object message) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, message);
    }
}
