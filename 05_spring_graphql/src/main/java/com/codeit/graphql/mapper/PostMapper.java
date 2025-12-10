package com.codeit.graphql.mapper;

import com.codeit.graphql.dto.post.PostDto;
import com.codeit.graphql.dto.post.PostPageDto;
import com.codeit.graphql.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    // 엔티티 -> DTO
    @Mapping(source = "author.id", target = "authorId")
    PostDto toDto(Post post);

    List<PostDto> toDtoList(List<Post> posts);

    // Page<Post> -> PostPageDto
    default PostPageDto toPageDto(Page<Post> page) {
        List<PostDto> contentDtos = toDtoList(page.getContent());
        return new PostPageDto(
                contentDtos,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
