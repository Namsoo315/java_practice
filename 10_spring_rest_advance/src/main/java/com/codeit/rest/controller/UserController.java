package com.codeit.rest.controller;

import com.codeit.rest.dto.common.ApiResult;
import com.codeit.rest.dto.user.UserCreateRequest;
import com.codeit.rest.entity.User;
import com.codeit.rest.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "User API")	// tag 까진 필수
public class UserController {
    private final UserService userService;

    @Operation(summary = "User 생성", description = "새로운 사용자를 생성합니다.")// 메서드에 대한 설명을 다는 어노테이션
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "생성 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청",
            content = @Content(schema = @Schema(implementation = ApiResult.class))),
        @ApiResponse(responseCode = "500", description = "서버 에러",
            content = @Content(schema = @Schema(implementation = ApiResult.class)))
    })
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
