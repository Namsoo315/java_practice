package com.codeit.graphql.mapper;


import com.codeit.graphql.dto.comment.CommentDto;
import com.codeit.graphql.dto.comment.CommentPageDto;
import com.codeit.graphql.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    // 엔티티 -> DTO
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "author.id", target = "authorId")
    CommentDto toDto(Comment comment);

    List<CommentDto> toDtoList(List<Comment> comments);

    // Page<Comment> -> CommentPageDto
    default CommentPageDto toPageDto(Page<Comment> page) {
        List<CommentDto> contentDtos = toDtoList(page.getContent());
        return new CommentPageDto(
                contentDtos,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
