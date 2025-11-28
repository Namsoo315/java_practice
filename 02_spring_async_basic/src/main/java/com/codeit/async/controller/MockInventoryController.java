package com.codeit.async.controller;

import com.codeit.async.dto.order.InventoryResponse;
import com.codeit.async.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

// 재고 관리 서비스, 우리 서버가 아닌 다른 서버라는 전제로 만들 예정
@RestController
@RequiredArgsConstructor
@RequestMapping("/mock-inventory")
public class MockInventoryController {

    private final Random random = new Random();
    private final InventoryService inventoryService;

    /**
     * 재고 체크 + 감소 (랜덤 실패 시나리오 포함)
     * 예: GET /mock-inventory/check/1?quantity=2
     */
    @GetMapping("/check/{productId}")
    public ResponseEntity<InventoryResponse> checkStock(
            @PathVariable Long productId,
            @RequestParam("quantity") int quantity
    ) {
        // 1) 서킷 브레이커 테스트용: 9 → 항상 장애
        if (productId == 9) {
            throw new RuntimeException("Service permanently down");
        }

        // 2) 5 이상 상품은 80% 확률로 실패 (일시 장애 시뮬레이션)
        if (productId >= 5) {
            if (random.nextInt(10) < 8) { // 0~7까지 80%
                throw new RuntimeException("Service temporarily unavailable");
            }
        }

        // 3) 정상 케이스: 1~3초 지연
        try {
            Thread.sleep(random.nextInt(1000) + 2000); // 1000~3000ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 4) 실제 재고 감소
        InventoryResponse response = inventoryService.decreaseStock(productId, quantity);
        return ResponseEntity.ok(response);
    }
}
