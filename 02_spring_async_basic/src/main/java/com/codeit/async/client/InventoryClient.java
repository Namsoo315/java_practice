package com.codeit.async.client;

import com.codeit.async.dto.order.InventoryResponse;
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
}
