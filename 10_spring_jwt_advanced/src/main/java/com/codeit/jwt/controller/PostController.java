package com.codeit.jwt.controller;

import com.codeit.jwt.dto.post.PostCreateRequest;
import com.codeit.jwt.dto.post.PostDto;
import com.codeit.jwt.dto.post.PostUpdateRequest;
import com.codeit.jwt.security.BlogUserDetails;
import com.codeit.jwt.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> create(
            @RequestBody @Valid PostCreateRequest request,
            @AuthenticationPrincipal BlogUserDetails userDetails) {
        log.info("포스트 생성 요청: title={}", request.title());
        PostDto post = postService.create(request, userDetails.getUserDto().id());
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> findAll(
            @RequestParam(required = false, defaultValue = "false") boolean includeBlind,
            @AuthenticationPrincipal BlogUserDetails userDetails) {
        List<PostDto> posts;
        if (includeBlind && userDetails != null &&
                userDetails.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            log.info("전체 포스트 조회 요청 (관리자)");
            posts = postService.findAll();
        } else {
            log.info("공개 포스트 조회 요청");
            posts = postService.findAllVisible();
        }

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> findById(@PathVariable Long postId) {
        log.info("포스트 조회 요청: id={}", postId);
        PostDto post = postService.findById(postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> update(
            @PathVariable Long postId,
            @RequestBody @Valid PostUpdateRequest request) {
        log.info("포스트 수정 요청: id={}", postId);
        PostDto post = postService.update(postId, request);
        return ResponseEntity.ok(post);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{postId}/blind")
    public ResponseEntity<PostDto> toggleBlind(@PathVariable Long postId) {
        log.info("포스트 블라인드 토글 요청: id={}", postId);
        PostDto post = postService.toggleDeleted(postId);
        return ResponseEntity.ok(post);
    }
}
