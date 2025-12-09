package com.codeit.network.controller;

import com.codeit.network.entity.User;
import java.util.UUID;

import com.codeit.network.sse.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sse")
public class SseController {
    private final SseService sseService;

    // 구독 end-point
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @RequestParam(value = "LastEventId", required = false) String lastEventId
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof User user)){
            throw new IllegalStateException("로그인된 사용자가 없습니다.");
        }
        UUID last = null;
        if(lastEventId != null && !lastEventId.isBlank()){
            last = UUID.fromString(lastEventId);
        }
        Long receiverId = user.getId();
        return sseService.connect(receiverId, last);
    }

    // 단순 카운터 SSE (1 ~ 5 증가 후 종료)
    @GetMapping(value = "/counter", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter simpleCounter() {
        SseEmitter emitter = new SseEmitter(10_000L); // 10초 타임아웃

        new Thread(()->{
            try {
                for(int i = 1; i <= 5; i++) {
                    // client로 보내는 방법
                    emitter.send(
                            SseEmitter.event()
                                    .id(""+i)
                                    .name("counter")
                                    .data("count = " + i)
                    );
                    Thread.sleep(1000L); // 1초
                }
                // 끝났을때
                emitter.complete();
            } catch (Exception e) {
                // 에러 발생시 종료
                emitter.completeWithError(e);
                throw new RuntimeException(e);
            }
        }).start();
        
        return emitter;
    }
}
