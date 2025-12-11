package com.codeit.network.security.jwt;


import com.codeit.network.security.MyUserDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtRegistry<Long> jwtRegistry;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        // 1. 인증 무효화를 위한 쿠키 발급
        Cookie refreshTokenCookie = jwtTokenProvider.genereateRefreshTokenExpirationCookie();
        response.addCookie(refreshTokenCookie);

        // 2. jwtRegistry 초기화
//        Arrays.stream(request.getCookies())
//                .filter(cookie -> cookie.getName().equals(JwtTokenProvider.REFRESH_TOKEN_COOKIE_NAME))
//                .findFirst()
//                .ifPresent(cookie -> {
//                    String refreshToken = cookie.getValue();
//                    Long userId = jwtTokenProvider.getUserId(refreshToken);
//                    // 토큰 무효화 리스트
//                    jwtRegistry.invalidateJwtInformationByUserId(userId);
//                });
        try {
             jwtRegistry.invalidateJwtInformationByUserId(((MyUserDetails)authentication.getPrincipal()).getUserDto().id());
        } catch (Exception ignored) {}
        log.debug("JWT logout handler executed - refresh token cookie cleared");
    }
}
