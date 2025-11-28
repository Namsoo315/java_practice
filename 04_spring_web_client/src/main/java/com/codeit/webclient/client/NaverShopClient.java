package com.codeit.webclient.client;

import com.codeit.webclient.dto.shop.NaverShopResponse;
import com.codeit.webclient.dto.shop.ShopSearchRequest;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverShopClient {

  public final WebClient naverWebClient;

  public Mono<NaverShopResponse> search(ShopSearchRequest request) {
    return naverWebClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/shop.json")
            .queryParam("query", request.query())
            .queryParam("display", request.display())
            .queryParam("start", request.startOrDefault())
            .queryParam("sort", request.sortOrDefault())
            .queryParamIfPresent("filter", Optional.ofNullable(request.filter()))
            .queryParamIfPresent("exclude", Optional.ofNullable(request.exclude()))
            .build())
        .retrieve()
        .bodyToMono(NaverShopResponse.class)
        .timeout(Duration.ofSeconds(2));
  }
}
