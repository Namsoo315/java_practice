package com.codeit.async.controller;


import com.codeit.async.dto.common.ErrorResponse;
import com.codeit.async.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Controller
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public CompletableFuture<ResponseEntity<?>> placeOrder(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        return orderService.placeOrderAsync(userId, productId, quantity)
                .thenApply(order -> {
                    if (order == null) {
                        return ResponseEntity.badRequest()
                                .body(new ErrorResponse(
                                        500,
                                        "/api/orders",
                                        "주문 실패",
                                        Instant.now()
                                ));
                    }
                    return ResponseEntity.ok(order);
                })
                .exceptionally(ex -> {
                    return ResponseEntity.status(500).body(new ErrorResponse(
                            500,
                            "/api/orders",
                            ex.getMessage(),
                            Instant.now()
                    ));
                });
    }
}
