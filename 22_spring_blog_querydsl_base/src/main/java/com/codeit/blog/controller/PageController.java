package com.codeit.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // 홈 (index)
    @GetMapping("/")
    public String homePage() {
        return "index"; // templates/index.html
    }

    // ===== 회원 =====
    @GetMapping("/user/signup")
    public String signupPage() {
        return "user/signup";
    }

    @GetMapping("/user/update")
    public String updateUserPage() {
        return "user/update";
    }

    @GetMapping("/user/delete")
    public String deleteUserPage() {
        return "user/delete";
    }

    @GetMapping("/user/login")
    public String loginPage() {
        return "user/login";
    }

    @GetMapping("/user/find")
    public String findUserPage() {
        return "user/find";
    }

    // ===== 게시글 =====
    @GetMapping("/post/create")
    public String createPostPage() {
        return "post/create";
    }

    @GetMapping("/post/update")
    public String updatePostPage() {
        return "post/update";
    }

    @GetMapping("/post/delete")
    public String deletePostPage() {
        return "post/delete";
    }

    @GetMapping("/post/deletesoft")
    public String deletePostPageSoft() {
        return "post/deletesoft";
    }

    @GetMapping("/post/find")
    public String findPostPage() {
        return "post/find";
    }

    // ===== 댓글 =====
    @GetMapping("/comment/create")
    public String createCommentPage() {
        return "comment/create";
    }

    @GetMapping("/comment/delete")
    public String deleteCommentPage() {
        return "comment/delete";
    }

    @GetMapping("/comment/find")
    public String findCommentPage() {
        return "comment/find";
    }
}
