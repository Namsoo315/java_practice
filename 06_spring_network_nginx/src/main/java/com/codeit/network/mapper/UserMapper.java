package com.codeit.network.mapper;

import com.codeit.network.dto.user.UserCreateRequest;
import com.codeit.network.dto.user.UserDto;
import com.codeit.network.dto.user.UserResponse;
import com.codeit.network.entity.Role;
import com.codeit.network.entity.User;
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
