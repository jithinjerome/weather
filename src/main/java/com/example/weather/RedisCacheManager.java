package com.example.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheManager {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String getFromCache(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void saveToCache(String key, String value, int expirationInSeconds){
        try{
            redisTemplate.opsForValue().set(key,value);
            redisTemplate.expire(key,expirationInSeconds, TimeUnit.SECONDS);
        }catch (Exception e){
            System.err.println("Redis error "+e.getMessage());
        }

    }
}
