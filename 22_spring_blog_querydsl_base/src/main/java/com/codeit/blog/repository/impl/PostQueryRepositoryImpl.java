package com.codeit.blog.repository.impl;

import com.codeit.blog.dto.post.PostFlatDetailDto;
import com.codeit.blog.dto.post.PostFlatDto;
import com.codeit.blog.dto.post.PostSearchRequest;
import com.codeit.blog.entity.*;
import com.codeit.blog.repository.PostQueryRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    // join 할때 필요할 QType을 미리 선언
    private static final QPost p = QPost.post;
    private static final QUser u = QUser.user;
    private static final QComment c = QComment.comment;
    private static final QBinaryContent b = QBinaryContent.binaryContent;
    private static final QPostLike l = QPostLike.postLike;

    private static final QUser postAuthor = new QUser("postAuthor");
    private static final QUser commentAuthor = new QUser("commentAuthor");

    @Override
    public Optional<Post> findByIdQ(Long id) {
        return null;
    }

    @Override
    public List<PostFlatDto> findAllFlat() {
        return null;
    }

    @Override
    public List<Post> search(String title, String content,
                             String author, Category category,
                             String tag, String commentContent,
                             String commentAuthorStr) {
        return null;
    }

    @Override
    public Page<Post> searchPage(String title, String content, String author,
                                 Category category, String tag,
                                 String commentContent, String commentAuthorStr,
                                 Pageable pageable) {
        return null;
    }

    // 5. 복합 검색 + 페이징 + pageable + soft delete 반영 + flat화
    // 최종 블로그 Slice 처리
    @Override
    public Slice<PostFlatDetailDto> searchPageSortedFlat(PostSearchRequest req) {
        return null;
    }


}
