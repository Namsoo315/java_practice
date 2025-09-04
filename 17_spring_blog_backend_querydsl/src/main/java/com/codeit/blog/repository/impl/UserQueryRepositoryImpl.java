package com.codeit.blog.repository.impl;

import com.codeit.blog.entity.QUser;
import com.codeit.blog.entity.User;
import com.codeit.blog.repository.UserQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

  private final JPAQueryFactory queryFactory;
  private static final QUser u = QUser.user;

  @Override
  public Optional<User> findByIdQ(Long id) {
    // SELECT * FROM users;
    User user = queryFactory
        .selectFrom(u)  //전체 컬럼 조회
        .where(u.id.eq(id))   //WHERE users.id = id
        .fetchOne();

    return Optional.ofNullable(user);
  }

  @Override
  public List<User> findAllOrderByCreatedDesc() {
    return queryFactory
        .selectFrom(u)
        .orderBy(u.createdAt.desc(), u.id.desc(), u.email.asc())    //createdAt 기준으로 내림차순
        .fetch();
  }

  @Override
  public Optional<User> findByLoginId(String usernameOrEmail) {
    if(usernameOrEmail == null || usernameOrEmail.isEmpty()) {
      return Optional.empty();
    }

    User user = queryFactory
        .selectFrom(u)
        .where(u.username.eq(usernameOrEmail).or(u.email.eq(usernameOrEmail)))
        .fetchOne();    //username, email이나 둘중 하나가 들어오면 이런식으로 처리할 수 있다.

    return Optional.ofNullable(user);
  }
}
