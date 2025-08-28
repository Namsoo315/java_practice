package com.codeit.data;

import com.codeit.data.entity.Category;
import com.codeit.data.entity.Post;
import com.codeit.data.entity.User;
import com.codeit.data.repository.PostRepository;
import com.codeit.data.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PostRepository postRepository;

  @Test
  void userRepository() {
    Optional<User> result = userRepository.findByUsername("test01");
    System.out.println("@@@@userRepositoryTest : "  +result.get());
    assert result.get().getUsername().equals("test01");    //검증용

// save
    User user = User.builder().username("admin")
        .email("admin@email.com").password("1234").nickname("관리자").build();
    User result1  = userRepository.save(user);
    System.out.println("@@@@userRepositoryTest : " + result1);
    assert result1.getUsername().equals("admin");

    // update
    result1.setNickname("학생");
    User result2 =  userRepository.save(result1);
    System.out.println("@@@@userRepositoryTest - update : " + result1);
    assert result2.getNickname().equals("학생");

    // delete
    userRepository.delete(result1);
    assert userRepository.findByUsername("admin").isEmpty();

  }

  @Test
  void setPostRepositoryTest() {
    // insert
    Post post = Post.builder()
        .title("시험글 입니다").content("시험 내용입니다")
        .tags("spring").category(Category.EDUCATION)
        .author(User.builder().id(1L).build())
        .build();

    Post result1 = postRepository.save(post);
    System.out.println("postRepositoryTest : "  +result1);
    assert result1.getTitle().equals("시험글 입니다");

    // searchNickName
    List<Post> result2 = postRepository.findByAuthor_NicknameContaining("판교");
    System.out.println("postRepositoryTest : " + result2);
    assert !result2.isEmpty();
  }


}
