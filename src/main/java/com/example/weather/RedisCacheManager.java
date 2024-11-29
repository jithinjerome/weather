package com.example.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheManager {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private String getFromCache(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void saveToCache(String key, String value, int expirationInSeconds){
        redisTemplate.opsForValue().set(key,value,expirationInSeconds, TimeUnit.SECONDS);
    }
}
