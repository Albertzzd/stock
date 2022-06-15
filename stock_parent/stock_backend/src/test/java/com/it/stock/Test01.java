package com.it.stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class Test01 {
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    public void aa(){
       redisTemplate.opsForValue().set("name","11");
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println("name = " + name);
    }
}
