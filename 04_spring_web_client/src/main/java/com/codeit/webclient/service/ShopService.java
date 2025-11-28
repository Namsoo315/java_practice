package com.codeit.webclient.service;

import com.codeit.webclient.client.NaverShopClient;
import com.codeit.webclient.dto.shop.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopService {
    private final NaverShopClient shopClient;

    @Retry(name = "naver")                         // 공통 인스턴스
    @CircuitBreaker(name = "naver", fallbackMethod = "shopFallback")
    public Mono<ShopSearchResult> searchShop(ShopSearchRequest req) {
        return shopClient.search(req)
                .map(resp -> {
                    List<ShopItemDto> items = resp.items().stream()
                            .map(ShopItemDto::from)
                            .toList();

                    return ShopSearchResult.of(
                            req.query(),
                            resp.total(),
                            resp.start(),
                            resp.display(),
                            items,
                            false
                    );
                });
    }

    private Mono<ShopSearchResult> shopFallback(ShopSearchRequest req, Throwable cause) {
        log.warn("쇼핑 API 폴백 실행 - query={}, reason={}", req.query(), cause.toString());

        return Mono.just(
                ShopSearchResult.of(
                        req.query(),
                        0,
                        req.startOrDefault(),
                        req.displayOrDefault(),
                        List.of(),
                        true
                )
        );
    }
}