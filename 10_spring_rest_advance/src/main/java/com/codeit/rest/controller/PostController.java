package com.codeit.rest.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codeit.rest.config.FileConfig;
import com.codeit.rest.dto.common.ApiResult;
import com.codeit.rest.dto.common.SimplePageResponse;
import com.codeit.rest.dto.post.PostCreateRequest;
import com.codeit.rest.dto.post.PostPageRequest;
import com.codeit.rest.dto.post.PostUpdateRequest;
import com.codeit.rest.entity.Category;
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
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)    // 파일 유첨이 필요할 때
	public ResponseEntity<ApiResult<Post>> create(
		@RequestPart("post") PostCreateRequest request,    // post input에 json으로 받아올때 사용하는 어노테이션
		@RequestParam(required = false) MultipartFile image) {
		Post createdPost = postService.create(request, image);
		return ResponseEntity.status(HttpStatus.CREATED)    // 201
			.body(ApiResult.ok(createdPost));
	}

	// 패턴1 - 파일 유첨이 없을 때 순수한 REST로 설게할 수 있는 패턴.
	@PostMapping(path = "/json", consumes = MediaType.APPLICATION_JSON_VALUE)    // 파일 유첨이 필요할 때
	public ResponseEntity<ApiResult<Post>> createForJson(
		@RequestBody PostCreateRequest post) {    // ModelAttribute : 진짜 form 처리, input을 각각의 이름으로 활용{
		Post createdPost = postService.create(post, null);
		return ResponseEntity.status(HttpStatus.CREATED)    // 201
			.body(ApiResult.ok(createdPost));
	}

	// 패턴2 - 모든 interface를 form로 맞춰 설계할 수 있다.
	@PostMapping(path = "/form", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)    // 파일 유첨이 필요할 때
	public ResponseEntity<ApiResult<Post>> createForMultiForm(
		@ModelAttribute PostCreateRequest post,    // ModelAttribute : 진짜 form 처리, input을 각각의 이름으로 활용
		@RequestParam(required = false) MultipartFile image) {
		Post createdPost = postService.create(post, image);
		return ResponseEntity.status(HttpStatus.CREATED)    // 201
			.body(ApiResult.ok(createdPost));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResult<Post>> get(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResult.ok(postService.findById(id)));
	}

	// 쿼리 처리 받아주는 곳
	// 1. findAll
	// 2. findByTitle / findByContent ...
	// 5가지 중 하마나 처리하는 방식
	@GetMapping
	public ResponseEntity<ApiResult<List<Post>>> getAll(
		@RequestParam(required = false) String title,
		@RequestParam(required = false) String content,
		@RequestParam(required = false) Category category,
		@RequestParam(required = false) List<String> tags,
		@RequestParam(required = false) Long authorId
	) {
		List<Post> result = null;
		if (title != null && !title.isEmpty()) {
			result = postService.findByTitle(title);
		} else if (content != null && !content.isEmpty()) {
			result = postService.findByContent(content);
		} else if (category != null) {
			result = postService.findByCategory(category);
		} else if (tags != null && !tags.isEmpty()) {
			result = postService.findByTagsAny(tags);
		} else if (authorId != null) {
			result = postService.findByAuthor(authorId);
		} else {
			result = postService.findAll();
		}

		return ResponseEntity.ok(ApiResult.ok(result));
	}

	@GetMapping("/page")
	public ResponseEntity<ApiResult<Page<Post>>> searchPostPage(PostPageRequest request) {
		return ResponseEntity.ok(ApiResult.ok(postService.findPage(request)));
	}

	@GetMapping("/page2")
	public ResponseEntity<ApiResult<SimplePageResponse<Post>>> searchPostPage2(PostPageRequest request) {
		return ResponseEntity.ok(ApiResult.ok(postService.findPage2(request)));
	}

	// 수정 (multipart/form-data)
	@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResult<Post>> update(
		@PathVariable Long id,
		@RequestPart("post") PostUpdateRequest reqBody,
		@RequestPart(value = "image", required = false) MultipartFile image) {

		Post updated = postService.update(id, reqBody, image);
		return ResponseEntity.ok(ApiResult.ok(updated));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResult<Void>> delete(@PathVariable Long id) {
		postService.delete(id);
		return ResponseEntity.ok(ApiResult.ok(null));
	}

	@GetMapping("/count")
	public ResponseEntity<ApiResult<Long>> count() {
		return ResponseEntity.ok(ApiResult.ok(postService.count()));
	}

	// 파일 다운로드
	@GetMapping("/{id}/download")
	public ResponseEntity<Resource> downloadByPostId(@PathVariable Long id) throws IOException {
		Post post = postService.findById(id);
		if (post.getImagePath() == null || post.getImagePath().isBlank()) {
			return ResponseEntity.notFound().build();
		}
		Path uploadRoot = Paths.get(fileConfig.getUploadDir()).toAbsolutePath().normalize();
		Path filePath = uploadRoot.resolve(post.getImagePath()).normalize();
		Resource resource = new UrlResource(filePath.toUri());
		if (!resource.exists() || !resource.isReadable()) {
			return ResponseEntity.notFound().build();
		}

		String contentType = Files.probeContentType(filePath);
		if (!StringUtils.hasText(contentType)) {
			contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		}

		String downloadName = StringUtils.hasText(post.getImageRealName())
			? post.getImageRealName()
			: resource.getFilename();

		String encoded = URLEncoder.encode(downloadName, StandardCharsets.UTF_8)
			.replaceAll("\\+", "%20");
		String contentDisposition = "attachment; filename=\"" + downloadName + "\"; filename*=UTF-8''" + encoded;

		return ResponseEntity.ok()
			.contentType(MediaType.parseMediaType(contentType))
			.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
			.contentLength(Files.size(filePath))
			.body(resource);
	}

}
