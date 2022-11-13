package com.example.config;

import com.example.entity.MessageReceiver2;
import com.example.redis.MessageReceiver;
import com.example.redis.RedisMessageListener;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
/**
 * 自定义 Redis 模板
 * @date 2022-06-09 22:06
 */
@Configuration
public class RedisConfig {
    /**
     * 通过源码可以看出，SpringBoot 自动帮我们在容器中生成了一个 RedisTemplate 和一个 StringRedisTemplate。
     * 但是，这个 RedisTemplate 的泛型是 <Object, Object>，写代码不方便，需要写好多类型转换的代码。
     * 我们需要一个泛型为 <String, Object> 形式的 RedisTemplate。
     * 并且，这个 RedisTemplate 没有设置数据存在 Redis 时，key 及 value 的序列化方式。
     * 由 @ConditionalOnMissingBean 可以看出，如果 Spring 容器中有了自定义的 RedisTemplate 对象，自动配置的 RedisTemplate 不会实例化。
     * @param factory
     * @return
     */
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 定义泛型为 <String, Object> 的 RedisTemplate
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        // 设置连接工厂
        template.setConnectionFactory(factory);
        // 定义 Json 序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // Json 转换工具
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 定义 String 序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 订阅模式的监听器,  MessageListenerAdapter listenerAdapter, MessageListenerAdapter listenerAdapter2
     * 表示不同的监听器，可以自定义监听到了输出的方法，一般一个 MessageListenerAdapter自定义就够啦
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
//    @Bean
//    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
//                                            MessageListenerAdapter listenerAdapter,
//                                            RedisMessageListener listener,) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        //订阅了一个叫chat的通道
//        container.addMessageListener(listenerAdapter, new PatternTopic("redisChat"));
//        // 一个监听器订阅多个通道
////        container.addMessageListener(listenerAdapter, new PatternTopic("redisChat1"));
//        // 不同监听器订阅不同的通道
////        container.addMessageListener(listenerAdapter2, new PatternTopic("chat1"));
//
//
//        /**
//         * 设置序列化对象
//         * 特别注意：1. 发布的时候需要设置序列化；订阅方也需要设置序列化
//         *         2. 设置序列化对象必须放在[加入消息监听器]这一步后面，否则会导致接收器接收不到消息
//         */
//        Jackson2JsonRedisSerializer seria = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        seria.setObjectMapper(objectMapper);
//        container.setTopicSerializer(seria);
//
//        return container;
//    }
    /**
     * 订阅模式的监听器,  MessageListenerAdapter listenerAdapter, MessageListenerAdapter listenerAdapter2表示两个自定义的
     * RedisMessageListener listener表示自带的
     * 三个表示不同的监听器，可以监听到了输出的方法，
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter,MessageListenerAdapter listenerAdapter2,
                                            RedisMessageListener listener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅了一个叫chat的通道
        container.addMessageListener(listenerAdapter, new PatternTopic("redisChat"));
       // 一个监听器订阅多个通道
//        container.addMessageListener(listenerAdapter, new PatternTopic("redisChat1"));
        // 不同监听器订阅不同的通道
        container.addMessageListener(listenerAdapter2, new PatternTopic("chat"));
        // RedisMessageListener  ==> onMessage
          container.addMessageListener(listener, new PatternTopic("onMessage"));



        /**
         * 设置序列化对象
         * 特别注意：1. 发布的时候需要设置序列化；订阅方也需要设置序列化
         *         2. 设置序列化对象必须放在[加入消息监听器]这一步后面，否则会导致接收器接收不到消息
         */
        Jackson2JsonRedisSerializer seria = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        seria.setObjectMapper(objectMapper);
        container.setTopicSerializer(seria);

        return container;
    }

    /**
     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
     * 也有好几个重载方法，这边默认调用处理器的方法 叫OnMessage
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
        //给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
        //不填defaultListenerMethod默认调用handleMessage
//        MessageListenerAdapter receiveMessage = new MessageListenerAdapter( receiver, "receiveMessage");

        /**
         * 设置序列化对象
         * 特别注意：1. 发布的时候需要设置序列化；订阅方也需要设置序列化
         *         2. 设置序列化对象必须放在[加入消息监听器]这一步后面，否则会导致接收器接收不到消息
         */
//        Jackson2JsonRedisSerializer seria = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        seria.setObjectMapper(objectMapper);
//        receiveMessage.setSerializer(seria);
        return new MessageListenerAdapter(receiver, "receiverMessage");

//        return receiveMessage;


    }
    @Bean
    MessageListenerAdapter listenerAdapter2(MessageReceiver2 receiver) {
        return new MessageListenerAdapter(receiver, "receiverMessage");
    }



    /**
     * 读取内容的template
     */
//    @Bean
//    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
//        return new StringRedisTemplate(connectionFactory);
//    }

}