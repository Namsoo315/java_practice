package com.codeit.cache.service;

import com.codeit.cache.dto.user.UserCreateRequest;
import com.codeit.cache.dto.user.UserDto;
import com.codeit.cache.dto.user.UserUpdateRequest;
import com.codeit.cache.entity.Role;
import com.codeit.cache.entity.User;
import com.codeit.cache.mapper.UserMapper;
import com.codeit.cache.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "users")
public class UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // 생성 : 전체 목록 캐시 비우기
//    @CacheEvict(allEntries = true) // 무효화 옵션, @CacheEvict(allEntries = true) : 모든 캐시 초기화
    @CacheEvict(key = "'all'") // 전체만 초기화
    @Transactional
    public UserDto create(UserCreateRequest request) {
        log.info("사용자 생성 시작: {}", request.username());

        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())  // 강의용, 실제는 인코딩
                .role(Role.USER)
                .build();

        userRepository.save(user);
        log.info("사용자 생성 완료: id={}", user.getId());
        return userMapper.toDto(user);
    }


    //    @Cacheable // 키를 자동으로 생성하여 캐시 활성화 됨!
    @Cacheable(key = "#id") // 간단한 키 설정 방법!, #id : 인자인 id를 가져와 key로 활용
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        log.info("DB에서 사용자 조회: id={}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return userMapper.toDto(user);
    }

    // 전체 조회
//    @Cacheable // 가장 간단한 캐시 활용 방법
    @Cacheable(value = "users", key = "'all'") // value : 캐시의 이름, key : 해당 캐시에서 사용할 고정키('all')
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        log.info("DB에서 전체 사용자 조회");
        return userMapper.toDtoList(userRepository.findAll());
    }

    // 수정 : 단건 캐시는 갱신, 전체 목록 캐시는 삭제
    @Caching(
            put = {
                    // users[#id] 갱신
                    @CachePut(key = "#id"),
            },
            evict = {
                    // users['all'] 삭제
                    @CacheEvict(key = "'all'")
            }
    )
//    @CachePut(key = "#id")
//    @CacheEvict(key = "'all'")
    @Transactional
    public UserDto update(Long id, UserUpdateRequest request) {
        log.info("사용자 수정 시작: id={}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(request.password());

        log.info("사용자 수정 완료: id={}", id);
        return userMapper.toDto(user);
    }

    // 삭제 : 단건 삭제, 전체도 삭제
    @Caching(evict = {
            @CacheEvict(key = "#id"),
            @CacheEvict(key = "'all'"),
    })
    @Transactional
    public void delete(Long id) {
        log.info("사용자 삭제 시작: id={}", id);
        userRepository.deleteById(id);
        log.info("사용자 삭제 완료: id={}", id);
    }


    // 로그인: DB 검증 + session 캐시에 저장 (Redis "session" 캐시)
    @CachePut(cacheNames = "session", key = "#username")
    @Transactional(readOnly = true)
    public UserDto login(String username, String password) {
        log.info("로그인 시도: username={}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        var authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return userMapper.toDto(user);
    }

    // 현재 로그인한 사용자 조회: 우선 session 캐시에서 조회
    @Cacheable(cacheNames = "session", key = "#username")
    @Transactional(readOnly = true)
    public UserDto findByUsername(String username) {
        log.info("세션 캐시 미스, DB에서 사용자 조회: username={}", username);

        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }


    // 세션 로그아웃: Redis "session" 캐시에서 제거
    @CacheEvict(cacheNames = "session", key = "#username")
    public void logout(String username) {
        log.info("로그아웃: username={}", username);
    }

}
