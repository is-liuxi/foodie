package com.liuxi.util.common;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </P>
 * @author liu xi
 * @date 2022/4/28 5:25
 */
public class RedisUtils {

    private static StringRedisTemplate redisTemplate;

    static {
        redisTemplate = SpringApplicationContextHandler.getBean(StringRedisTemplate.class);
    }

    /**
     * 设置缓存
     * @param key
     * @param value
     */
    public static void set(String key, String value) {

        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置过期时间得 key
     * @param key
     * @param value
     * @param timeout
     */
    public static void set(String key, String value, long timeout) {

        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 是否存在当前 key
     * @param key
     * @return
     */
    public static Boolean hasKey(String key) {

        return redisTemplate.hasKey(key);
    }

    /**
     * 获取 value
     * @param key
     * @return
     */
    public static String get(String key) {

        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取 key 的过期时间
     * @param key
     * @return
     */
    public static Long getExpire(String key) {

        return redisTemplate.getExpire(key);
    }

    /**
     * 设置 key 过期时间
     * @param key
     * @param timeout
     */
    public static void expire(String key, long timeout) {

        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 自增 1
     * @param key
     */
    public static void incr(String key) {

        redisTemplate.opsForValue().increment(key);
    }

    /**
     * incrBy，自增指定值
     * @param key
     * @param delta
     */
    public static void incrBy(String key, long delta) {

        redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 根据指定模式返回 key
     * @param pattern
     * @return
     */
    public static Set<String> keys(String pattern) {

        return redisTemplate.keys(pattern);
    }

    /**
     * 删除指定 key
     * @param key
     */
    public static void del(String key) {

        redisTemplate.delete(key);
    }

    /**
     * Hash 类型添加缓存
     * @param key
     * @param field
     * @param value
     */
    public static void hSet(String key, String field, Object value) {

        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * Hash 类型获取值
     * @param key
     * @param field
     * @return
     */
    public static String hGet(String key, String field) {

        return (String) redisTemplate.opsForHash().get(key, field);
    }

    /**
     * Hash 类型获取值
     * @param key
     * @param fields
     * @return
     */
    public static String hGet(String key, Object... fields) {

        return (String) redisTemplate.opsForHash().get(key, fields);
    }

    /**
     * Hash 类型删除
     * @param key
     * @param fields
     */
    public static void hDel(String key, Object fields) {

        redisTemplate.opsForHash().delete(key, fields);
    }
}
