package com.example.websocket;

import com.alibaba.fastjson.JSONObject;
import com.example.Constant.WebsocketConst;
import com.example.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TestSocketController
 */
@RestController
@RequestMapping("/sys/socketTest")
public class TestSocketController {

    @Autowired
    private WebSocket webSocket;

    @PostMapping("/sendAll")
    public Result<String> sendAll(@RequestBody JSONObject jsonObject) {
    	String message = jsonObject.getString("message");
    	JSONObject obj = new JSONObject();
    	obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_TOPIC);
		obj.put(WebsocketConst.MSG_ID, "M0001");
		obj.put(WebsocketConst.MSG_TXT, message);
//    	webSocket.sendMessage(obj.toJSONString());//推送redis
			webSocket.pushMessage(obj.toJSONString());//直接推送给各个客户端
        return Result.success("群发！");
    }

    @PostMapping("/sendUser")
    public Result<String> sendUser(@RequestBody JSONObject jsonObject) {
    	String userId = jsonObject.getString("userId");
    	String message = jsonObject.getString("message");
    	JSONObject obj = new JSONObject();
    	obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_USER);
    	obj.put(WebsocketConst.MSG_USER_ID, userId);
		obj.put(WebsocketConst.MSG_ID, "M0001");
		obj.put(WebsocketConst.MSG_TXT, message);
        webSocket.sendMessage(userId, obj.toJSONString());//推送redis
        return Result.success("单发");
    }

}