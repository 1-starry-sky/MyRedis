package com.example.redis;

import com.example.websocket.WebSocket;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisMessageListener implements MessageListener {
    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private WebSocket webSocket;  //进行将消息发生的客户端

    @Override
    public void onMessage(Message message, byte[] pattern) {

        // 接收的topic
        log.info("channel:" + new String(pattern));


        //序列化对象（特别注意：发布的时候需要设置序列化；订阅方也需要设置序列化）
        // 覆写(overwrite) 给定 key 所储存的字符串值，从偏移量 offset 开始
//        MessageDto messageDto = (MessageDto) redisTemplate.getValueSerializer().deserialize(message.getBody());

        redisTemplate.setValueSerializer(new StringRedisSerializer());
       String   messageS= (String) redisTemplate.getValueSerializer().deserialize(message.getBody());
//        log.info(messageDto.getData()+","+messageDto.getContent());
        log.info(messageS);
        webSocket.pushMessage(messageS);
//        log.info("message:" + message.getBody().toString());   //  打印message:[B@44e83f18
//        log.info("message:" +messageS);   //  打印message:[B@44e83f18
    }
}


