package com.codeit.network.controller;


import com.codeit.network.dto.message.MessageCreateRequest;
import com.codeit.network.dto.message.MessageResponse;
import com.codeit.network.mapper.ChannelMapper;
import com.codeit.network.service.ChannelMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/channels/")
@RequiredArgsConstructor
public class ChannelMessageController {

    private final ChannelMessageService channelMessageService;
    private final TaskExecutor longPollingTaskExecutor;


    // 채널 메시지 작성(POST)
    @PostMapping("/{channelId}/messages")
    public MessageResponse sendMessage(@PathVariable Long channelId,
                                       @RequestBody MessageCreateRequest request) {
        log.info("[POST] /api/channels/{}/messages 요청됨 : {}", channelId, request);
        return channelMessageService.sendChannelMessage(channelId, request);
    }


    // Polling : 단발 조회, 기존 사용하던 REST와 동일!
    @GetMapping("/{channelId}/messages")
    public List<MessageResponse> getChannelMessages(
            @PathVariable Long channelId,
            @RequestParam(required = false) Long lastMessageId // full scan을 줄이기 위한 마지막 조회 ID
    ) {
        log.info("[GET] /api/channels/{}/messages 요청됨: lastMessageId={}", channelId, lastMessageId);
        if(lastMessageId == null) {
            return channelMessageService.getChannelMessages(channelId);
        } else {
            return channelMessageService.pollChannelMessages(channelId, lastMessageId);
        }
    }


    // Long Polling : 최대 30초 동안 back-end에서 이벤트 감지 예정
    // DeferredResult : Long polling 구현을 위해 지연 응답이 가능한 객체
    @GetMapping("/{channelId}/messages/long-poll")
    public DeferredResult<List<MessageResponse>> longPollChannelMessages(
            @PathVariable Long channelId,
            @RequestParam(required = false) Long lastMessageId // full scan을 줄이기 위한 마지막 조회 ID
    ) {
        log.info("[GET-LONG] /api/channels/{}/messages 요청됨: lastMessageId={}", channelId, lastMessageId);
        long timeoutMillis = 30_000L;
        DeferredResult<List<MessageResponse>> deferredResult
                = new DeferredResult<>(timeoutMillis, Collections.emptyList());

        longPollingTaskExecutor.execute(()->{
            try {
                Long cursor = lastMessageId != null ? lastMessageId : 0L;
                while (!deferredResult.isSetOrExpired()) {
                    List<MessageResponse> messages =
                            channelMessageService.pollChannelMessages(channelId, cursor);

                    if(!messages.isEmpty()) {
                        log.info("[LONG-POLL] 새메시지 발견, 반환!: 갯수={}개", messages.size());
                        deferredResult.setResult(messages); // 메세지 추가!!
                        return;
                    }
                    Thread.sleep(3000L);
                }

                // 타임 아웃 발생!
                log.info("[LONG-POLL] Time out 발생!");
                deferredResult.setResult(Collections.emptyList());
            } catch (Exception e) {
                log.info("[LONG-POLL] Error 발생!");
            }
        });

        return deferredResult;
    }
}
