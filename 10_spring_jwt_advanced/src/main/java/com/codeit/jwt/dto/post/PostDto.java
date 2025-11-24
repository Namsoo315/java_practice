package com.codeit.jwt.dto.post;


import com.codeit.jwt.dto.user.UserDto;

import java.time.Instant;

public record PostDto(
        Long id,
        String title,
        String content,
        boolean deleted,
        Instant createdAt,
        Instant updatedAt,
        UserDto author
) {}
