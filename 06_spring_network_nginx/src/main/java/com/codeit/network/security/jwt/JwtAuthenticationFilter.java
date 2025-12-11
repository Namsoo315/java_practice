package com.codeit.network.security.jwt;

import com.codeit.network.dto.user.UserDto;
import com.codeit.network.security.MyUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final ObjectMapper objectMapper;
    private final JwtRegistry<Long> jwtRegistry;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        // 로그인/리프레시 요청은 이 필터 안 태움
        return path.equals("/api/auth/refresh") || path.equals("/api/auth/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // 1) Authorization 헤더 → 2) REFRESH_TOKEN 쿠키 순서로 토큰 추출
            String token = resolveToken(request);
            System.out.println("token = " + token);

            if (StringUtils.hasText(token)) {
                if ((tokenProvider.validateAccessToken(token)
                        || tokenProvider.validateRefreshToken(token))
                        && (jwtRegistry.hasActiveJwtInformationByAccessToken(token)
                        || jwtRegistry.hasActiveJwtInformationByRefreshToken(token)) ) {

                    String username = tokenProvider.getUsernameFromToken(token);
                    UserDto userDto = null;
                    try {
                        userDto = tokenProvider.parseAccessToken(token).userDto();
                    } catch (Exception ignored) {}
                    if(userDto == null){
                        try {
                            userDto = tokenProvider.parseRefreshToken(token).userDto();
                        } catch (Exception ignored) {}
                    }
                    if(userDto == null) throw new RuntimeException("INVALID_TOKEN");
                    MyUserDetails userDetails = new MyUserDetails(userDto, null);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    log.debug("Authentication success for user: {}", username);

                } else {
                    log.debug("Invalid JWT token (expired, tampered, or revoked) - treat as anonymous");
                    SecurityContextHolder.clearContext();
                }
            }

        } catch (Exception e) {
            // 예외 발생해도 마찬가지로 인증만 지우고, 요청은 계속 흐르게
            log.error("JWT authentication error: {}", e.getMessage(), e);
            SecurityContextHolder.clearContext();
        }

        // 무조건 다음 필터/컨트롤러로 넘김
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String headerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(headerToken) && headerToken.startsWith("Bearer ")) {
            return headerToken.substring(7);
        }

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName() + " = " + cookie.getValue());
                if ("REFRESH_TOKEN".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    // 이제는 안 쓰이지만 남겨두고 싶으면 놔둬도 됨
    private void sendErrorResponse(HttpServletResponse response, String message, int status)
            throws IOException {

        var errorResponse = new com.codeit.network.dto.common.ErrorResponse("" + status, message);
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
