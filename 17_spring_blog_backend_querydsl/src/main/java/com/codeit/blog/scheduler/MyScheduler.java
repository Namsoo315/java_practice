package com.codeit.blog.scheduler;

import com.codeit.blog.repository.UserRepository;
import com.codeit.blog.service.AuthService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyScheduler {

  private final AuthService authService;
  private final UserRepository userRepository;

  // 5초마다 실행
  @Scheduled(fixedRate = 5000)
  public void task1() {
    System.out.println("5초마다 실행 :" + LocalDateTime.now());
  }
}
