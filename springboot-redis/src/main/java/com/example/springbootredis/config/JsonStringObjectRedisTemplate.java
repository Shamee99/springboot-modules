package com.example.springbootredis.config;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * //TODO
 *
 * @author 彭耀煌
 * @date 2024/6/27 10:35
 */
public class JsonStringObjectRedisTemplate extends RedisTemplate<String, Object> {

    public JsonStringObjectRedisTemplate() {
        setKeySerializer(RedisSerializer.string());
        setValueSerializer(RedisSerializer.json());
        setHashKeySerializer(RedisSerializer.string());
        setHashValueSerializer(RedisSerializer.json());
    }

    public JsonStringObjectRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }

}
