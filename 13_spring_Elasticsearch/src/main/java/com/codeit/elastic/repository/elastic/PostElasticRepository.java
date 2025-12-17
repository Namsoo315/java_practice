package com.codeit.elastic.repository.elastic;

import com.codeit.elastic.document.PostDocument;
import java.util.Optional;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostElasticRepository extends ElasticsearchRepository<PostDocument, String> {
  
  // RDB 갱신이 발생하면 갱신이나 삭제를 위해
  Optional<PostDocument> findFirstByPostId(Long postId);
  void deleteByPostId(Long postId);
}
