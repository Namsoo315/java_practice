package com.codeit.blog.dto.post;

import com.codeit.blog.dto.comment.CommentResponse;
import com.codeit.blog.dto.file.AttachFileResponse;
import com.codeit.blog.dto.user.UserSimpleResponse;
import com.codeit.blog.entity.Category;
import com.codeit.blog.entity.PostLike;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public record PostResponse(
        Long id,
        String title,
        String content,
        Category category,
        String tags,
        Instant createdAt,
        Instant updatedAt,
        UserSimpleResponse author,
        List<CommentResponse> comments,
        List<AttachFileResponse> attachments,
        List<PostLike> likes,
        long likeCount
) {
    public PostResponse {
        // null 방지를 위한 기본 리스트 초기화
        if (comments == null) comments = new ArrayList<>();
        if (attachments == null) attachments = new ArrayList<>();
    }
}
