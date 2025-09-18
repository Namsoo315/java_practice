package com.codeit.blog.repository;

import com.codeit.blog.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest(classes = {UserRepositoryTest.class})
@DataJpaTest    // jpa에 관련 최소 bean만 load하는 어노테이션
@ActiveProfiles("test") // test yml 활성화
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager em;   // 트랜잭션을 컨르롤 하기. 위한 manager

    // 고정 픽스처 생성
    private User createUser(String username, String email){
        return User.builder()
                .username(username)
                .password("password123")
                .email(email)
                .nickname("스프링 마스터")
                .hasAvatar(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .build();
    }

    @Test
    @DisplayName("username으로 사용자 조회")
    void findByUsernameTest() {
        // given : 테스트셋 생성
        userRepository.save(createUser("codeit", "codeit@gmail.com"));
        em.flush(); // 강제 commit 수행
        em.clear(); // 캐시 비우는 명령

        // when : 테스트 대상
        Optional<User> found = userRepository.findByUsername("codeit");

        // then : 테스트 검증
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("codeit");
        assertThat(found.get().getEmail()).isEqualTo("codeit@gmail.com");
    }

    @Test
    @DisplayName("없는 Username 조회시 빈 Optional 출력")
    void findByUsernameTest2(){
        assertThat(userRepository.findByUsername("no_user")).isEmpty();
    }

    @Test
    @DisplayName("email로 사용자 조회")
    void findByEmailTest() {
        //given
        userRepository.save(createUser("codeit2", "codeit2@gmail.com"));
        em.flush(); em.clear();

        // when
        Optional<User> found = userRepository.findByEmail("codeit2@gmail.com");

        //then
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("codeit2");
        assertThat(found.get().getEmail()).isEqualTo("codeit2@gmail.com");
    }

    @Test
    @DisplayName("username 중복 여부 확인")
    void findByUsernameTest3(){
        // given
        userRepository.save(createUser("codeit3", "codeit3@gmail.com"));

        em.flush(); em.clear();

        // when
        Optional<User> found = userRepository.findByUsername("codeit3");

        //then
        assertThat(found).isPresent();
        assertThat(userRepository.existsByUsername("codeit3")).isTrue();
        assertThat(userRepository.existsByEmail("codeit3@gmail.com")).isTrue();
    }

    @Test
    @DisplayName("email 중복 여부 확인")
    void existsByEmail() {
        // given
        userRepository.save(createUser("codeit4", "codeit4@example.com"));
        em.flush(); em.clear();

        // then
        assertThat(userRepository.existsByEmail("codeit4@example.com")).isTrue();
        assertThat(userRepository.existsByEmail("none@example.com")).isFalse();
    }



}
