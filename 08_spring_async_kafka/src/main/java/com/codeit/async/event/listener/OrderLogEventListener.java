package com.codeit.async.event.listener;

import com.codeit.async.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderLogEventListener {

    @Async("eventTaskExecutor")
    @KafkaListener(
            topics = "order-created",
            groupId = "order-log-group"
    )
    public void handleOrderLogEvent(OrderCreatedEvent event) {
        log.info("[로그 리스너] 주문 이벤트 수신: {}", event);
    }
}

