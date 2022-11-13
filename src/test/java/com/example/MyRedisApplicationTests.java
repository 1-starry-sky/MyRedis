package com.example;

import com.example.config.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class MyRedisApplicationTests {

	@Resource
	private RedisTemplate redisTemplate;

	@Resource
	private RedisUtil redisUtil;
	@Test
	void contextLoads() {

//		redisTemplate.opsForValue().set("myKey", "myValue");
//		System.out.println(redisTemplate.opsForValue().get("myKey"));
	}

	/**
	 *消息发布者
	 */
	@Test
	public void sendMessage() {
		redisUtil.convertAndSend("redisChat", String.valueOf(Math.random()));

//		redisTemplate.convertAndSend("redisChat", String.valueOf(Math.random()));
	}


}
