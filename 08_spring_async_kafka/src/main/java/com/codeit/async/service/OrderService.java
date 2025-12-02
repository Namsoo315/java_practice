package com.codeit.async.service;

import com.codeit.async.dto.OrderCreateRequest;
import com.codeit.async.dto.OrderResponse;
import com.codeit.async.dto.ProductResponse;
import com.codeit.async.entity.Order;
import com.codeit.async.entity.Product;
import com.codeit.async.entity.User;
import com.codeit.async.event.OrderCreatedEvent;
import com.codeit.async.event.publisher.OrderEventPublisher;
import com.codeit.async.repository.OrderRepository;
import com.codeit.async.repository.ProductRepository;
import com.codeit.async.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;


    // 상품 목록 조회
    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(p -> new ProductResponse(
                        p.getId(),
                        p.getName(),
                        p.getDescription(),
                        p.getPrice(),
                        p.getQuantity()
                ))
                .toList();
    }

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest req) {
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Product product = productRepository.findById(req.productId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        if (product.getQuantity() < req.quantity()) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        product.setQuantity(product.getQuantity() - req.quantity());

        double unitPrice = product.getPrice();
        double totalAmount = unitPrice * req.quantity();

        Order order = Order.builder()
                .user(user)
                .product(product)
                .status("CREATED")
                .quantity(req.quantity())
                .unitPrice(unitPrice)
                .totalAmount(totalAmount)
                .shippingAddr(req.shippingAddr())
                .build();

        Order saved = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                saved.getId(),
                user.getId(),
                product.getId(),
                saved.getQuantity(),
                saved.getTotalAmount(),
                saved.getShippingAddr(),
                saved.getCreatedAt()
        );

        // 비동기 Kafka 발행
        log.info("[Spring] kafka 발생, commit 완료 이후 비동기 실행 요청");
        eventPublisher.publishEvent(event);

        return new OrderResponse(
                saved.getId(),
                saved.getStatus(),
                user.getId(),
                product.getId(),
                saved.getQuantity(),
                saved.getUnitPrice(),
                saved.getTotalAmount(),
                saved.getShippingAddr(),
                saved.getCreatedAt()
        );
    }
}
