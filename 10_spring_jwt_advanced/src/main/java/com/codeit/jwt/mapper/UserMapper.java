package com.codeit.jwt.mapper;


import com.codeit.jwt.dto.user.UserCreateRequest;
import com.codeit.jwt.dto.user.UserDto;
import com.codeit.jwt.dto.user.UserResponse;
import com.codeit.jwt.entity.Role;
import com.codeit.jwt.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUser(UserCreateRequest createRequest);

    UserDto toDto(User user);

    @Mapping(source = "role", target = "role")
    UserResponse toResponse(User user);

    List<UserResponse> toUserResponseList(List<User> users);

    default Role toRole(String role) {
        if (role == null) {
            return Role.USER;
        }
        return Role.valueOf(role.toUpperCase());
    }
}
