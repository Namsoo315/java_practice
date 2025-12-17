package com.codeit.elastic.mapper;

import com.codeit.elastic.document.PostDocument;
import com.codeit.elastic.dto.PostResponse;
import com.codeit.elastic.dto.PostSearchResponse;
import com.codeit.elastic.dto.PostSearchRow;
import com.codeit.elastic.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface PostMapper {

    // RDB Entity -> REST Response
    @Mapping(target = "postId", source = "id")
    @Mapping(target = "category", expression = "java(post.getCategory().name())")
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "authorUsername", source = "author.username")
    @Mapping(target = "authorNickname", source = "author.nickname")
    @Mapping(target = "deleted", expression = "java(post.isDeleted())")
    PostResponse toPostResponse(Post post);

    @Mapping(target = "category", source = "category")
    PostSearchResponse toSearchResponse(PostDocument doc);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category")
    PostDocument toDocument(PostSearchRow row);
}
