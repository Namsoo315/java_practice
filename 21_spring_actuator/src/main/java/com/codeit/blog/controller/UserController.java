package com.codeit.blog.controller;

import com.codeit.blog.dto.base.ApiResult;
import com.codeit.blog.dto.user.UserCreateRequest;
import com.codeit.blog.dto.user.UserResponse;
import com.codeit.blog.dto.user.UserUpdateRequest;
import com.codeit.blog.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<ApiResult<UserResponse>> create(@Valid UserCreateRequest user) {
    UserResponse created = userService.create(user);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResult.ok(created));
  }

  @PostMapping("/post2")
  public ResponseEntity<ApiResult<UserResponse>> post2(
      @RequestParam @NotBlank @Size String username,
      @RequestParam @NotBlank @Size String password,
      @RequestParam @NotBlank @Size String email,
      @RequestParam @NotNull @Size String nickname,
      LocalDate birthday) {
    UserCreateRequest user = new UserCreateRequest(username, password, email, nickname, birthday);
    UserResponse created = userService.create(user);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResult.ok(created));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResult<UserResponse>> findById(@PathVariable Long id) {
    return ResponseEntity.ok(ApiResult.ok(userService.findById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResult<?>> findAll(
      @RequestParam(required = false) String username
  ) {
    if (username != null && !username.isBlank()) {
      return ResponseEntity.ok(ApiResult.ok(userService.findByUsername(username)));
    } else {
      return ResponseEntity.ok(ApiResult.ok(userService.findAll()));
    }
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<ApiResult<UserResponse>> updatePut(
      @PathVariable Long id, @RequestBody UserUpdateRequest user
  ) {
    return ResponseEntity.ok(
        ApiResult.ok(userService.update(id, user))
    );
  }

  @PatchMapping(value = "/{id}")
  public ResponseEntity<ApiResult<UserResponse>> updatePatch(
      @PathVariable Long id, @RequestBody UserUpdateRequest user
  ) {
    return ResponseEntity.ok(
        ApiResult.ok(userService.update(id, user))
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResult<Void>> delete(@PathVariable Long id) {
    userService.delete(id);
    return ResponseEntity.ok(ApiResult.ok(null, "사용자가 삭제되었습니다."));
  }

}
