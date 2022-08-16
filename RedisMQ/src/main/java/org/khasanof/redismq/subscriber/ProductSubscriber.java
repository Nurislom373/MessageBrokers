package org.khasanof.redismq.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class ProductSubscriber implements MessageListener {

    Logger logger = LoggerFactory.getLogger(ProductSubscriber.class);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        logger.info("subscribe event {}", message);
    }
}
