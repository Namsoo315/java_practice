package com.codeit.async.event.handler;

import com.codeit.async.event.UserLoginFailedEvent;
import com.codeit.async.event.UserLoginSuccessEvent;
import com.codeit.async.event.UserRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

// 로그 이벤트 처리 핸들러
@Component
@Slf4j
public class LogHandler {
    // 회원 가입 이벤트 처리 핸들러
//    @Async(value = "cpuExecutor") // 빠지면 비동기 처리되지 않는다!
    @EventListener(condition = "#event.role != 'ADMIN'")
    public void handleUserRegistered(UserRegisteredEvent event) throws InterruptedException {
        Thread.sleep(1000);
        log.info("[{}] 회원가입 완료 로그, username : {}, userid : {}",
                Thread.currentThread().getName(), event.username(), event.userId());
    }

    // 로그인 실패 이벤트
    @Async(value = "cpuExecutor")
    @EventListener(condition = "#event.username != 'admin'")
    public void handleUserLoginFailed(UserLoginFailedEvent event) throws InterruptedException {
        Thread.sleep(1000);
        log.info("[{}] 로그인 실패, username : {}, 사유 : {}",
                Thread.currentThread().getName(), event.username(), event.reason());
    }

    // 로그인 성공 이벤트
    @Async(value = "cpuExecutor")
    @EventListener(condition = "#event.username != 'admin'")
    public void handleUserLoginFailed(UserLoginSuccessEvent event) throws InterruptedException {
        Thread.sleep(1000);
        log.info("[{}] 로그인 성공, username : {}",
                Thread.currentThread().getName(), event.username());
    }
}
