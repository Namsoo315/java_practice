package com.codeit.rest.service;

import com.codeit.rest.dto.user.UserCreateRequest;
import com.codeit.rest.dto.user.UserUpdateRequest;
import com.codeit.rest.entity.User;

import java.util.List;

public interface UserService {
    User create(UserCreateRequest newUser);

    User findById(Long id);

    User findByUsername(String username);

    List<User> findAll();

    User update(Long id, UserUpdateRequest updateRequest);

    User updateAll(Long id, UserCreateRequest newUser);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
