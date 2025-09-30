package com.codeit.blog.service;

import com.codeit.blog.dto.user.UserCreateRequest;
import com.codeit.blog.dto.user.UserResponse;
import com.codeit.blog.entity.User;
import com.codeit.blog.mapper.UserMapper;
import com.codeit.blog.repository.UserRepository;
import com.codeit.blog.storage.FileStorage;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
// mock을 통한 테스트 수행 코드

@ExtendWith(MockitoExtension.class) // MockitoExtension 환경에서 테스트 하는 어노테이션
public class UserServiceTest {

  // Mock을 만들 때 UserService를 테스트할 때 필요한 의존성을 Mock으로 만드는 영역
  // 해당 객체르를 실체 객체가 아닌 Mock으로 만들어 활용할 때 사용하는 어노테이션
  @Mock
  private UserRepository userRepository;
  @Mock
  private UserMapper userMapper;
  @Mock
  private FileStorage fileStorage;

  @InjectMocks  // Autowired가 아닌 InjectMocks 사용해야함.
  private UserService userService;

  // 테스트를 위한 객체나 변수들 선언
  private Long userId;
  private String username;
  private String email;
  private String password;

  private User user;                 // 매핑된 엔티티(저장 전)
  private UserResponse userResponse; // 서비스 반환 DTO

  @BeforeEach
  void setUp() {
    userId = 1L;
    username = "test01";
    email = "test01@gmail.com";
    password = "1234";

    user = User.builder().username(username).password(password).email(email).nickname("스프링 마스터")
        .birthday(LocalDate.of(1990, 1, 1)).hasAvatar(false).build();

    userResponse = new UserResponse(userId, username, password, email, "스프링 마스터",
        LocalDate.of(1990, 1, 1), Instant.now(), Instant.now());
  }

  @Test
  @DisplayName("사용자 생성 테스트(아바타 없음)")
  void create_user_without_avatar(){
    // given
    UserCreateRequest req = new UserCreateRequest(
        username, password, email, "스프링마스터",
        LocalDate.of(1990, 1, 1));

    // given 모의 설정(Mock 시나리오 설정)
    // User user = userMapper.toUser(newUser);
    given(userMapper.toUser(any())).willReturn(user);

    // userRepository.existsByUsername(user.getUsername())
    given(userRepository.existsByUsername(username)).willReturn(false);

    // userRepository.save(user);
    given(userRepository.save(any())).willReturn(user);

    // userMapper.toUserDetailResponse(user);
    given(userMapper.toUserDetailResponse(any())).willReturn(userResponse);

    // when
    UserResponse result = userService.create(req, null);

    // then
    assertThat(result).isEqualTo(userResponse);
    verify(userRepository, times(1)).save(any());
    verify(userMapper, times(1)).toUser(any());
  }

  @Test
  @DisplayName("사용자 생성 테스트(아바타 있음)")
  void create_user_withAvatar(){
    // given
    UserCreateRequest req = new UserCreateRequest(
        username, password, email, "스프링마스터",
        LocalDate.of(1990, 1, 1));
    MultipartFile avatar = new MockMultipartFile(
        "avatar", "a.png", "image/png", "png".getBytes());

    // given 모의 설정(Mock 시나리오 설정)
    // User user = userMapper.toUser(newUser);
    given(userMapper.toUser(any())).willReturn(user);

    // userRepository.existsByUsername(user.getUsername())
    given(userRepository.existsByUsername(username)).willReturn(false);

    // userRepository.save(user);
    given(userRepository.save(any())).willReturn(user);

    // userMapper.toUserDetailResponse(user);
    given(userMapper.toUserDetailResponse(any())).willReturn(userResponse);

    // when
    UserResponse result = userService.create(req, avatar);

    // then
    assertThat(result).isEqualTo(userResponse);
    verify(userRepository, times(1)).save(any());
    verify(userMapper, times(1)).toUser(any());

    // save로직이 동작했는지 검증
    verify(fileStorage).saveAvatarFile(eq(username), any(MultipartFile.class));
  }


}
