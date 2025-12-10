package com.codeit.graphql.controller;

import com.codeit.graphql.dto.comment.CommentDto;
import com.codeit.graphql.dto.post.PostDto;
import com.codeit.graphql.dto.user.UserDto;
import com.codeit.graphql.service.CommentService;
import com.codeit.graphql.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PostFieldResolver {

  private final UserService userService;
  private final CommentService commentService;

  @SchemaMapping(typeName = "Post", field = "author")
  public UserDto author(PostDto post) {
    return userService.getUser(post.authorId());
  }

  @SchemaMapping(typeName = "Post", field = "comments")
  public Iterable<CommentDto> comments(PostDto post) {
    return commentService.getCommentsByPostId(post.id());
  }

}
