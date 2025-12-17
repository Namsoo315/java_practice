package com.codeit.elastic.controller;

import com.codeit.elastic.document.PostDocument;
import com.codeit.elastic.dto.PostCreateRequest;
import com.codeit.elastic.dto.PostResponse;
import com.codeit.elastic.dto.PostUpdateRequest;
import com.codeit.elastic.dto.common.PagedResult;
import com.codeit.elastic.service.PostSearchService;
import com.codeit.elastic.service.PostService;
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
    private final PostSearchService postSearchService;

    // CRUD (RDB)
    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody PostCreateRequest request) {
        PostResponse saved = postService.create(request);
        return ResponseEntity
                .created(URI.create("/api/posts/" + saved.postId()))
                .body(saved);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> get(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.get(postId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> update(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest request
    ) {
        return ResponseEntity.ok(postService.update(postId, request));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostResponse> delete(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.delete(postId));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    // Search (Elasticsearch)
    @GetMapping("/search/content")
    public ResponseEntity<PagedResult<PostDocument>> searchContent(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(postSearchService.searchContent(keyword, page, size));
    }

    @GetMapping("/search/author")
    public ResponseEntity<PagedResult<PostDocument>> searchAuthor(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(postSearchService.searchAuthor(keyword, page, size));
    }

    @GetMapping("/search/all")
    public ResponseEntity<PagedResult<PostDocument>> searchAll(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(postSearchService.searchAll(keyword, page, size));
    }

}
