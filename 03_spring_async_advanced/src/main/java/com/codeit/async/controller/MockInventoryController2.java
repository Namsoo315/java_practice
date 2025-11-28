package com.codeit.async.controller;

import com.codeit.async.dto.order.InventoryResponse;
import com.codeit.async.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@RestController
@RequiredArgsConstructor
@RequestMapping("/fallback-inventory")
public class MockInventoryController2 {

    private final Random random = new Random();
    private final InventoryService inventoryService;

    // 장애 없는 fallback 메서드
    @GetMapping("/check/{productId}")
    public ResponseEntity<InventoryResponse> checkStock(
            @PathVariable Long productId,
            @RequestParam("quantity") int quantity
    ) {

        // 1) 서킷 브레이커 테스트용: 9 → 항상 장애
        if (productId == 9) {
            System.out.println("서버 수신!!!!!! Circuit Breaker Opened !!! - productId=9, quantity=" + quantity + "");
            throw new RuntimeException("Service permanently down");
        }

        // 2) 5 이상 상품은 80% 확률로 실패 (일시 장애 시뮬레이션)
        if (productId >= 5) {
            if (random.nextInt(10) < 5) { // 0~5까지 50%
                throw new RuntimeException("Service temporarily unavailable");
            }
        }

        // 3) 정상 케이스: 1~3
        try {
            Thread.sleep(random.nextInt(1000)); // 1000~3000ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        InventoryResponse response = inventoryService.decreaseStock(productId, quantity);
        return ResponseEntity.ok(response);
    }
}
