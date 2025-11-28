package com.codeit.async.config;


import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync // @Async 어노테이션을 활성화 하기 위한 중요한 어노테이션
@Configuration
@RequiredArgsConstructor
public class AsyncConfig implements AsyncConfigurer {

    private final MeterRegistry meterRegistry;

    // CPU 바운드 작업용 Executor
    //  - 예 : 이미지 처리(썸네일), 데이터 변환, 암호화, 복호화 등등 CPU 자원을 많이 사용 하는 작업
    //  - 코어 수 기준으로 설정
    @Bean(name = "cpuExecutor")
    public ThreadPoolTaskExecutor cpuExecutor() {
        int cores = Runtime.getRuntime().availableProcessors();

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cores); // 코어수 만큼
        executor.setMaxPoolSize(cores + 1); // 코어 + 1
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("cpu-async-");
        // pool 다찼을 경우의 거부 정책
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        
        // Micrometer 등록(모니터링용)
        ExecutorServiceMetrics.monitor(meterRegistry,
                executor.getThreadPoolExecutor(),
                "cpu-executor");
        
        return executor;
    }

    // IO 바운드 작업용 Executor
    //  - 예 : 이메일 발송, 외부 API 호출, 파일 IO, DB 대량 조회 등등
    //  - 대기 시간이 많으므로 CPU보다 더 많은 스레드 허용
    @Bean(name = "ioExecutor")
    public ThreadPoolTaskExecutor ioExecutor() {
        int cores = Runtime.getRuntime().availableProcessors();

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cores * 2); // 최소 CPU 2배수
        executor.setMaxPoolSize(cores * 4); // 많으면 4~5배수
        executor.setQueueCapacity(500); // IO 널널히
        executor.setThreadNamePrefix("io-async-");
        // pool 다찼을 경우의 거부 정책
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();

        // Micrometer 등록(모니터링용)
        ExecutorServiceMetrics.monitor(meterRegistry,
                executor.getThreadPoolExecutor(),
                "io-executor");
        return executor;
    }

    // void 반환용 @Async 메서드 예외처리 핸들러 적용
    @Override
    public @Nullable AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new GlobalAsyncExceptionHandler();
    }
}
