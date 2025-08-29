package com.codeit.data.mapper;

import com.codeit.data.dto.post.PostCreateRequest;
import com.codeit.data.entity.Post;
import com.codeit.data.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

  @Mapping(target = "id", ignore = true)  //특정 맵핑을 무시할 떄 사용하는 어노테이션
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)

  Post toPost(PostCreateRequest request, User author);
}
