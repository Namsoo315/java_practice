package com.codeit.blog.controller;


import com.codeit.blog.dto.base.ApiResult;
import com.codeit.blog.dto.base.SlicePageResponse;
import com.codeit.blog.dto.comment.CommentResponse;
import com.codeit.blog.dto.post.*;
import com.codeit.blog.service.CommentService;
import com.codeit.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<PostResponse>> create(
            @RequestPart("post") PostCreateRequest req,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        PostResponse created = postService.create(req, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.ok(created));
    }

    // 상세보기
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<PostResponse>> findById(@PathVariable Long id) {
        PostResponse found = postService.findById(id);
        return ResponseEntity.ok(ApiResult.ok(found));
    }

    // 일반 page 예제
    @GetMapping()
    public ResponseEntity<List<PostResponse>> findPage() {
        return ResponseEntity.ok(postService.findAll());
    }

    // JPQL + soft delete 적용
    @GetMapping("/search1")
    public ResponseEntity<SlicePageResponse<PostSimpleResponse>> findPage(
            @ModelAttribute PostSearchRequest searchReq) {
        return ResponseEntity.ok(postService.findPage(searchReq));
    }

    // 일반 page 예제 (soft delete + QueryDSL 적용)
    @GetMapping("/search2")
    public ResponseEntity<SlicePageResponse<PostFlatDetailDto>> findPage2(
            @ModelAttribute PostSearchRequest searchReq) {
        return ResponseEntity.ok(postService.findPageForDsl(searchReq));
    }


    @DeleteMapping("/{id}/soft")
    public ResponseEntity<ApiResult<Void>> deleteSoft(@PathVariable Long id) {
        postService.deleteSoft(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<PostResponse>> update(
            @PathVariable Long id,
            @RequestPart("post") PostUpdateRequest req,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        PostResponse updated = postService.update(id, req, files);
        return ResponseEntity.ok(ApiResult.ok(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Void>> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<ApiResult<List<PostResponse>>> findTitle(@PathVariable String title) {
        return ResponseEntity.ok(ApiResult.ok(postService.findTitle(title)));
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<ApiResult<List<PostResponse>>> findTag(@PathVariable String tag) {
        return ResponseEntity.ok(ApiResult.ok(postService.findTag(tag)));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResult<List<PostResponse>>> findCategory(@PathVariable String category) {
        return ResponseEntity.ok(ApiResult.ok(postService.findCategory(List.of(category))));
    }

    @GetMapping("/category")
    public ResponseEntity<ApiResult<List<PostResponse>>> findCategory(@RequestParam List<String> category) {
        return ResponseEntity.ok(ApiResult.ok(postService.findCategory(category)));
    }


    @GetMapping("/count")
    public ResponseEntity<ApiResult<Long>> count() {
        return ResponseEntity.ok(ApiResult.ok(postService.count()));
    }


    // 특정 게시글(Post)의 댓글 조회
    @GetMapping("/{postId}/comments")
    public ResponseEntity<ApiResult<List<CommentResponse>>> findByPost(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.findByPostId(postId);
        return ResponseEntity.ok(ApiResult.ok(comments));
    }

}
