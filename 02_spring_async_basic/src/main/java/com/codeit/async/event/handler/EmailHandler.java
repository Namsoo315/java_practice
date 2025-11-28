package com.codeit.async.event.handler;

import com.codeit.async.event.UserLoginFailedEvent;
import com.codeit.async.event.UserRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

// Email 처리용 핸들러
@Slf4j
@Component
public class EmailHandler {

    private final String ADMIN_EMAIL = "admin@test.com";

    // 회원 가입 이벤트 처리 핸들러
    @Async(value = "ioExecutor")
    @EventListener(condition = "#event.role != 'ADMIN'")
    public void handleUserRegistered(UserRegisteredEvent event) throws InterruptedException {
        // 이메일 전송 시뮬레이션
        log.info("[{}] 회원가입 알림 이메일 전송 시작, email : {}",
                Thread.currentThread().getName(), event.email());
        Thread.sleep(3000);
        log.info("[{}] 회원가입 알림 이메일 전송 완료!, email : {}",
                Thread.currentThread().getName(), event.email());
    }

    // 로그인 실패 이벤트
    @Async(value = "cpuExecutor")
    @EventListener(condition = "#event.username != 'admin'")
    public void handleUserLoginFailed(UserLoginFailedEvent event) throws InterruptedException {
        Thread.sleep(3000);
        log.info("[{}] 로그인 이메일 알림, username : {}, 사유 : {}, 전송 : {}",
                Thread.currentThread().getName(), event.username(), event.reason(), ADMIN_EMAIL);
    }
}
