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
                // WebSocket + REST 로그인 함께 쓰므로 form, basic은 모두 비활성
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // WebSocket handshake는 CSRF 제외해야 함
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/ws-stomp/**",          // WebSocket endpoint
                                "/api/auth/**"           // 로그인은 REST이므로 CSRF 제외
                        )
                )

                .authorizeHttpRequests(auth -> auth
                        // 로그인, 회원가입만 공개
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/signup",
                                "/ws-stomp/**"           // WebSocket handshake는 인증 없어도 허용 (개발용)
                        ).permitAll()

                        // 나머지 API도 현재는 개발 편의를 위해 모두 허용
                        .anyRequest().permitAll()
                );

        // 세션은 AuthService.login()에서 직접 관리한다고 가정하므로 기본 설정 유지
        return http.build();
    }
}
