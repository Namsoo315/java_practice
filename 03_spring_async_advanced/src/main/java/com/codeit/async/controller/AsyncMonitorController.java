package com.codeit.async.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monitor")
public class AsyncMonitorController {
    private final ThreadPoolTaskExecutor cpuExecutor;
    private final ThreadPoolTaskExecutor ioExecutor;

    @GetMapping("/executors")
    public Map<String, Object> getExecutors() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("cpuExecutor", toStats(cpuExecutor));
        result.put("ioExecutor", toStats(ioExecutor));
        return result;
    }

    private Map<String, Object> toStats(ThreadPoolTaskExecutor executor) {
        ThreadPoolExecutor delegate = executor.getThreadPoolExecutor();

        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("corePoolSize", delegate.getCorePoolSize());
        stats.put("maxPoolSize", delegate.getMaximumPoolSize());
        stats.put("poolSize", delegate.getPoolSize());
        stats.put("activeCount", delegate.getActiveCount());
        stats.put("queueSize", delegate.getQueue().size());
        stats.put("taskCount", delegate.getTaskCount());
        stats.put("completedTaskCount", delegate.getCompletedTaskCount());
        stats.put("threadNamePrefix", executor.getThreadNamePrefix());
        return stats;
    }
}
