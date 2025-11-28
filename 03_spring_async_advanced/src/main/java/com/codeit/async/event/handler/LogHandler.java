package com.codeit.async.event.handler;

import com.codeit.async.event.UserLoginFailedEvent;
import com.codeit.async.event.UserLoginSuccessEvent;
import com.codeit.async.event.UserRegisteredEvent;
import com.codeit.async.security.AsyncUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

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

        // 로그인 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AsyncUserDetails user = (AsyncUserDetails)authentication.getPrincipal();

        // MDC 컨텍스트 가져오기
        Map<String, String> mdc = MDC.getCopyOfContextMap();

        // 비동기 스레드에서도 요청 정보 + 사용자 정보를 함께 로깅
        log.info("[{}] 로그인 성공, username={}, userId={}, role={}, requestId={}, method={}, uri={}, threadname={}",
                Thread.currentThread().getName(),
                user.getUsername(),
                user.getId(),
                user.getRole(),
                mdc.get("requestId"),
                mdc.get("requestMethod"),
                mdc.get("requestUri"),
                mdc.get("threadName")
        );
    }
}
