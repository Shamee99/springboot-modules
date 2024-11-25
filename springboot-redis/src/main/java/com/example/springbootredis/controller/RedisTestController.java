package com.example.springbootredis.controller;

import com.example.springbootredis.config.JsonStringObjectRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * //TODO
 *
 * @author 彭耀煌
 * @date 2024/6/27 10:03
 */
@RestController
@RequestMapping("redis")
public class RedisTestController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private StringRedisTemplate StringRedisTemplate;
    @Resource
    private JsonStringObjectRedisTemplate jsonStringObjectRedisTemplate;


    @GetMapping("/insert")
    public void insert() throws Exception {
        jsonStringObjectRedisTemplate.opsForValue().set("test_key2233", "测试是的数据");
    }

    @GetMapping("/get")
    public String get() throws Exception {
        return jsonStringObjectRedisTemplate.opsForValue().get("test_key2233").toString();
    }

}
