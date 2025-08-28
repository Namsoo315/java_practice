package com.codeit.data.repository;

import com.codeit.data.entity.User;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
  //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
  // username(사용자 아이디) User 찾아오기(회원가입 시 id 체크할 때)
  Optional<User> findByUsername(String username);

  // email로 찾기
  List<User> findByEmail(String email);

  // 중복 체크
  boolean existsByUsername(String username);

  // 회원 가입 날짜 체크
  Collection<User> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}
