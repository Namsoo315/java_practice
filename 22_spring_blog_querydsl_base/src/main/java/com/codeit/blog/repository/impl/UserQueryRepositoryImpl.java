package com.codeit.blog.repository.impl;

import com.codeit.blog.entity.QUser;
import com.codeit.blog.entity.User;
import com.codeit.blog.repository.UserQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory queryFactory;
    private static final QUser u = QUser.user;

    @Override
    public Optional<User> findByIdQ(Long id) {
       return null;
    }

    @Override
    public List<User> findAllOrderByCreatedDesc() {
        return null;
    }

    @Override
    public Optional<User> findByLoginId(String usernameOrEmail) {
        return null;
    }
}
