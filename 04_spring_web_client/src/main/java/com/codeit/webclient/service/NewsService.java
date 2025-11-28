package com.codeit.webclient.service;

import com.codeit.webclient.client.NaverNewsClient;
import com.codeit.webclient.dto.news.NewsArticleDto;
import com.codeit.webclient.dto.news.NewsSearchRequest;
import com.codeit.webclient.dto.news.NewsSearchResult;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {

  private final NaverNewsClient naverNewsClient;

  @CircuitBreaker(name = "naver", fallbackMethod = "newsFallback")
  public Mono<NewsSearchResult> searchNews(NewsSearchRequest request) {
    return naverNewsClient.searchNews(request)
        .map(response -> {
          List<NewsArticleDto> articles = response.items().stream()
              .map(NewsArticleDto::from)
              .toList();
          return NewsSearchResult.of(
              request.query(),
              response.total(),
              response.start(),
              response.display(),
              articles,
              false
          );
        });
  }

  // Resilience4j fallback ( 원래 인자 + Throwable, 리턴 타입 동일)
  private Mono<NewsSearchResult> newsFallback(NewsSearchRequest request, Throwable cause) {
    log.warn("네이버 뉴스 API 폴백 실행, query={}, reason={}",
        request.query(), cause.toString());

    return Mono.just(
        NewsSearchResult.of(
            request.query(),
            0,
            request.startOrDefault(),
            request.displayOrDefault(),
            List.of(),   // 빈 결과
            true         // fallback 여부 표시
        )
    );
  }
}
