package com.codeit.batch.tasklet;

import com.codeit.batch.entity.SystemLoginStats;
import com.codeit.batch.service.LoginStatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
@RequiredArgsConstructor
public class SystemStatsRefreshTasklet implements Tasklet, StepExecutionListener {

    private final LoginStatService loginStatService;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("[SystemStatsRefreshTasklet] beforeStep");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        SystemLoginStats saved = loginStatService.refreshSystemStatsRow();
        log.info("[SystemStatsRefreshTasklet] refreshed -> users={}, attempts={}, success={}, last={}",
                saved.getTotalUsers(),
                saved.getTotalLoginAttempts(),
                saved.getTotalSuccessLogins(),
                saved.getLastLoginTime());
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("[SystemStatsRefreshTasklet] afterStep");
        return ExitStatus.COMPLETED;
    }
}
