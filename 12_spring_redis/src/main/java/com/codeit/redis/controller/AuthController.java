package com.codeit.redis.controller;

import com.codeit.redis.dto.LoginRequest;
import com.codeit.redis.dto.UserResponse;
import com.codeit.redis.entity.User;
import com.codeit.redis.service.AuthService;
import com.codeit.redis.session.MySession;
import com.codeit.redis.session.MySessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private static final String SESSION_COOKIE_NAME = "CODEIT_SESSION_ID";

    private final MySessionManager sessionManager;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(
            @RequestBody LoginRequest req,
            HttpServletResponse response
    ) {
        try {
            User user = authService.login(req.username(), req.password());
            String sessionId = sessionManager.createSession(user);

            Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(sessionManager.cookieMaxAgeSeconds());
            response.addCookie(cookie);

            MySession session = sessionManager.getSession(sessionId)
                    .orElseThrow(() -> new IllegalStateException("session create failed"));

            return ResponseEntity.ok(new UserResponse(
                    session.getUserId(),
                    session.getUsername(),
                    session.getCreatedAt()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = SESSION_COOKIE_NAME, required = false) String sessionId,
            HttpServletResponse response
    ) {
        if (sessionId != null && !sessionId.isBlank()) {
            sessionManager.deleteSession(sessionId);
        }

        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(
            @CookieValue(name = SESSION_COOKIE_NAME, required = false) String sessionId
    ) {
        if (sessionId == null || sessionId.isBlank()) {
            return ResponseEntity.status(401).build();
        }

        return sessionManager.getSession(sessionId)
                .map(s -> ResponseEntity.ok(new UserResponse(
                        s.getUserId(),
                        s.getUsername(),
                        s.getCreatedAt()
                )))
                .orElseGet(() -> ResponseEntity.status(401).build());
    }
}
