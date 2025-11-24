package com.codeit.jwt.security.jwt;

import com.codeit.jwt.dto.user.UserDto;
import com.codeit.jwt.security.BlogUserDetails;
import com.codeit.jwt.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


// Json 기반으로 인증이 성공하였을 때 토큰을 발행시켜줄 Handler
@Component
@RequiredArgsConstructor
public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;
  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    // 1) 인증된 사용자 정보 추출
    BlogUserDetails userDetails = (BlogUserDetails) authentication.getPrincipal();
    UserDto userDto = userDetails.getUserDto();

    // 2) Access Token 생성 (JWTObject)
    JwtObject jwtObject = jwtUtil.generateJWTObject(userDto);
    String accessToken = jwtObject.token();

    // 3) Json 응답
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
//    response.getWriter().write(objectMapper.writeValueAsString(jwtObject)); // json 객체로 (원래 이렇게 함)
    response.getWriter().write(objectMapper.writeValueAsString(accessToken)); // 스프린트 미션용 (json으로 accessToken이 날아가게 할꺼임)
  }
}
