package com.codeit.redis.keystore;

import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Optional;

public class RedisKeyStore<V> implements KeyStore<String, V> {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String prefix;

    public RedisKeyStore(RedisTemplate<String, Object> redisTemplate, String prefix) {
        this.redisTemplate = redisTemplate;
        this.prefix = prefix;
    }

    private String k(String key) {
        return prefix + ":" + key;
    }

    @Override
    public void put(String key, V value, Duration ttl) {
        if (ttl == null) {
            redisTemplate.opsForValue().set(k(key), value);
        } else {
            redisTemplate.opsForValue().set(k(key), value, ttl);
        }
    }

    @Override
    public Optional<V> get(String key) {
        Object value = redisTemplate.opsForValue().get(k(key));
        return Optional.ofNullable((V) value);
    }

    @Override
    public boolean delete(String key) {
        return redisTemplate.delete(k(key));
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(k(key));
    }
}
