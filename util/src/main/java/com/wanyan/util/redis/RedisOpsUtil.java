package com.wanyan.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis 操作工具
 */
@Component
public class RedisOpsUtil {

    private final RedisTemplate<String, String> redisTemplate;

    private RedisOpsUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 当键不存在时，设置键值
     * @param key 键
     * @param val 值
     * @param timeout 超时时长，单位：毫秒（ms）
     * @return Boolean
     */
    public Boolean setValueIfAbsent(String key, String val, long timeout) {
        if (timeout < 0) {
            return setValueIfAbsent(key, val);
        }
        return setValueTimeOutIfAbsent(key, val, timeout, TimeUnit.MILLISECONDS);
    }

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setValue(String key, String val) {
        redisTemplate.opsForValue().set(key, val);
    }

    private Boolean setValueIfAbsent(String key, String val) {
        return redisTemplate.opsForValue().setIfAbsent(key, val);
    }

    private Boolean setValueTimeOutIfAbsent(String key, String val, long timeout, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(key, val, timeout, timeUnit);
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}
