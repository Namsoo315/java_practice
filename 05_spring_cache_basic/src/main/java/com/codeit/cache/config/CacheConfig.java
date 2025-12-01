package com.codeit.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public CacheManager cacheManager() {
    // public CaffeineCacheManager caffeineCacheManager() { // 둘다 상관없음
    // CaffeineCacheManager 생성 및 캐시 네임 선정
    CaffeineCacheManager manager = new CaffeineCacheManager("users", "posts", "postSearch");

    // 세팅
    manager.setCaffeine(Caffeine.newBuilder()
        // 1) 메모리 설정
        .maximumSize(10_000) // 캐시 갯수, 누수 및 무한 캐시 방지용 옵션
//        .maximumWeight(50_000_000)  // 메모리 크기, 50MB (maximumSize와 같이 설정하면 안됨!
        // 2) TTL 옵션
//        .expireAfterWrite(10, TimeUnit.MINUTES)
        .expireAfterWrite(Duration.ofMinutes(10 + ThreadLocalRandom.current().nextInt(-2, 3)))  // +- 2m 랜덤 시간 추가
        // 3) TTI 옴션 - 접근 없으면 자동 만료
        .expireAfterAccess(Duration.ofMinutes(5))
        // 4) 세션 관련 통계 활성화 (Hit, Miss, Eviction = 초기화)
        .recordStats()
        // 5) 제거 이벤트 로깅
        .removalListener(((key, value, cause) -> {
          log.info("removalListener key ={}, value ={}, cause ={}", key, value, cause);
        }))
        // 5-1 ) 무효화 이벤트 로깅
        .evictionListener((key, value, cause) -> {
          log.info("evictionListener key ={}, value ={}, cause ={}", key, value, cause);
        })
    );

    return manager;
  }
}
