package com.codeit.jwt.security.jwt;

import com.codeit.jwt.dto.common.ErrorResponse;
import com.codeit.jwt.dto.user.UserDto;
import com.codeit.jwt.security.BlogUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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

import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;

// 요청 온 패킷의 header 영역을 체크 (Authorization + Bearer)
// 하여 토큰이 있는 경우 인증과 인가를 동시에하는 필터
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = resolveToken(request);
            if(StringUtils.hasText(token)) {
                if(jwtUtil.validate(token)) {
                    // 유효한 경우
                    UserDto userDto = jwtUtil.parse(token).userDto();
                    BlogUserDetails userDetails = new BlogUserDetails(userDto, null);
                    UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // context에 담아 다음 처리에서 authentication을 쓸수 있게 끔 만드는 코드
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("set authentication for user : {}", userDetails.getUsername());
                }else {
                    log.debug("Invalid JWT token Provider {}", token);
                    sendErrorResponse(response, "INVALID_TOKEN", "유효하지 않는 토큰입니다.", HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        } catch (Exception e) {
            log.debug("Invalid JWT token Provider {}", e.getMessage());
            SecurityContextHolder.clearContext();   // context 비우기
            sendErrorResponse(response, "AUTH_FAILED", "인증처리중에 오류가 발생하였다.", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        // 만약 헤더 검사가 없으면 다음 필터로 이동
        filterChain.doFilter(request, response);
    }

    // Authorization: Bearer xxx 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 공통 에러 응답
    private void sendErrorResponse(HttpServletResponse response, String code, String message, int status) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(code, message);
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}