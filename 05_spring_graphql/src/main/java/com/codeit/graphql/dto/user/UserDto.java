package com.codeit.graphql.dto.user;


import com.codeit.graphql.entity.Role;

import java.time.LocalDateTime;

public record UserDto(
        Long id,
        String username,
        String email,
        String nickName,
        Role role,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}