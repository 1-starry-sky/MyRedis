package com.example.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@EnableScheduling//开启定时器功能
@Component
public class MessageSender {
    @Autowired
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 间隔2秒，通过stringRedisTemplate对象向redis消息队列chat频道发布消息
     */
//    @Scheduled(fixedDelay = 2000)
    public void sendMessage() {
        redisTemplate.convertAndSend("redisChat", String.valueOf(Math.random()));
    }

}
