package com.codeit.rest.controller;

import com.codeit.rest.dto.common.ApiResult;
import com.codeit.rest.dto.user.UserCreateRequest;
import com.codeit.rest.entity.User;
import com.codeit.rest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    // @RequestBody : 대상 객체가 json, xml로 구성되었을때 자동으로 파싱해주는 어노테이션
    @PostMapping("/")
    public ResponseEntity<ApiResult<User>> createUser(@RequestBody UserCreateRequest request) {
        User createUser = userService.create(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(createUser));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResult.ok(createUser, "사용자가 정상적으로 생성되었습니다."));
    }

    // @PathVariable : 경로상의 식별자가 있는 경우 매개변수로 가져오는 어노테이션
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<User>> findUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResult.ok(userService.findById(id)));
    }
}
