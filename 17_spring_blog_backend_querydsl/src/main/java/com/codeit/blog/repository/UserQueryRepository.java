package com.codeit.blog.repository;

import com.codeit.blog.entity.User;
import java.util.List;
import java.util.Optional;

// Query DSL에서 사용할 인터페이스
public interface UserQueryRepository {
  Optional<User> findByIdQ(Long id);
  List<User> findAllOrderByCreatedDesc();
  Optional<User> findByLoginId(String usernameOrEmail);
}
