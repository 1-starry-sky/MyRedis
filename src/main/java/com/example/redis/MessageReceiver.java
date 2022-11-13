package com.example.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageReceiver {

    /**
     * 接收消息方法
     */
    public void receiverMessage(String channel,String message ) {
        // 接收的topic
        log.info("channel:" + channel);

        log.info("message:" + message);
//        System.out.println("MessageReceiver收到一条新消息：" + message);
    }

}
