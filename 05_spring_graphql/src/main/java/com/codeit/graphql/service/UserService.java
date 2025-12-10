package com.codeit.graphql.service;

import com.codeit.graphql.dto.user.UserDto;
import com.codeit.graphql.entity.User;
import com.codeit.graphql.mapper.UserMapper;
import com.codeit.graphql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

}
