package com.codeit.jwt.dto.user;


import com.codeit.jwt.entity.Role;

public record UserDto(
        Long id,
        String username,
        String email,
        Role role
) {}
