package com.codeit.async.event.listener;

import com.codeit.async.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEmailEventListener {

    // private final EmailService emailService;  // 실제 서비스 주입 가정

    @Async("eventTaskExecutor")
    @KafkaListener(
            topics = "order-created",
            groupId = "order-email-group"
    )
    public void handleOrderEmailEvent(OrderCreatedEvent event) {
        log.info("[메일 리스너] 주문 메일 발송: orderId={}, userId={}, amount={}",
                event.orderId(), event.userId(), event.totalAmount());

        // emailService.sendOrderCreatedMail(event);
    }
}
