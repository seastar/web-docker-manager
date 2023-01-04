package com.github.seastar.wdm.data.redis;

import com.github.seastar.wdm.data.redis.key.KeyStore;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Supplier;

/**********************************
 * Date: 2022/12/28
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
public class ValueRedisWorker<V> extends RedisWorker<V> {

    private final ValueOperations<String, V> opsForValue;

    public ValueRedisWorker(RedisTemplate<String, V> holdTemplate, String group) {
        super(holdTemplate, group);
        this.opsForValue = holdTemplate().opsForValue();
    }

    private String k(KeyStore keyStore) {
        return "%s:%s".formatted(group(), keyStore.strAs());
    }

    public void set(KeyStore keyStore, V value, Duration expire) {
        opsForValue.set(k(keyStore), value, expire);
    }

    public Optional<V> get(KeyStore keyStore) {
        return Optional.ofNullable(opsForValue.get(k(keyStore)));
    }

    public void delete(KeyStore keyStore) {
        holdTemplate().delete(k(keyStore));
    }

    public Long incr(KeyStore keyStore, Supplier<Duration> expire) {
        var key = k(keyStore);
        var inc = opsForValue.increment(key);
        if (Optional.ofNullable(holdTemplate().getExpire(key)).orElse(-1L) < 0) {
            holdTemplate().expire(key, expire.get());
        }
        return inc;
    }
}
