package com.loiterer.listener.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis 工具类
 *
 * @author cmt
 * @date 2020/10/22
 */
@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户缓存键的前缀
     */
    public static final String USER_KEY_PREFIX = "user:";

    /**
     * 设置给定 key 的值
     *
     * @param key 键
     * @param value 值
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取指定 key 对应的值
     *
     * @param key 键
     * @return 返回键值
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 判断缓存中是否存在该 key
     *
     * @param key 键
     * @return
     */
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

}
