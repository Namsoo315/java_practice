package com.codeit.rest.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeit.rest.dto.common.ApiResult;
import com.codeit.rest.entity.Post;
import com.codeit.rest.service.PostService;
import com.codeit.rest.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/posts")
@Tag(name = "Post", description = "Post API")	// tag 까진 필수
public class PostControllerV2 {
	private final PostService postService;
	private final UserService userService;

	// HATEOS 상세 조회
	@GetMapping("/{id}")
	public ResponseEntity<ApiResult<EntityModel<Post>>> findById(@PathVariable Long id) {
		Post post = postService.findById(id);

		EntityModel<Post> entityModel = EntityModel.of(post,
			linkTo(methodOn(PostControllerV2.class).findById(id)).withSelfRel(),
			linkTo(methodOn(PostControllerV2.class).findById(id)).withRel("next"),
			linkTo(methodOn(PostControllerV2.class).findAll()).withRel("posts")
			);
		if(post.getAuthorId() != null) {
			entityModel.add(linkTo(PostControllerV2.class).slash(post.getAuthorId()).withRel("author"));
		}
		return ResponseEntity.ok(ApiResult.ok(entityModel));
	}

	// HATEOAS 전체 조회
	@GetMapping
	public CollectionModel<EntityModel<Post>> findAll() {
		List<EntityModel<Post>> posts = postService.findAll().stream()
			.map(p -> EntityModel.of(
				p,
				linkTo(methodOn(PostControllerV2.class).findById(p.getId())).withSelfRel()
			))
			.toList();

		return CollectionModel.of(
			posts,
			linkTo(methodOn(PostControllerV2.class).findAll()).withSelfRel()
		);
	}
}
