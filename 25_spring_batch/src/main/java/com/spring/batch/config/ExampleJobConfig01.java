package com.spring.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class ExampleJobConfig01 {

  private final JobRepository jobRepository;
  private final PlatformTransactionManager transactionManager;

  @Bean
  public Job exampleJob01() {
    return new JobBuilder("exampleJob01", jobRepository)
        .start(step01())    // job에 대한 step을 정의해야함.
        .build();
  }

  @Bean
  public Step step01() {
    return new StepBuilder("step01", jobRepository)
        .tasklet((contribution, chunkContext) -> {
              log.info("In step 01");
              System.out.println("In step 01");
//              return RepeatStatus.CONTINUABLE;
              return RepeatStatus.FINISHED;
            }
            , transactionManager)
        .build();
  }

}
