package com.codeit.jwt.mapper;


import com.codeit.jwt.dto.post.PostDto;
import com.codeit.jwt.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PostMapper {

    @Mapping(source = "author", target = "author")
    PostDto toDto(Post post);
}
