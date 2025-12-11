package com.codeit.network.service;


import com.codeit.network.dto.user.JwtInformation;
import com.codeit.network.security.MyUserDetails;
import com.codeit.network.security.jwt.JwtRegistry;
import com.codeit.network.security.jwt.JwtTokenProvider;
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
        // 토큰 자체가 유효한지 검증하고, JWT 세션에서도 유효한지 검증하는 단계
        if(!tokenProvider.validateRefreshToken(refreshToken)
            || !jwtRegistry.hasActiveJwtInformationByRefreshToken(refreshToken)){
            log.info("Invalid or expired refresh token: {}", refreshToken);
            throw new RuntimeException("Invalid or expired refresh token");
        }

        String username = tokenProvider.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(userDetails == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        try {
            // 토큰을 재발급하는 로직
            MyUserDetails blogUserDetails = (MyUserDetails) userDetails;
            String newAccessToken = tokenProvider.generateAccessToken(blogUserDetails);
            String newRefreshToken = tokenProvider.generateRefreshToken(blogUserDetails);
            log.info("Refresh Token: {}", newAccessToken);

            JwtInformation newJwtInformation = new  JwtInformation(
                    blogUserDetails.getUserDto()
                    ,newAccessToken
                    ,newRefreshToken
            );
            // JWT 세션의 기존 세션을 rotate 하는 메서드 호출 -> token 갱신용!
            jwtRegistry.rotateJwtInformation(refreshToken, newJwtInformation);
            return newJwtInformation;
        } catch (Exception e) {
            log.error("Failed to generate new tokens for user: {}", username, e);
            throw new RuntimeException("INTERNAL_SERVER_ERROR");
        }
    }


}
