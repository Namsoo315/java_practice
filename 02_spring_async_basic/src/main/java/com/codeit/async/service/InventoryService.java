package com.codeit.async.service;

import com.codeit.async.dto.order.InventoryResponse;
import com.codeit.async.entiry.Product;
import com.codeit.async.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository productRepository;

    @Transactional
    public InventoryResponse decreaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다"));

        int current = product.getQuantity();
        boolean inStock = current >= quantity;

        if (!inStock) {
            log.warn("재고 부족 - productId={}, 요청수량={}, 현재재고={}",
                    productId, quantity, current);
            // 재고 부족이면 줄이지 않고 현재 재고 정보만 내려줌
            return new InventoryResponse(productId, false, current);
        }

        product.setQuantity(current - quantity); // 실제 재고 차감
        productRepository.save(product);
        return new InventoryResponse(productId, true, product.getQuantity());
    }
}
