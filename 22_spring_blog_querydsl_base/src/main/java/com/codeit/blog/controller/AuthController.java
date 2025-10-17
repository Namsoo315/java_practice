package com.codeit.blog.controller;


import com.codeit.blog.dto.base.ApiResult;
import com.codeit.blog.dto.user.UserLoginRequest;
import com.codeit.blog.dto.user.UserResponse;
import com.codeit.blog.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "login")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid UserLoginRequest loginRequest) {
        UserResponse user = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
