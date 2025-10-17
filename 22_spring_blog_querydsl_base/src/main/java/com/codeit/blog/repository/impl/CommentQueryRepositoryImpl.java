package com.codeit.blog.repository.impl;

import com.codeit.blog.dto.comment.CommentFlatResponse;
import com.codeit.blog.entity.QComment;
import com.codeit.blog.entity.QPost;
import com.codeit.blog.entity.QUser;
import com.codeit.blog.repository.CommentQueryRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    private static final QComment c = QComment.comment;
    private static final QPost p = QPost.post;
    private static final QUser u = QUser.user;

    @Override
    public Slice<CommentFlatResponse> findFlatByPostId(Long postId, Pageable pageable) {
        //  where (soft delete 제외)
        BooleanBuilder where = new BooleanBuilder();
        where.and(c.deletedAt.isNull());
        where.and(p.deletedAt.isNull());
        where.and(p.id.eq(postId));

        //  정렬
        OrderSpecifier<?> order = resolveOrder(pageable);

        //  조회(size+1)
        List<CommentFlatResponse> rows = queryFactory
                .select(Projections.constructor(
                        CommentFlatResponse.class,
                        c.id,
                        c.content,
                        c.createdAt,
                        p.id.as("postId"),
                        u.id.as("userId"),
                        u.nickname.as("userNickname")
                ))
                .from(c)
                .join(c.post, p)
                .join(c.author, u)
                .where(where)
                .orderBy(order)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        boolean hasNext = rows.size() > pageable.getPageSize();
        if (hasNext) rows.remove(rows.size() - 1);

        return new SliceImpl<>(rows, pageable, hasNext);
    }

    @Override
    public Slice<CommentFlatResponse> findFlatByUserId(Long userId, Pageable pageable) {
        //  where (soft delete 제외)
        BooleanBuilder where = new BooleanBuilder();
        where.and(c.deletedAt.isNull());
        where.and(p.deletedAt.isNull());
        where.and(u.id.eq(userId));

        //  정렬
        OrderSpecifier<?> order = resolveOrder(pageable);

        //  조회(size+1)
        List<CommentFlatResponse> rows = queryFactory
                .select(Projections.constructor(
                        CommentFlatResponse.class,
                        c.id,
                        c.content,
                        c.createdAt,
                        p.id.as("postId"),
                        u.id.as("userId"),
                        u.nickname.as("userNickname")
                ))
                .from(c)
                .join(c.post, p)
                .join(c.author, u)
                .where(where)
                .orderBy(order)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        boolean hasNext = rows.size() > pageable.getPageSize();
        if (hasNext) rows.remove(rows.size() - 1);

        return new SliceImpl<>(rows, pageable, hasNext);
    }


    
    private OrderSpecifier<?> resolveOrder(Pageable pageable) {
        // 기본 정렬: 최신 댓글 먼저
        if (pageable == null || pageable.getSort().isUnsorted()) {
            return c.id.desc();
        }
        
        Sort.Order so = pageable.getSort().stream()
                .findFirst()
                .orElse(Sort.Order.desc("id"));

        boolean asc = so.isAscending();
        String key = so.getProperty();

        return switch (key) {
            case "id" -> asc ? c.id.asc() : c.id.desc();
            case "createdAt" -> asc ? c.createdAt.asc() : c.createdAt.desc();
            default -> c.id.desc();
        };
    }
}
