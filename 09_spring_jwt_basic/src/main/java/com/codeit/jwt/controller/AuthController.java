package com.codeit.jwt.controller;

import com.codeit.jwt.dto.user.UserDto;
import com.codeit.jwt.security.BlogUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("csrf-token")
    public ResponseEntity<Void> getCsrfToken(CsrfToken csrfToken) {
        log.debug("CSRF 토큰 요청");
        log.trace("CSRF 토큰: {}", csrfToken.getToken());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("me")
    public ResponseEntity<UserDto> me(@AuthenticationPrincipal BlogUserDetails userDetails) {
        log.info("내 정보 조회 요청");
        return ResponseEntity.status(HttpStatus.OK).body(userDetails.getUserDto());
    }

    @GetMapping("me2")
    public ResponseEntity<UserDto> me2() {
        log.info("내 정보 조회 요청 (SecurityContext)");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof BlogUserDetails userDetails)) {
            log.warn("인증 정보가 없습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDto userDto = userDetails.getUserDto();
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
