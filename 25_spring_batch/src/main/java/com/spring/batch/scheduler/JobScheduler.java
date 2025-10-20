package com.spring.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobScheduler {

  // job을 실행 시켜줄 객체
  private final JobLauncher jobLauncher;
  private final Job exampleJob01;   // Bean으로 등록한 job을 설정할 수 있음.

  @Scheduled(initialDelay = 1000, fixedDelay = 5000)
  public void runJob()
      throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
    JobParameters jobParameter = new JobParametersBuilder()
        .addLong("ts", System.currentTimeMillis())
        .toJobParameters();

    JobExecution exec = jobLauncher.run(exampleJob01, jobParameter);

    // 실행 결과 로그
    log.info("==== Job Finished ====");
    log.info("Status            : {}", exec.getStatus());
    log.info("Exit Status       : {}", exec.getExitStatus());
    log.info("Job Instance ID   : {}", exec.getJobId());
    log.info("Job getCreateTime : {}", exec.getCreateTime());
    log.info("Job getEndTime    : {}", exec.getEndTime());
    log.info("Last Updated      : {}", exec.getLastUpdated());
    log.info("Failure Exceptions: {}", exec.getFailureExceptions());
  }

}
