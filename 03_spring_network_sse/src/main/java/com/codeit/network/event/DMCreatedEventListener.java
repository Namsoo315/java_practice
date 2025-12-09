package com.codeit.network.event;

import com.codeit.network.sse.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DMCreatedEventListener {

    private final SseService sseService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(DMCreatedEvent event) {
        log.info("[DM Event] receiver={} messageId={}", event.receiverId(), event.message().id());

        // SSE 알림 발송
        sseService.send(
                Set.of(event.receiverId()),
                "dm",
                event.message()
        );
    }
}
