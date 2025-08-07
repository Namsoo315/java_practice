package com.codeit.mvc.controller;

import com.codeit.mvc.domain.User;
import com.codeit.mvc.repository.UserRepository;
import com.codeit.mvc.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
// class 레벨에서 /users 경로를 붙여주는 역할
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // @RequestMapping : 가장 기본적인 요청 처리 어노테이션 최초의 url 맵핑 어노테이션
    // /index.do <- 레거시한 표현 스프링 프레임워크에서 사용했던 패턴 중 하나 (동적인 확장자)
    @RequestMapping(value = {"/index.do", "/index", "/"}, method = RequestMethod.GET)
    public String index(/*파라미터 주입 자리*/) {
        return "users/index";
    }
    // ------------------- 헨들러 메서드 스타일 정리 --------------------//
    // 1. 서블릿 스타일
    // 장점 : 서블릿 프로젝트 호환용
    // 단점 : 복잡함, 거의 사용안함.

    // get 처리
    @GetMapping("/userServlet")
    public String userServlet() {
        return "users/userEnrollForServlet";
    }

    @PostMapping("/userServlet")
    public String userServlet(HttpServletRequest request, HttpServletResponse response
            , HttpSession httpSession) {
        User user = new User();
        user.setId(Long.parseLong(request.getParameter("id")));
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setRoles(request.getParameter("roles"));
        user.setDevLang(Arrays.asList(request.getParameter("devLang").split(",")));

        // model을 대신하는 방법
        request.setAttribute("user", user);

        // 쿠키 활용법
        Cookie cookie = new Cookie("saveUsername", user.getUsername());
        cookie.setMaxAge(60 * 60);  // 1 시간
        response.addCookie(cookie);

        // 세션 가져오는 법
        request.getSession().setAttribute("username", user.getUsername());

        // 세션 가져오는 방법 2, spring 에서 세션 사용 하는 방법 중 하나.
        httpSession.setAttribute("username", user.getUsername());

        return "users/userView";
    }

}
