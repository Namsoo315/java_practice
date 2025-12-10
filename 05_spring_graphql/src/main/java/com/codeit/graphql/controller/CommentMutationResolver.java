package com.codeit.graphql.controller;

import com.codeit.graphql.dto.comment.CommentCreateRequest;
import com.codeit.graphql.dto.comment.CommentDto;
import com.codeit.graphql.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CommentMutationResolver {

    private final CommentService commentService;

    // 댓글 생성
    @MutationMapping
    public CommentDto createComment(@Argument("input") CommentCreateRequest input) {
        return commentService.createComment(input);
    }

    // 댓글 삭제 (soft delete)
    @MutationMapping
    public Boolean deleteComment(@Argument Long id) {
        return commentService.deleteComment(id);
    }
}
