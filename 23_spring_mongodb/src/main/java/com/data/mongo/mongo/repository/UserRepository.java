package com.data.mongo.mongo.repository;

import com.data.mongo.mongo.document.UserDocument;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<UserDocument, String> {

  Optional<UserDocument> findByUsername(String username);
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
  List<UserDocument> findByNicknameContainsIgnoreCase (String keyword);
  Page<UserDocument> findByBirthdayBetween(LocalDate birthdayAfter, LocalDate birthdayBefore);

  @Query(value = "{'nickname' :  {$regex :  ?0, $options :  'i'}}",    // value = json 기반 쿼리
          fields = "{'username' :  1, 'email' :  1}")  // fields = 보여줄 값, 프로젝션
  List<UserDocument> searchNickname(String keyword, Sort sort);
}
