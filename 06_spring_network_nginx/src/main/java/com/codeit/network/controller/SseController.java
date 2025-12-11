package com.codeit.network.controller;

import com.codeit.network.entity.User;
import com.codeit.network.security.MyUserDetails;
import com.codeit.network.security.jwt.JwtTokenProvider;
import com.codeit.network.sse.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sse")
public class SseController {

    private final SseService sseService;
    private final JwtTokenProvider jwtTokenProvider;

    // 구독 end-point
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @RequestParam(value = "LastEventId", required = false) String lastEventId
    ) {
        UUID last = null;
        if (lastEventId != null && !lastEventId.isBlank()) {
            last = UUID.fromString(lastEventId);
        }
        Long userId = userDetails.getUserDto().id();
        return sseService.connect(userId, last);
    }


    // 단순 카운터 SSE (1 ~ 5 증가 후 종료)
    @GetMapping(value = "/counter", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter simpleCounter() {
        SseEmitter emitter = new SseEmitter(10_000L); // 10초 타임아웃

        new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    emitter.send(
                            SseEmitter.event()
                                    .id(String.valueOf(i))
                                    .name("counter")
                                    .data("count = " + i)
                    );
                    Thread.sleep(1000L);
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
                throw new RuntimeException(e);
            }
        }).start();

        return emitter;
    }
}
