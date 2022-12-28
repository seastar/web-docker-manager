package com.github.seastar.wdm.data.redis;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
public interface RedisWorker<V> {
    RedisTemplate<String, V> holdTemplate();
    String group();
}
