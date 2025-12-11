package com.codeit.network.controller;

import com.codeit.network.dto.user.JwtDto;
import com.codeit.network.dto.user.JwtInformation;
import com.codeit.network.dto.user.UserDto;
import com.codeit.network.security.MyUserDetails;
import com.codeit.network.security.jwt.JwtTokenProvider;
import com.codeit.network.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("csrf-token")
    public ResponseEntity<Void> getCsrfToken(CsrfToken csrfToken) {
        log.debug("CSRF 토큰 요청");
        log.trace("CSRF 토큰: {}", csrfToken.getToken());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // refresh 요청, 토큰을 회전하는 메서드
    // -> 모든 토큰을 재발행하는 과정
    @PostMapping("refresh")
    public ResponseEntity<JwtDto> refreshToken(
            @CookieValue(JwtTokenProvider.REFRESH_TOKEN_COOKIE_NAME) String refreshToken,
            HttpServletResponse response
    ){
        log.info("Refresh Token: {}", refreshToken);
        JwtInformation jwtInformation = authService.refreshToken(refreshToken);
        Cookie cookie = jwtTokenProvider.genereateRefreshTokenCookie(jwtInformation.getRefreshToken());
        response.addCookie(cookie);

        JwtDto body = new JwtDto(jwtInformation.getUserDto(), jwtInformation.getAccessToken());
        return ResponseEntity.ok(body);
    }


    @GetMapping("me")
    public ResponseEntity<UserDto> me(@AuthenticationPrincipal MyUserDetails userDetails) {
        log.info("내 정보 조회 요청");
        return ResponseEntity.status(HttpStatus.OK).body(userDetails.getUserDto());
    }

    @GetMapping("me2")
    public ResponseEntity<UserDto> me2() {
        log.info("내 정보 조회 요청 (SecurityContext)");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof MyUserDetails userDetails)) {
            log.warn("인증 정보가 없습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDto userDto = userDetails.getUserDto();
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
