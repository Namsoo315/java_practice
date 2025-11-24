package com.codeit.jwt.service;

import com.codeit.jwt.dto.user.JwtInformation;
import com.codeit.jwt.security.BlogUserDetails;
import com.codeit.jwt.security.jwt.JwtRegistry;
import com.codeit.jwt.security.jwt.JwtTokenProvider;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtRegistry<Long> jwtRegistry;
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    // 토큰 재발급 시나리오
    public JwtInformation refreshToken(String refreshToken) {
        // Validate refresh token (토큰 자체가 유효한지 검증, JWT 세션에서도 유효한지 검증)
        if (!tokenProvider.validateRefreshToken(refreshToken) || !jwtRegistry.hasActiveJwtInformationByRefreshToken(refreshToken)) {
            log.info("Invalid or expired refresh token : {}", refreshToken);
            throw new RuntimeException("Invalid or expired refresh token");
        }

        String username = tokenProvider.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }

        try {
            BlogUserDetails blogUserDetails = (BlogUserDetails) userDetails;
            String newAccessToken = tokenProvider.generateAccessToken(blogUserDetails);
            String newRefreshToken = tokenProvider.generateRefreshToken(blogUserDetails);
            log.info("RefreshToken : {}", newAccessToken);
            JwtInformation newJwtInformation = new JwtInformation(
                blogUserDetails.getUserDto()
                , newAccessToken
                , newRefreshToken
            );

            // JWT의 세션의 기존 세션을 rotate하는 메서드 호출 -> 토큰 교체
            jwtRegistry.rotateJwtInformation(refreshToken, newJwtInformation);
            return newJwtInformation;
        } catch (Exception e) {
            log.error("Failed to generate new tokens for user: {}", username, e);
        	throw new RuntimeException(e);
        }
    }


}
