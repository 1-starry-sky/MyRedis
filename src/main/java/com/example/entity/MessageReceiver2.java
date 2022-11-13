package com.example.entity;

import org.springframework.stereotype.Component;

@Component
public class MessageReceiver2 {

    /**
     * 接收消息方法
     */
    public void receiverMessage(String message) {
        System.out.println("MessageReceiver2收到一条新消息：" + message);
    }
}

