package com.codeit.cache.mapper;


import com.codeit.cache.dto.post.PostDto;
import com.codeit.cache.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PostMapper {

    @Mapping(source = "author", target = "author")
    PostDto toDto(Post post);

    List<PostDto> toDtoList(List<Post> posts);
}
