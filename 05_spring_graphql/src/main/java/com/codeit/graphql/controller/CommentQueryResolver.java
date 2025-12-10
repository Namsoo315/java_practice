package com.codeit.graphql.controller;

import com.codeit.graphql.dto.comment.CommentDto;
import com.codeit.graphql.dto.comment.CommentPageDto;
import com.codeit.graphql.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CommentQueryResolver {

    private final CommentService commentService;

    // 단건 조회
    @QueryMapping
    public CommentDto comment(@Argument Long id) {
        return commentService.getComment(id);
    }

    // 페이징 조회
    @QueryMapping
    public CommentPageDto comments(@Argument int page, @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentService.getComments(pageable);
    }

    // 내용 검색
    @QueryMapping
    public CommentPageDto searchCommentsByContent(@Argument String keyword,
                                                  @Argument int page,
                                                  @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentService.searchByContent(keyword, pageable);
    }

    // 작성자 닉네임 검색
    @QueryMapping
    public CommentPageDto searchCommentsByAuthorNickName(@Argument String nickName,
                                                         @Argument int page,
                                                         @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentService.searchByAuthorNickName(nickName, pageable);
    }
}
