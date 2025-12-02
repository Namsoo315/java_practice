package com.codeit.cache.controller;

import com.codeit.cache.dto.common.LoginResponse;
import com.codeit.cache.dto.user.LoginRequest;
import com.codeit.cache.dto.user.UserCreateRequest;
import com.codeit.cache.dto.user.UserDto;
import com.codeit.cache.dto.user.UserUpdateRequest;
import com.codeit.cache.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserCreateRequest request) {
        UserDto created = userService.create(request);
        return ResponseEntity
                .created(URI.create("/api/users/" + created.id()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request
    ) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 로그인
// 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest
    ) {
        UserDto user = userService.login(request.username(), request.password());

        // 세션 저장
        httpRequest.getSession(true).setAttribute("LOGIN_USER", user.username());

        // 응답 생성
        LoginResponse response = new LoginResponse(
                true,
                "로그인 성공했습니다.",
                user
        );

        return ResponseEntity.ok(response);
    }


    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, Principal principal) {

        if (principal != null) {
            userService.logout(principal.getName());
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }

    // 현재 로그인한 사용자 정보
    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDto user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(user);
    }
}

