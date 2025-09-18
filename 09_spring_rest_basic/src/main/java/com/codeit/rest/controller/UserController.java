package com.codeit.rest.controller;

import com.codeit.rest.dto.common.ApiResult;
import com.codeit.rest.dto.user.UserCreateRequest;
import com.codeit.rest.dto.user.UserUpdateRequest;
import com.codeit.rest.entity.User;
import com.codeit.rest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    // @RequestBody : 대상 객체가 json, xml로 구성되었을때 자동으로 파싱해주는 어노테이션
    @PostMapping("/")
    public ResponseEntity<ApiResult<User>> createUser(@RequestBody UserCreateRequest request) {
        User createUser = userService.create(request);
        // return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(createUser));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResult.ok(createUser, "사용자가 정상적으로 생성되었습니다."));
    }

    // @PathVariable : 경로상의 식별자가 있는 경우 매개변수로 가져오는 어노테이션
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<User>> findUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResult.ok(userService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> findAll(@RequestParam(required = false) String username) {
        if(username != null && !username.isEmpty()){
            return ResponseEntity.ok(ApiResponse.ok(List.of(userService.findByUsername(username))));
        }else {
            return ResponseEntity.ok(ApiResponse.ok(userService.findAll()));
        }
    }

    // Update
    @PutMapping("/{id}")  // PutMapping : 값 전체가 새로운 값으로 대체될때
    public ResponseEntity<ApiResponse<User>> updatePut(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(userService.update(id, request)));
    }

    @PatchMapping("/{id}")  // PutMapping : 값 전체가 새로운 값으로 대체될때
    public ResponseEntity<ApiResponse<User>> updatePatch(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(userService.update(id, request)));
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("사용자가 정상적으로 삭제 되었습니다"));
    }

}
