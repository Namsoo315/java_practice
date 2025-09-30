package com.codeit.blog.service;

import com.codeit.blog.dto.user.UserResponse;
import com.codeit.blog.mapper.UserMapper;
import com.codeit.blog.repository.UserRepository;
import com.codeit.blog.storage.FileStorage;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// mock을 통한 테스트 수행 코드

@ExtendWith(MockitoExtension.class) // MockitoExtension 환경에서 테스트 하는 어노테이션
public class UserServiceTest2 {

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

  @Test
  void findAllTest(){
    List<UserResponse> all = userService.findAll();
    System.out.println(all);

    assert !all.isEmpty();

  }
}
