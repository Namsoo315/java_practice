package com.codeit.webclient.controller;

import com.codeit.webclient.dto.news.NewsSearchRequest;
import com.codeit.webclient.dto.news.NewsSearchResult;
import com.codeit.webclient.dto.shop.ShopSearchRequest;
import com.codeit.webclient.dto.shop.ShopSearchResult;
import com.codeit.webclient.service.NewsService;
import com.codeit.webclient.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchApiController {

    private final NewsService newsService;
    private final ShopService shopService;

    @GetMapping("/news/search")
    public Mono<NewsSearchResult> searchNews(@ModelAttribute NewsSearchRequest request) {
        return newsService.searchNews(request);
    }

    @GetMapping("/shop/search")
    public Mono<ShopSearchResult> searchShop(@ModelAttribute ShopSearchRequest request) {
        return shopService.searchShop(request);
    }
}
