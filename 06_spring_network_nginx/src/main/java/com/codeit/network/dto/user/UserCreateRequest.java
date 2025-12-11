package com.codeit.network.dto.user;

import com.codeit.network.entity.Role;
import lombok.Builder;

@Builder
public record UserCreateRequest(
        String username,
        String nickname,
        String email,
        String password,
        Role role    // null이면 USER
) {}
