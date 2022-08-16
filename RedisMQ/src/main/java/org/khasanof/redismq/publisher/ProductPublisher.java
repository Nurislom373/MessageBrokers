package org.khasanof.redismq.publisher;

import org.khasanof.redismq.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductPublisher {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ChannelTopic channelTopic;

    @PostMapping(value = "/publish")
    public String publish(@RequestBody Product product) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), product.toString());
        return "event publish!";
    }
}
