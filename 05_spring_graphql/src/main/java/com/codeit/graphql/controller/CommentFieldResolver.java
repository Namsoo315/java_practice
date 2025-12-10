package com.codeit.graphql.controller;

import com.codeit.graphql.dto.comment.CommentDto;
import com.codeit.graphql.dto.post.PostDto;
import com.codeit.graphql.dto.user.UserDto;
import com.codeit.graphql.service.PostService;
import com.codeit.graphql.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CommentFieldResolver {

    private final UserService userService;
    private final PostService postService;

    @SchemaMapping(typeName = "Comment", field = "author")
    public UserDto author(CommentDto comment) {
        return userService.getUser(comment.authorId());
    }

    @SchemaMapping(typeName = "Comment", field = "post")
    public PostDto post(CommentDto comment) {
        return postService.getPost(comment.postId());
    }
}
