package com.codeit.jwt.config;

import com.codeit.jwt.entity.Role;
import com.codeit.jwt.security.Http403ForbiddenAccessDeniedHandler;
import com.codeit.jwt.security.jwt.JsonUsernamePasswordAuthenticationFilter;
import com.codeit.jwt.security.LoginFailureHandler;
import com.codeit.jwt.security.SpaCsrfTokenRequestHandler;
import com.codeit.jwt.security.jwt.JwtAuthenticationFilter;
import com.codeit.jwt.security.jwt.JwtLoginSuccessHandler;
import com.codeit.jwt.security.jwt.JwtLogoutHandler;
import com.codeit.jwt.security.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            ObjectMapper objectMapper,
            Http403ForbiddenAccessDeniedHandler http403ForbiddenAccessDeniedHandler,
            JwtLoginSuccessHandler jwtLoginSuccessHandler,
            JwtLogoutHandler jwtLogoutHandler,
            LoginFailureHandler loginFailureHandler,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            JwtUtil jwtutil    // JWT 발급/검증 유틸
    ) throws Exception {

        http
                // 1) 인가 설정
                .authorizeHttpRequests(authorize -> authorize
                        // 공개 엔드포인트
                        .requestMatchers("/api/auth/login", "/error", "/", "/index.html").permitAll()
                        .requestMatchers("/api/auth/csrf-token").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers("/api/posts").permitAll()
                        // 나머지는 USER 이상만 접근
                        .anyRequest().hasRole(Role.USER.name())
                )

                // 2) CSRF 설정 (Cookie 방식)
//                .csrf(csrf -> csrf.disable()) // 테스트 용도로는 끄기 권장
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/auth/logout")   // 로그아웃은 CSRF 예외
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
                )

                // 3) JWT 기반 로그아웃
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .addLogoutHandler(jwtLogoutHandler)
                        .logoutSuccessHandler(
                                new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT))
                )

                // 4) 예외 처리
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                        .accessDeniedHandler(http403ForbiddenAccessDeniedHandler)
                )

                // 5) 세션 정책: 완전 Stateless
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 6) JSON 로그인 필터 설정 (폼 로그인 → JWT 로그인으로 교체)
                .with(
                        new JsonUsernamePasswordAuthenticationFilter.Configurer(objectMapper),
                        configurer -> configurer
                                .loginProcessingUrl("/api/auth/login")
                                // 로그인 성공 시 JWT 발급
                                .successHandler(jwtLoginSuccessHandler)
                                // 로그인 실패 응답 (기존 LoginFailureHandler 대신 JSON 실패 핸들러 사용)
                                .failureHandler(loginFailureHandler)
                )
                // 7) 매 요청마다 Authorization 헤더의 JWT를 검증하는 필터
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        JsonUsernamePasswordAuthenticationFilter.class
                );

        // 8) cors 설정 추가
        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.addAllowedOriginPattern("*");  // 모든 Origin 허용
            config.addAllowedHeader("*");         // 모든 Header 허용
            config.addAllowedMethod("*");         // 모든 Method 허용
            config.setAllowCredentials(true);     // 쿠키/인증정보 허용 (필요할 때만)
            return config;
        }));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role(Role.ADMIN.name())
                .implies(Role.USER.name())
//                .role(Role.CHANNEL_MANAGER.name())
//                .implies(Role.USER.name())
                .build();
    }

    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(
            RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);
        return handler;
    }

    @Bean
    public CommandLineRunner debugFilterChain(SecurityFilterChain filterChain) {
        return args -> {
            int filterSize = filterChain.getFilters().size();
            List<String> filterNames = IntStream.range(0, filterSize)
                    .mapToObj(idx -> String.format("\t[%s/%s] %s", idx + 1, filterSize,
                            filterChain.getFilters().get(idx).getClass()))
                    .toList();
            log.debug("Debug Filter Chain...\n{}", String.join(System.lineSeparator(), filterNames));
        };
    }
}
