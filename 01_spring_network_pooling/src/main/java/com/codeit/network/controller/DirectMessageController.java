package com.codeit.network.controller;

import com.codeit.network.dto.message.MessageCreateRequest;
import com.codeit.network.dto.message.MessageResponse;
import com.codeit.network.service.DirectMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/dm")
@RequiredArgsConstructor
public class DirectMessageController {

    private final DirectMessageService directMessageService;
    private final TaskExecutor longPollingTaskExecutor;

    // 0) DM 작성 (POST)
    @PostMapping("/{receiverUserId}/messages")
    public MessageResponse sendDirectMessage(
            @PathVariable Long receiverUserId,
            @RequestBody MessageCreateRequest request
    ) {
        log.info("[POST] /api/dm/{}/messages 요청됨: {}", receiverUserId, request);
        return directMessageService.sendDirectMessage(receiverUserId, request);
    }

    // 단일 Polling
    @GetMapping("/messages")
    public List<MessageResponse> getInbox(
            @RequestParam(required = false) Long lastMessageId
    ) {
        log.info("[GET] /api/dm/messages 요청됨: lastMessageId={}", lastMessageId);

        if (lastMessageId == null) {
            return directMessageService.getInbox();
        }
        return directMessageService.pollInbox(lastMessageId);
    }

    // Long Polling
    @GetMapping("/messages/long-poll")
    public DeferredResult<List<MessageResponse>> longPollInbox(
            @RequestParam Long lastMessageId
    ) {
        log.info("[GET] /api/dm/messages/long-poll 요청됨: lastMessageId={}", lastMessageId);

        long timeoutMillis = 30_000L;

        DeferredResult<List<MessageResponse>> deferredResult =
                new DeferredResult<>(timeoutMillis, Collections.emptyList());

        longPollingTaskExecutor.execute(() -> {
            try {
                long endTime = System.currentTimeMillis() + timeoutMillis;
                Long cursor = lastMessageId != null ? lastMessageId : 0L;

                while (!deferredResult.isSetOrExpired() && System.currentTimeMillis() < endTime) {

                    List<MessageResponse> messages =
                            directMessageService.pollInbox(cursor);

                    if (!messages.isEmpty()) {
                        log.info("[LONG-POLL-DM] 새 메시지 발견 → 즉시 반환: {}개", messages.size());
                        deferredResult.setResult(messages);
                        return;
                    }

                    Thread.sleep(1_000L);
                }

                if (!deferredResult.isSetOrExpired()) {
                    log.info("[LONG-POLL-DM] 타임아웃 → 새 메시지 없음 → 빈 리스트 반환");
                    deferredResult.setResult(Collections.emptyList());
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("[LONG-POLL-DM] Interrupted", e);
                deferredResult.setErrorResult(e);

            } finally {
                log.debug("[LONG-POLL-DM] SecurityContext cleared");
                SecurityContextHolder.clearContext();
            }
        });

        return deferredResult;
    }
}