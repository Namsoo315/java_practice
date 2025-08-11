package com.codeit.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.codeit.web.dto.UserDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginForm(@CookieValue(value = "savedUsername", required = false) String savedUsername) {
		System.out.println("저장된 cookie 값 : " + savedUsername);
		return "users/loginForm";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute UserDTO userDTO, HttpServletResponse response, HttpSession session) {

		// username을 쿠키로 저장 시킬것
		Cookie cookie = new Cookie("savedUsername", userDTO.getUsername());
		cookie.setMaxAge(60 * 60 * 24);
		cookie.setPath("/");
		// cookie.setHttpOnly(true);
		// cookie.setSecure(ture);
		response.addCookie(cookie);

		// 세션에 로그인된 정보 저장.
		session.setAttribute("loginUser", userDTO);

		return "redirect:/login/success";
	}

	@GetMapping("/login/success")
	public String loginSuccess(HttpSession session, Model model) {
		UserDTO userDTO = (UserDTO) session.getAttribute("loginUser");
		model.addAttribute("user", userDTO);

		return "users/loginSuccess";
	}

	@GetMapping("/login/status")
	public String loginStatus(Model model, @SessionAttribute(name = "loginUser", required = false) UserDTO user) {
		model.addAttribute("user", user);
		return "users/loginStatus";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session, HttpServletResponse response) {
		System.out.println(session.getId());
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(session.getCreationTime()));
		// 세션에서 사용자 제거하기
		session.removeAttribute("loginUser");	// 하나만 제거할 때 활용
		session.invalidate();	//세션 자체를 삭제 !! 실제 로그아웃 할 때는 이걸 활용

		// Cookie savedUsername 제거하기
		Cookie cookie = new Cookie("savedUsername", null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);

		return "redirect:/login";
	}
}
