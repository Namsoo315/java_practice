package com.codeit.cache.controller;

import com.codeit.cache.dto.common.PageResponse;
import com.codeit.cache.dto.post.PostCreateRequest;
import com.codeit.cache.dto.post.PostDto;
import com.codeit.cache.dto.post.PostSearchRequest;
import com.codeit.cache.dto.post.PostUpdateRequest;
import com.codeit.cache.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequest request) {
        PostDto created = postService.create(request);
        return ResponseEntity
                .created(URI.create("/api/posts/" + created.id()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request
    ) {
        return ResponseEntity.ok(postService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 검색 + 페이지 (keyword, page, size)
    @GetMapping
    public ResponseEntity<PageResponse<PostDto>> searchPosts(
            @ModelAttribute PostSearchRequest request
    ) {
        return ResponseEntity.ok(postService.search(request));
    }

    // 전체 목록 (단순 리스트)
    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }
}

