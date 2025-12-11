package com.codeit.network.dto.user;


import com.codeit.network.entity.Role;

public record UserDto(
        Long id,
        String username,
        String nickname,
        String email,
        Role role
) {}
