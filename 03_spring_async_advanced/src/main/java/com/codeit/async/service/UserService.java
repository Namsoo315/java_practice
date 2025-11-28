package com.codeit.async.service;

import com.codeit.async.entiry.User;
import com.codeit.async.event.UserLoginFailedEvent;
import com.codeit.async.event.UserLoginSuccessEvent;
import com.codeit.async.event.UserRegisteredEvent;
import com.codeit.async.repository.UserRepository;
import com.codeit.async.security.AsyncUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final SimpleLogService simpleLogService;

    @Transactional
    public User register(String username, String email, String rawPassword) {
        User user = User.builder()
                .username(username)
                .email(email)
                .password(rawPassword)
                .role("USER")
                .build();

        try {
            User saved = userRepository.save(user);
            // 회원가입 이벤트 발행
            eventPublisher.publishEvent(new UserRegisteredEvent(saved.getId(),
                    saved.getUsername(), saved.getEmail(), "USER"));
            simpleLogService.asyncLog("## 사용자 생성 완료 UserId : "+ saved.getId());

            return saved;
        } catch (Exception e) {
            throw new RuntimeException("DB 에러 발생");
        }
    }

    @Transactional(readOnly = true)
    public User login(String username, String rawPassword) {

        // 1) 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다"));

        // 2) 비밀번호 불일치 → 실패 이벤트 + 예외
        if (!user.getPassword().equals(rawPassword)) {
            eventPublisher.publishEvent(new UserLoginFailedEvent(username,"비밀번호 오류"));
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다");
        }

        // 3) 로그인 성공 이벤트 발행
        simpleLogService.asyncLog("## 로그인 성공, UserId : " + user.getId());
        eventPublisher.publishEvent(new UserLoginSuccessEvent(user.getId(),username));

        // 시큐리티 직접 처리 방법
        AsyncUserDetails userDetails = AsyncUserDetails.from(user);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return user;
    }

}

