package org.khasanof.rabbitmqrpc;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: Nurislom
 * <br/>
 * Date: 21.05.2023
 * <br/>
 * Time: 17:28
 * <br/>
 * Package: org.khasanof.rabbitmqrpc
 */
@Component
public class Client {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    public void send(int n) {
        Long response = (Long) rabbitTemplate.convertSendAndReceive(directExchange.getName(), "roytuts", n);

        System.out.println("Got " + response + " ");
    }

}
