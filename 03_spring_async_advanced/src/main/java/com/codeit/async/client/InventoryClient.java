package com.codeit.async.client;

import com.codeit.async.dto.order.InventoryResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryClient {

    private final WebClient inventoryWebClient;

    // 재고 감소 요청 비동기 호출
    @Async("cpuExecutor")
    // 서킷브레이크 적용, 서버 장애가 특정 비율이 넘을시 모든 요청을 잠시 차단하는 어노테이션
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackStock")
    public CompletableFuture<InventoryResponse> decreaseStockAsync(Long productId, int quantity) {
        System.out.println(Thread.currentThread().getName() + " : " + Thread.currentThread().getId() + " : " + Thread.currentThread().getState());
        log.info("재고 감소 요청(WebClient async) - productId={}, quantity={}",
                productId, quantity);

        return inventoryWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/mock-inventory/check/{productId}")
                        .queryParam("quantity", quantity)
                        .build(productId))
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .toFuture();
    }

    // fallback 메서드
    // fallback이란 장애 대체할수 있는 경로
    public CompletableFuture<InventoryResponse> fallbackStock(Long productId, int quantity, Throwable cause) {
        log.warn("재고 서비스 장애, fallback 처리 - productId={}, quantity={}, reason={}",
                productId, quantity, cause.toString());

        InventoryResponse fallback = new InventoryResponse(
                productId,
                false,
                -1   // 재고 정보 알 수 없으니 0 or 캐시 값
        );
        return CompletableFuture.completedFuture(fallback);
    }

}
