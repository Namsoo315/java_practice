package com.codeit.blog.dto.user;

import lombok.Builder;

@Builder
public record UserSimpleResponse(
        String username,
        String email,
        String nickname
) { }
