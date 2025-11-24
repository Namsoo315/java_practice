package com.codeit.jwt.service;

import com.codeit.jwt.dto.user.UserCreateRequest;
import com.codeit.jwt.dto.user.UserResponse;
import com.codeit.jwt.dto.user.UserUpdateRequest;
import com.codeit.jwt.entity.User;
import com.codeit.jwt.mapper.UserMapper;
import com.codeit.jwt.repository.UserRepository;
import com.codeit.jwt.security.jwt.JwtRegistry;
import com.codeit.jwt.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtRegistry<Long> jwtRegistry;

    @Transactional
    public UserResponse create(UserCreateRequest newUser) {
        User user = userMapper.toUser(newUser);
        if (user.getId() != null) {
            throw new RuntimeException("잘못된 사용자 파라미터입니다.");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("이미 사용 중인 사용자명입니다: " + user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("이미 사용 중인 이메일입니다: " + user.getEmail());
        }
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("사용자 저장 중 오류가 발생했습니다.");
        }

        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. id=" + id));
        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. username=" + username));
        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userMapper.toUserResponseList(userRepository.findAll());
    }


    @PreAuthorize("principal.userDto.id == #id")
    @Transactional
    public UserResponse update(Long id, UserUpdateRequest patch) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. id=" + id));

        if (patch.password() != null) {
            user.setPassword(passwordEncoder.encode(patch.password()));
        }
        if (patch.email() != null) {
            user.setEmail(patch.email());
        }
        User saved = userRepository.save(user);

        // 세션 무효화
//        sessionManager.invalidateSessionsByUserId(id);
        jwtRegistry.invalidateJwtInformationByUserId(id);
        return userMapper.toResponse(saved);
    }

    @PreAuthorize("principal.userDto.id == #id")
    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. id=" + id));
        userRepository.delete(user);

        // 세션 무효화
//        sessionManager.invalidateSessionsByUserId(id);
        jwtRegistry.invalidateJwtInformationByUserId(id);
    }
}
