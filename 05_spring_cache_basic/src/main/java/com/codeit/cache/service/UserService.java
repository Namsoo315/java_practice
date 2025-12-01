package com.codeit.cache.service;

import com.codeit.cache.dto.user.UserCreateRequest;
import com.codeit.cache.dto.user.UserDto;
import com.codeit.cache.dto.user.UserUpdateRequest;
import com.codeit.cache.entity.Role;
import com.codeit.cache.entity.User;
import com.codeit.cache.mapper.UserMapper;
import com.codeit.cache.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@CacheConfig(cacheNames = "users")
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // 생성 : 전체 목록 캐시 비우기
//    @CacheEvict(allEntries = true)  // 무효화 옵션, @CacheEvict(allEntries = true) : 모든 캐시 초기화
    @CacheEvict(key = "'all'")
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

    @Cacheable
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        log.info("DB에서 사용자 조회: id={}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return userMapper.toDto(user);
    }

    // 전체 조회 캐싱
//    @Cacheable
    @Cacheable(cacheNames = "users", key = "'all'")    // 해당 서비스에서 사용할 공통 캐시 이름 (@CacheConfig 사용 X)
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        log.info("DB에서 전체 사용자 조회");
        return userMapper.toDtoList(userRepository.findAll());
    }


    @Caching(put = {
        @CachePut(key = "#id")
        // users[id] 갱신
    },
        // users['all'] 삭제
        evict = {
            @CacheEvict(key = "'all'")}
    )
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

    @Caching(evict = {
        @CacheEvict(key = "#id"),
        @CacheEvict(key = "'all'")
    })
    @Transactional
    public void delete(Long id) {
        log.info("사용자 삭제 시작: id={}", id);
        userRepository.deleteById(id);
        log.info("사용자 삭제 완료: id={}", id);
    }
}