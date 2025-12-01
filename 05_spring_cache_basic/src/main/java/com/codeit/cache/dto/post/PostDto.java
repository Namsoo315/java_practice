package com.codeit.cache.dto.post;

import com.codeit.cache.dto.user.UserDto;

public record PostDto(
        Long id,
        String title,
        String content,
        UserDto author
) {}
