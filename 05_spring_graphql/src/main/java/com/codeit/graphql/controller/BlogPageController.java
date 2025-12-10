package com.codeit.graphql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogPageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
