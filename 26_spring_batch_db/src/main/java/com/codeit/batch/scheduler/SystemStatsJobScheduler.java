package com.codeit.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SystemStatsJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job systemStatsJob;

    // 매 5분마다 실행 (예시) — 필요에 맞게 크론 수정
//    @Scheduled(cron = "0 0/5 * * * *")
//    @Scheduled(cron = "*/5 * * * * *")
    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public void runSystemStatsJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addString("runAt", Instant.now().toString())
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(systemStatsJob, params);
            log.info("[Scheduler] systemStatsJob started: id={}, status={}",
                    execution.getJobId(), execution.getStatus());
        } catch (Exception e) {
            log.error("[Scheduler] systemStatsJob failed", e);
        }
    }
}
