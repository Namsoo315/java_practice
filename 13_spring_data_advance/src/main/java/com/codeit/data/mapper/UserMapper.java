package com.codeit.data.mapper;

import com.codeit.data.dto.user.UserCreateRequest;
import com.codeit.data.dto.user.UserResponse;
import com.codeit.data.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "id", ignore = true)  //특정 맵핑을 무시할 떄 사용하는 어노테이션
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  User toUser(UserCreateRequest request);

  UserResponse toUserResponse(User user);
}
