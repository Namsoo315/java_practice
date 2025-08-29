package com.codeit.data.controller;


import com.codeit.data.dto.base.ApiResult;
import com.codeit.data.dto.post.PostDetailResponse;
import com.codeit.data.dto.post.PostSimpleResponse;
import com.codeit.data.entity.Post;
import com.codeit.data.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/posts")
public class PostControllerV2 {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<ApiResult<List<Post>>> findAll() {
        List<Post> posts = postService.findAllV2();
        return ResponseEntity.ok(ApiResult.ok(posts));
    }


    @GetMapping("/findAll2")
    public ResponseEntity<ApiResult<List<PostSimpleResponse>>> findAll2() {
        List<PostSimpleResponse> posts = postService.findAllV3();
        return ResponseEntity.ok(ApiResult.ok(posts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<PostDetailResponse>> findById(@PathVariable Long id) {
        PostDetailResponse found = postService.findById2(id);
        return ResponseEntity.ok(ApiResult.ok(found));
    }

}