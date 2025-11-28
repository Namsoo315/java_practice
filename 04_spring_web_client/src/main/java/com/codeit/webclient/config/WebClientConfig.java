package com.codeit.webclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableRetry   // @Retryable 활성화
public class WebClientConfig {

  @Value("${naver.api.base-url}")
  private String baseUrl;

  @Value("${naver.api.client-id}")
  private String clientId;

  @Value("${naver.api.client-secret}")
  private String clientSecret;

  @Bean
  public WebClient webClient() {
    return WebClient.builder()
        .baseUrl(baseUrl)       // api에서 공통적으로 사용할 URL
        .defaultHeader("X-Naver-Client_Id", clientId)
        .defaultHeader("X-Naver-Client-Secret", clientSecret)
        .build();
  }
}
