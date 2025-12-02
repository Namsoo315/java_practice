package com.codeit.cache.dto.user;

import com.codeit.cache.entity.Role;

public record UserDto(
        Long id,
        String username,
        String email,
        Role role
) {}