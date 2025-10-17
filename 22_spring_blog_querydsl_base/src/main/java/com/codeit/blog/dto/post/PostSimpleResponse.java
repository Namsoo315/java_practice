package com.codeit.blog.dto.post;

import com.codeit.blog.dto.user.UserSimpleResponse;
import com.codeit.blog.entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.time.Instant;

@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public record PostSimpleResponse(
        Long id,
        String title,
        Category category,
        String tags,
        Instant createdAt,
        UserSimpleResponse author
) { }
