package com.codeit.jwt.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {
    private final ObjectMapper objectMapper;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=UTF-8");

        Map<String, Object> body = new HashMap<>();
        body.put("code", 200);
        body.put("message", "로그아웃 되었습니다.");

        try {
            response.getWriter().write(objectMapper.writeValueAsString(body));
        } catch (IOException e) {
            throw new RuntimeException("로그아웃 응답 작성 실패", e);
        }
    }
}