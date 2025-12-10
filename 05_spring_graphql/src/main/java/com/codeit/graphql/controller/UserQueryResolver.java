package com.codeit.graphql.controller;

import com.codeit.graphql.dto.user.UserDto;
import com.codeit.graphql.repository.UserRepository;
import com.codeit.graphql.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

// QueryResolver : graphql 스키마에 정의 쿼리를 작성하는 영역!!
@Controller
@RequiredArgsConstructor
public class UserQueryResolver {

  private final UserService userService;

  @QueryMapping
  public UserDto user(@Argument Long id) {
    return userService.getUser(id);
  }

  @QueryMapping
  public List<UserDto> users() {
    return userService.getUsers();
  }
}
