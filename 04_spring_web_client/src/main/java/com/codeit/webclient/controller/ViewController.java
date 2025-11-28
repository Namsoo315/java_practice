package com.codeit.webclient.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "index";   // templates/index.html
    }

    @GetMapping("/news")
    public String newsPage() {
        return "news";    // templates/news.html
    }

    @GetMapping("/shop")
    public String shopPage() {
        return "shop";    // templates/shop.html
    }
}

