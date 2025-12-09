package com.codeit.network.service;

import com.codeit.network.dto.user.MeResponse;
import com.codeit.network.dto.user.UserLoginRequest;
import com.codeit.network.dto.user.UserLoginResponse;
import com.codeit.network.entity.User;
import com.codeit.network.entity.UserRole;
import com.codeit.network.mapper.UserMapper;
import com.codeit.network.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserLoginResponse login(UserLoginRequest request, HttpServletRequest httpRequest) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!user.getPassword().equals(request.password())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        List<GrantedAuthority> authorities =
                user.getRole() == UserRole.ADMIN
                        ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                        : List.of(new SimpleGrantedAuthority("ROLE_USER"));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, authorities);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);

        return userMapper.toLoginResponse(user);
    }

    public MeResponse me() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() == null || !(context.getAuthentication().getPrincipal() instanceof User user)) {
            throw new IllegalStateException("로그인된 사용자가 없습니다.");
        }
        return userMapper.toMeResponse(user);
    }
}
