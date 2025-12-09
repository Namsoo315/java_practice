package com.codeit.network.mapper;

import com.codeit.network.dto.user.MeResponse;
import com.codeit.network.dto.user.UserLoginResponse;
import com.codeit.network.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserLoginResponse toLoginResponse(User user);

    MeResponse toMeResponse(User user);
}
