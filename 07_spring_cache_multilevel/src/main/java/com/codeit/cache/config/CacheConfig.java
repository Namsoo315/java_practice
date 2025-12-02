package com.codeit.cache.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {

    public CaffeineCacheManager caffeineCacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager("posts", "postSearch");
        manager.setCaffeine(
                Caffeine.newBuilder()
                        .maximumSize(10_000)
                        .expireAfterWrite(Duration.ofMinutes(10 + ThreadLocalRandom.current().nextInt(-2, 3)))
                        .expireAfterAccess(Duration.ofMinutes(5))
                        .removalListener((key, value, cause) ->
                                log.info("removalListener call! key={} value={} cause={}", cause, key, value))
                        .evictionListener((key, value, cause) ->
                                log.info("evictionListener call! key={} value={} cause={}", cause, key, value))
                        .recordStats()
        );
        return manager;
    }

    public RedisCacheManager redisCacheManager(
            RedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper
    ) {
        ObjectMapper redisObjectMapper = objectMapper.copy();
        redisObjectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                DefaultTyping.EVERYTHING,
                As.PROPERTY
        );

        RedisCacheConfiguration config =
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        new GenericJackson2JsonRedisSerializer(redisObjectMapper)
                                )
                        )
                        .prefixCacheNameWith("cache-demo:")
                        .entryTtl(Duration.ofSeconds(600))
                        .disableCachingNullValues();

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .initialCacheNames(Set.of("users", "session", "posts", "postSearch"))
                .build();
    }

//    // 멀티레벨 캐시 매니저 단순히 2개를 구성하고 싶을떄
//    @Bean
//    public CacheManager cacheManager(
//            RedisConnectionFactory connectionFactory,
//            ObjectMapper objectMapper
//    ) {
//        return new CompositeCacheManager(caffeineCacheManager(),
//                redisCacheManager(connectionFactory, objectMapper)); // L1 → L2 순서
//    }

    // L1, L2를 완전히 분리하고 싶을때
    @Bean
    public CacheManager cacheManager(
            RedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper
    ) {
        CaffeineCacheManager caffeine = caffeineCacheManager();
        RedisCacheManager redis = redisCacheManager(connectionFactory, objectMapper);

        // L1+L2 체인으로 묶을 캐시 이름
        Set<String> chainCaches = Set.of("posts", "postSearch");

        return new CodeitLevelCacheManager(caffeine, redis, chainCaches);
    }
}
