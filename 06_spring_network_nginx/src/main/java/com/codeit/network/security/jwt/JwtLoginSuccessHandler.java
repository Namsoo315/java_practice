package com.codeit.network.security.jwt;


import com.codeit.network.dto.common.ErrorResponse;
import com.codeit.network.dto.user.JwtDto;
import com.codeit.network.dto.user.JwtInformation;
import com.codeit.network.security.MyUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 로그인이 성공되면 후단을 처리할 핸들러
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtRegistry<Long> jwtRegistry;


    // JSON 또는 Form 필터를 통해 이미 인증이 완료된 authentication을 인자로 받아오고,
    // 해당 authentication을 기반으로 accessToken과 refreshToken을 발급하는 메서드
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        if(authentication.getPrincipal() instanceof MyUserDetails userDetails){
            try {
                // 토큰 발급 코드
                String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
                String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
                
                // refreshToken은 쿠키에 담고, httponly 옵션 등을 활성화하여 쿠키로 전달예정
                Cookie refreshCookie = jwtTokenProvider.genereateRefreshTokenCookie(refreshToken);
                response.addCookie(refreshCookie);

                // accessToken은 브라우저에 저장할수 있도록 일반 응답(json, body)으로 보낼 예정
                JwtDto jwtDto = new JwtDto(userDetails.getUserDto(), accessToken);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(objectMapper.writeValueAsString(jwtDto));
                
                // JWT 세션 = jwtRegistry에 발급된 토큰 저장
                jwtRegistry.registerJwtInformation(
                        new JwtInformation(userDetails.getUserDto(), accessToken, refreshToken));

                log.info("Successfully registered JWT for user {}", userDetails.getUserDto());
            } catch (JOSEException e) {
                log.error(e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                ErrorResponse errorResponse = new ErrorResponse(
                        ""+HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            }
        } else {
            log.error("UNAUTHORIZED Error!!");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ErrorResponse errorResponse = new ErrorResponse(
                    ""+HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
    }
}












