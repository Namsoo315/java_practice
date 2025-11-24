package com.codeit.jwt.security.jwt;

import com.codeit.jwt.dto.user.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

// UsernamePasswordAuthenticationFilter 대신에 json으로 검증하는 필터
@RequiredArgsConstructor
public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper; // json 파싱, 생성 mapper


    // attemptAuthentication : json 기반으로 파싱하여 인증을 수행하고 Authentication 생성하는 메서드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        try {
            // JSON 파싱
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(),
                    LoginRequest.class);

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
            
            setDetails(request, authenticationToken);
            return this.getAuthenticationManager().authenticate(authenticationToken); // 실제 인증이 발생하는 구간
        } catch (Exception e) {
            throw new AuthenticationServiceException("인증 실패! ", e);
        }
    }

    public static class Configurer extends
            AbstractAuthenticationFilterConfigurer<HttpSecurity, Configurer, JsonUsernamePasswordAuthenticationFilter> {

        // 기본 로그인 처리 URL
        public static final String DEFAULT_LOGIN_URL = "/api/auth/login";

        public Configurer(ObjectMapper objectMapper) {
            // 두 번째 인자는 기본 loginProcessingUrl
            super(new JsonUsernamePasswordAuthenticationFilter(objectMapper), DEFAULT_LOGIN_URL);
        }

        @Override
        protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
            return request ->
                    request.getRequestURI().equals(loginProcessingUrl)
                            && request.getMethod().equals("POST");
        }

        @Override
        public void init(HttpSecurity http) throws Exception {
            loginProcessingUrl(DEFAULT_LOGIN_URL);
            super.init(http);
        }
    }
}
