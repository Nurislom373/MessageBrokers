package org.khasanof.rabbitmqrpc;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Author: Nurislom
 * <br/>
 * Date: 21.05.2023
 * <br/>
 * Time: 17:26
 * <br/>
 * Package: org.khasanof.rabbitmqrpc
 */
@Component
public class Server {

    @RabbitListener(queues = "${queue.name}")
    public long factorial(int n) {
        System.out.println("Received request for " + n);
        long res = computeFactorial(n);
        System.out.println("Returned " + res);
        return res;
    }

    public long computeFactorial(int number) {
        long result = 1;

        for (int f = 2; f <= number; f++) {
            result *= f;
        }

        return result;
    }

}
