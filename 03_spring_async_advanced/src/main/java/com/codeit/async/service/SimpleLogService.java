package com.codeit.async.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SimpleLogService {

    // @Async : 가장 간단한 비동기 메서드 만드는 방법!
    @Async(value = "ioExecutor")
    public void asyncLog(String message){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log(message);
    }

    @Async(value = "ioExecutor")
    public void asyncLog(String format, Object... args){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log(String.format(format, args));
    }

    private void log(String message) {
        String threadName = Thread.currentThread().getName();
        log.info("[{}], {}", threadName, message);
    }
}
