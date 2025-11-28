package com.codeit.async.event.handler;


import com.codeit.async.event.ProductCreatedEvent;
import com.codeit.async.service.InventoryService;
import com.codeit.async.service.ThumbnailTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventHandler {
    private final ThumbnailTaskService thumbnailTaskService;

    // 1) 커밋 직전(트랜잭션이 커밋되기 전에 실행
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
//    @Async // 붙이면 안된다!!
    public void handleBeforeCommitCreate(ProductCreatedEvent event) {
        //  @Async가 필요하다면 여기서 비동기 메서드 호출 예정!
        log.info("[BEFORE_COMMIT] 상품 생성 최종 검증, {}", event.product().getName());
    }

    // 2) 커밋 성공 후(실제 DB 반영 이후)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAfterCommitCreate(ProductCreatedEvent event) {
        log.info("[AFTER_COMMIT] 상품 생성 커밋 완료!!!, {}", event.product().getName());
        // 커밋 이후 썸네일 생성 비동기 처리 시작!
        thumbnailTaskService.generateThumbnailsAsync(event.taskId(), event.product(), event.files());
    }
    
    // 3) 롤백 발생시(예외 발생시)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleAfterRollbackCreate(ProductCreatedEvent event) {
        log.info("[AFTER_ROLLBACK] 오류 발생으로 Rollback 발생!!");
    }

    // 4) 트랜잭션 종료시(성공과 실패 무관)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleAfterCompletionCreate(ProductCreatedEvent event) {
        log.info("[AFTER_COMPLETION] 작업 종료!!");
    }

}
