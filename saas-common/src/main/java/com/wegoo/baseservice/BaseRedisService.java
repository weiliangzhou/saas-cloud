package com.wegoo.baseservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis服务
 */
@Component
public class BaseRedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setString(String key, Object data, Long timeout) {
        if (data instanceof String) {
            String value = (String) data;
            stringRedisTemplate.opsForValue().set(key, value);
        }
        if (timeout != null) {
            stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    public String getString(String key) {
        Object value = stringRedisTemplate.opsForValue().get(key);
        return value == null ? null : value.toString();
    }

    public Object getObject(String key) {
        Object value = stringRedisTemplate.opsForValue().get(key);
        return value;
    }

    public void delKey(String key) {
        stringRedisTemplate.delete(key);
    }

}
