package com.codeit.graphql.controller;


import com.codeit.graphql.dto.post.PostDto;
import com.codeit.graphql.dto.post.PostPageDto;
import com.codeit.graphql.entity.Post;
import com.codeit.graphql.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PostQueryResolver {

  private final PostService postService;

  // 단건 조회
  @QueryMapping
  public PostDto post(@Argument Long id) {
    return postService.getPost(id);
  }

  // 페이징 조회
  @QueryMapping
  public PostPageDto posts(@Argument int page, @Argument int size) {
    Pageable pageable = PageRequest.of(page, size);
    return postService.getPosts(pageable);
  }

  // 제목 검색
  @QueryMapping
  public PostPageDto searchPostsByTitle(
      @Argument String keyword,
      @Argument int page, @Argument int size) {

    Pageable pageable = PageRequest.of(page, size);
    return postService.searchByTitle(keyword, pageable);
  }


}
