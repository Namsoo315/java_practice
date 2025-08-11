package com.codeit.web.controller;

import com.codeit.web.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/users")
public class LoginRestController {

	@PostMapping("/login")
	public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDto, HttpSession session) {
		session.setAttribute("loginUser", userDto);
		return ResponseEntity.ok(userDto);
	}

	@PostMapping("/loginForm")
	public ResponseEntity<UserDTO> loginForm(UserDTO userDto, HttpSession session) {
		session.setAttribute("loginUser", userDto);
		return ResponseEntity.ok(userDto);
	}

	@GetMapping("/me")
	public ResponseEntity<UserDTO> getLoginUser(HttpSession session) {
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return ResponseEntity.status(401).build();  // 인증 안 된 경우
		}
		return ResponseEntity.ok(loginUser);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		session.invalidate();  // 세션 전체 초기화
		return ResponseEntity.ok("로그아웃 완료");
	}
}