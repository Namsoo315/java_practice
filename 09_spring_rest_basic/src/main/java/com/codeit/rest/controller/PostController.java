package com.codeit.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codeit.rest.config.FileConfig;
import com.codeit.rest.dto.common.ApiResult;
import com.codeit.rest.dto.post.PostCreateRequest;
import com.codeit.rest.entity.Post;
import com.codeit.rest.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
	private final PostService postService;
	private final FileConfig fileConfig;

	// 패턴 1 - 파일 유첨이 있을 때 특수하게 처리해야하는 패턴
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)	// 파일 유첨이 필요할 때
	public ResponseEntity<ApiResult<Post>> create(
		@RequestPart("post")PostCreateRequest request,	// post input에 json으로 받아올때 사용하는 어노테이션
		@RequestParam(required = false) MultipartFile image) {
		Post createdPost = postService.create(request, image);
		return ResponseEntity.status(HttpStatus.CREATED)	// 201
			.body(ApiResult.ok(createdPost));
	}

	// 패턴1 - 파일 유첨이 없을 때 순수한 REST로 설게할 수 있는 패턴.
	@PostMapping(path ="/json", consumes = MediaType.APPLICATION_JSON_VALUE)	// 파일 유첨이 필요할 때
	public ResponseEntity<ApiResult<Post>> createForJson(
		@RequestBody PostCreateRequest post){	// ModelAttribute : 진짜 form 처리, input을 각각의 이름으로 활용{
		Post createdPost = postService.create(post, null);
		return ResponseEntity.status(HttpStatus.CREATED)	// 201
			.body(ApiResult.ok(createdPost));
	}

	// 패턴2 - 모든 interface를 form로 맞춰 설계할 수 있다.
	@PostMapping(path ="/form", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)	// 파일 유첨이 필요할 때
	public ResponseEntity<ApiResult<Post>> createForMultiForm(
		@ModelAttribute PostCreateRequest post,	// ModelAttribute : 진짜 form 처리, input을 각각의 이름으로 활용
		@RequestParam(required = false) MultipartFile image) {
		Post createdPost = postService.create(post, image);
		return ResponseEntity.status(HttpStatus.CREATED)	// 201
			.body(ApiResult.ok(createdPost));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResult<Post>> get(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResult.ok(postService.findById(id)));
	}

	@GetMapping
	public ResponseEntity<ApiResult<List<Post>>> getAll() {

		return ResponseEntity.ok(ApiResult.ok(postService.findAll()));
	}

}
