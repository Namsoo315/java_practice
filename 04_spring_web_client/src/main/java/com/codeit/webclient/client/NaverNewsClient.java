package com.codeit.webclient.client;


import com.codeit.webclient.dto.news.NaverNewsResponse;
import com.codeit.webclient.dto.news.NewsSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverNewsClient {

    public final WebClient naverWebClient;

    public Mono<NaverNewsResponse> searchNews(NewsSearchRequest request) {
        return naverWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/news.json")
                        .queryParam("query", request.query())
                        .queryParam("display", request.display())
                        .queryParam("start", request.start())
                        .queryParam("sort", request.sort())
                        .build()
                ).retrieve() // 응답 받아오는 메서드
                .onStatus(HttpStatusCode::is4xxClientError,
                        res -> Mono.error(
                                new IllegalArgumentException("클라이언트 오류")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        res -> Mono.error(
                                new IllegalStateException("서버 오류")))
                .bodyToMono(NaverNewsResponse.class)
                .timeout(Duration.ofSeconds(2)) // timeout 2초
                .retryWhen(
                        Retry.backoff(2, Duration.ofMillis(200)) // 2번 200ms
                                .filter(ex -> ex instanceof IOException)
                ).doOnError(ex -> log.error("네이버 API 호출 실패!"));
            }
}
