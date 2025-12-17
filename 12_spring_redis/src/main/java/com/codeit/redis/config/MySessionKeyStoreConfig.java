package com.codeit.redis.config;

import com.codeit.redis.keystore.RedisKeyStore;
import com.codeit.redis.session.MySession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class MySessionKeyStoreConfig {

    @Bean
    public RedisKeyStore<MySession> sessionStore(
            RedisTemplate<String, Object> redisTemplate
    ) {
        return new RedisKeyStore<>(redisTemplate, "my-session");
    }
}