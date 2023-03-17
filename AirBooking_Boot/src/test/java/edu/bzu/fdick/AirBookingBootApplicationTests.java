package edu.bzu.fdick;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class AirBookingBootApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void RedisTest() {


        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //存在code文件下
        ops.set("code:"+"fdick", "11", 60, TimeUnit.SECONDS);
        System.out.println(ops.get("code:"+"fdick"));
    }
    @Test
    void RedisTest2() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String value= ops.get("code:"+"fdick");
        System.out.println(value);
    }

}
