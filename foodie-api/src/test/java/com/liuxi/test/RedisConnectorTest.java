package com.liuxi.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/28 4:19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisConnectorTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void redisSetValue() {

        redisTemplate.opsForValue().set("test", "哈哈哈");
    }
}
