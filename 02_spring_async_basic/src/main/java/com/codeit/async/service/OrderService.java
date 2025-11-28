package com.codeit.async.service;

import com.codeit.async.client.InventoryClient;
import com.codeit.async.entiry.Order;
import com.codeit.async.entiry.Product;
import com.codeit.async.entiry.User;
import com.codeit.async.repository.OrderRepository;
import com.codeit.async.repository.ProductRepository;
import com.codeit.async.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final ApplicationEventPublisher eventPublisher;
    
    // 비동기 주문 생성
    // 일부 조회는 직접 DB 조회할 예정이고,
    // 재고 서비스는 WebClient + CompletableFuture로 처리 될 예정
    public CompletableFuture<Order> placeOrderAsync(Long userId, Long productId, int quantity) {
        // 동기 조회
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("사용자를 찾을수 없습니다."));
        Product  product = productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("상품을 찾을수 없습니다."));
        log.info("[{}] userid={}, productId={}, quantity={}", Thread.currentThread().getName(), userId, productId, quantity);
        
        // 1) 재고 감소 비동기 호출
        return inventoryClient.decreaseStockAsync(productId, quantity)
                // 2) 재고 응답 검증
                .thenApply(inventoryResponse -> {
                    if (!inventoryResponse.inStock()
                            || inventoryResponse.availableQuantity() < quantity) {
                        // 재고 부족 상태 예외 발생
                        throw new IllegalStateException("재고가 부족합니다.");
                    }
                    return inventoryResponse;
                })
                // 3) 주문 저장
                .thenApply(inventoryResponse -> {
                    Order order = Order.builder()
                            .user(user)
                            .product(product)
                            .quantity(quantity)
                            .totalAmount(product.getPrice() * quantity)
                            .unitPrice(product.getPrice())
                            .status("CREATED")
                            .shippingAddr(user.getAddress())
                            .build();
                    orderRepository.save(order);
                    Order savedOrder = orderRepository.save(order);
                    log.info("[{}] 주문 생성 완료!, orderId={}", Thread.currentThread().getName(),savedOrder.getId());
                    return order;
                });
    }

}
