package com.codeit.async.event.publisher;

import com.codeit.async.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventPublisher {
    private static final String ORDER_CREATED_TOPIC = "order-created";
    private final KafkaTemplate<String, Object> kafka; // kafka 객체

    @Async("eventTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreatedKafkaEvent(OrderCreatedEvent event) {
        String key = event.orderId().toString();
        log.info("[Kafka] 주문 생성 이벤트 전송 시작 (AFTER_COMMIT), topic={}, key={}", ORDER_CREATED_TOPIC, key);
        kafka.send(ORDER_CREATED_TOPIC, key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.warn("[Kafka] 주문 생성 이벤트 전송 실패, topic={}, key={}, reason={}", ORDER_CREATED_TOPIC, key, ex.toString());
                    } else {
                        log.info("[Kafka] 주문 생성 이벤트 전송 성공, topic={}, partition={}, offset={}",
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}
