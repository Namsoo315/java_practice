package com.codeit.async.controller;

import com.codeit.async.dto.user.LoginRequest;
import com.codeit.async.dto.user.LoginResponse;
import com.codeit.async.dto.user.SignUpRequest;
import com.codeit.async.dto.user.SignUpResponse;
import com.codeit.async.entiry.User;
import com.codeit.async.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest request) {
        User saved = userService.register(request.username(), request.email(), request.password());
        SignUpResponse response = new SignUpResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getAddress(),
                saved.getRole()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        User user = userService.login(request.username(), request.password());
        LoginResponse response = new LoginResponse(user.getId(), user.getUsername(), user.getRole());
        return ResponseEntity.ok(response);
    }
}
