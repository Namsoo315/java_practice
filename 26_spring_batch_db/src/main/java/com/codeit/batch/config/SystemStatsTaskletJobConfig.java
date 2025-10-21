package com.codeit.batch.config;

import com.codeit.batch.service.LoginStatService;
import com.codeit.batch.tasklet.SystemStatsRefreshTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class SystemStatsTaskletJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final LoginStatService loginStatService;

    @Bean
    public Job systemStatsJob(Step systemStatsStep) {
        return new JobBuilder("systemStatsJob", jobRepository)
                .start(systemStatsStep)
                .build();
    }

    @Bean
    public Step systemStatsStep(SystemStatsRefreshTasklet tasklet) {
        return new StepBuilder("systemStatsStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .listener(tasklet)
                .build();
    }

    @Bean
    public SystemStatsRefreshTasklet systemStatsRefreshTasklet() {
        return new SystemStatsRefreshTasklet(loginStatService);
    }
}
