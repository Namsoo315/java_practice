package com.codeit.elastic.repository;

import com.codeit.elastic.dto.PostSearchRow;
import com.codeit.elastic.entity.QPost;
import com.codeit.elastic.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    private final QPost post = QPost.post;
    private final QUser user = QUser.user;

    public List<PostSearchRow> findAllForIndexing() {
        return queryFactory
                .select(Projections.constructor(
                        PostSearchRow.class,
                        post.id,
                        post.title,
                        post.content,
                        post.tags,
                        post.category.stringValue(),
                        user.id,
                        user.username,
                        user.nickname,
                        post.createdAt
                ))
                .from(post)
                .join(post.author, user)
                .where(post.deletedAt.isNull())
                .fetch();
    }

    public Optional<PostSearchRow> findOneForIndexing(Long postId) {
        return Optional.ofNullable(
                queryFactory
                        .select(Projections.constructor(
                                PostSearchRow.class,
                                post.id,
                                post.title,
                                post.content,
                                post.tags,
                                post.category.stringValue(),
                                user.id,
                                user.username,
                                user.nickname,
                                post.createdAt
                        ))
                        .from(post)
                        .join(post.author, user)
                        .where(post.id.eq(postId), post.deletedAt.isNull())
                        .fetchOne()
        );
    }
}
