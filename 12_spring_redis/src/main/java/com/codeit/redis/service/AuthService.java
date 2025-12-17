package com.codeit.redis.service;

import com.codeit.redis.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private static final List<User> USERS = List.of(
            new User(1L, "user1", "1234"),
            new User(2L, "user2", "1234"),
            new User(3L, "user3", "1234"),
            new User(4L, "user4", "1234"),
            new User(5L, "user5", "1234")
    );

    public User login(String username, String password) {
        return USERS.stream()
                .filter(u ->
                        u.getUsername().equals(username) &&
                                u.getPassword().equals(password)
                )
                .findFirst()
                .orElse(null);
    }
}
