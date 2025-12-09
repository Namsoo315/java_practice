package com.codeit.network.controller;

import com.codeit.network.dto.user.MeResponse;
import com.codeit.network.dto.user.UserLoginRequest;
import com.codeit.network.dto.user.UserLoginResponse;
import com.codeit.network.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest request,
                                   HttpServletRequest httpRequest) {
        return authService.login(request, httpRequest);
    }

    @GetMapping("/me")
    public MeResponse me() {
        return authService.me();
    }
}
