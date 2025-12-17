package com.codeit.redis.keystore;

import java.time.Duration;
import java.util.Optional;

public interface KeyStore<K, V> {

    void put(K key, V value, Duration ttl);

    Optional<V> get(K key);

    boolean delete(K key);

    boolean exists(K key);
}
