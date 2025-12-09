package com.codeit.network.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 1) CSRF: 개발 단계에서는 전체 비활성화 (POST /api/channels 403 방지)
                .csrf(AbstractHttpConfigurer::disable)

                // 2) WebSocket + REST 로그인 함께 쓰므로 form, basic은 모두 비활성
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // 3) URL별 인가 설정
                .authorizeHttpRequests(auth -> auth
                        // 로그인, 회원가입, STOMP 핸드셰이크는 모두 공개
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/signup",
                                "/ws-stomp/**"
                        ).permitAll()

                        // 그 외 요청은 현재는 개발 편의를 위해 모두 허용
                        .anyRequest().permitAll()
                );

        // 세션은 AuthService.login()에서 직접 관리한다고 가정하므로 기본 설정 유지
        return http.build();
    }
}
