package com.codeit.cache.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";   // templates/index.html
    }

    @GetMapping("/users")
    public String users() {
        return "user";    // templates/user.html
    }

    @GetMapping("/posts")
    public String posts() {
        return "post";    // templates/post.html
    }
}
