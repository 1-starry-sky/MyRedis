package com.example.websocket;

import cn.hutool.core.util.ObjectUtil;

import com.example.redis.MessageDto;
import com.example.utils.BaseMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 监听消息(采用redis发布订阅方式发送消息)
 * @author: jeecg-boot
 */
@Slf4j
@Component
public class SocketHandler {

    @Autowired
    private WebSocket webSocket;

//    @Override
//    public void onMessage(BaseMap map) {
//        log.info("【SocketHandler消息】Redis Listerer:" + map.toString());
//
//        String userId = map.get("userId");
//        String message = map.get("message");
//        if (ObjectUtil.isNotEmpty(userId)) {
//            webSocket.pushMessage(userId, message);
//        } else {
//            webSocket.pushMessage(message);
//        }
//
//    }


}