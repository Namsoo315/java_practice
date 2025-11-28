package com.codeit.async.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Slf4j
@Configuration
public class WebClientConfig {

    /**
     * WebClient 공용 HttpClient 설정
     * - 연결 타임아웃
     * - 읽기/쓰기 타임아웃
     */
    private HttpClient httpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .responseTimeout(Duration.ofSeconds(5))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5))
                                .addHandlerLast(new WriteTimeoutHandler(5))
                );
    }

    /**
     * 재고 서비스용 WebClient
     * - baseUrl: http://localhost:8080
     * - 로깅 필터 포함
     */
    @Bean
    public WebClient inventoryWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8080")  // MockInventoryController 기준
                .clientConnector(new ReactorClientHttpConnector(httpClient()))
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    /**
     * Request 로깅 필터
     */
    private ExchangeFilterFunction logRequest() {
        return (request, next) -> {
            log.info("[WebClient Request] {} {}", request.method(), request.url());
            return next.exchange(request);
        };
    }

    /**
     * Response 로깅 필터
     */
    private ExchangeFilterFunction logResponse() {
        return (request, next) ->
                next.exchange(request)
                        .doOnNext(response ->
                                log.info("[WebClient Response] status={}", response.statusCode()));
    }
}
