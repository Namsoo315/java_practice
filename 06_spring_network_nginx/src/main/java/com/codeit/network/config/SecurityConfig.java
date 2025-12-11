package com.codeit.network.config;

import com.codeit.network.entity.Role;
import com.codeit.network.security.Http403ForbiddenAccessDeniedHandler;
import com.codeit.network.security.LoginFailureHandler;
import com.codeit.network.security.SpaCsrfTokenRequestHandler;
import com.codeit.network.security.jwt.*;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {

        http
                // 1) URL별 인가 설정
                .authorizeHttpRequests(auth -> auth
                        // 로그인, 회원가입, STOMP 핸드셰이크, 기본 페이지는 모두 공개
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/signup",
                                "/ws-stomp/**",
                                "/error",
                                "/",
                                "/index.html"
                        ).permitAll()

                        // CSRF 토큰, 회원 가입, 토큰 리프레시, 게시글 목록 등도 공개
                        .requestMatchers("/api/auth/csrf-token").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/posts").permitAll()

                        // 그 외 요청은 현재는 개발 편의를 위해 모두 허용
                        .anyRequest().permitAll()
                )

                // 2) CSRF 설정 (Cookie 방식)
//              .csrf(csrf -> csrf.disable()) // 테스트 용도로는 끄기 권장
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/auth/logout")   // 로그아웃은 CSRF 예외
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
                )

                // 3) form login 사용 (JWT 발급용 로그인 엔드포인트)
                .formLogin(login -> login
                        .loginProcessingUrl("/api/auth/login")
                        .successHandler(jwtLoginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                )

                // 4) JWT 기반 로그아웃
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .addLogoutHandler(jwtLogoutHandler)
                        .logoutSuccessHandler(
                                new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT))
                )

                // 5) 예외 처리
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                        .accessDeniedHandler(http403ForbiddenAccessDeniedHandler)
                )

                // 6) 세션 정책: 완전 Stateless
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

//              // 7) JSON 로그인 필터 설정 (폼 로그인 → JWT 로그인으로 교체)
//                .with(
//                        new JsonUsernamePasswordAuthenticationFilter.Configurer(objectMapper),
//                        configurer -> configurer
//                                .loginProcessingUrl("/api/auth/login")
//                                // 로그인 성공 시 JWT 발급
//                                .successHandler(jwtLoginSuccessHandler)
//                                // 로그인 실패 응답 (기존 LoginFailureHandler 대신 JSON 실패 핸들러 사용)
//                                .failureHandler(loginFailureHandler)
//                )

                // 8) 매 요청마다 Authorization 헤더의 JWT를 검증하는 필터
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        // JsonUsernamePasswordAuthenticationFilter.class // json 쓰고 싶을때
                        UsernamePasswordAuthenticationFilter.class // 미션 요구사항
                );

        // 9) CORS 설정
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
//              .role(Role.CHANNEL_MANAGER.name())
//              .implies(Role.USER.name())
                .build();
    }

    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(
            RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);
        return handler;
    }

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(
//            UserDetailsService userDetailsService,
//            PasswordEncoder passwordEncoder,
//            RoleHierarchy roleHierarchy
//    ) {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder);
//        provider.setAuthoritiesMapper(new RoleHierarchyAuthoritiesMapper(roleHierarchy));
//        return provider;
//    }

    @Bean
    public CommandLineRunner debugFilterChain(SecurityFilterChain filterChain) {
        return args -> {
            int filterSize = filterChain.getFilters().size();
            List<String> filterNames = IntStream.range(0, filterSize)
                    .mapToObj(idx -> String.format("\t[%s/%s] %s",
                            idx + 1,
                            filterSize,
                            filterChain.getFilters().get(idx).getClass()))
                    .toList();
            log.debug("Debug Filter Chain...\n{}", String.join(System.lineSeparator(), filterNames));
        };
    }

    @Bean
    public JwtRegistry jwtRegistry(JwtTokenProvider jwtTokenProvider) {
        return new InMemoryJwtRegistry(1, jwtTokenProvider);
    }
}
