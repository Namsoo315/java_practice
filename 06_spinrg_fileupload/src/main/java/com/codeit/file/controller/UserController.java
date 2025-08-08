package com.codeit.file.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.codeit.file.config.FileConfig;
import com.codeit.file.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final FileConfig fileConfig;

	@GetMapping("/users/upload")
	public String uploadUserForm() {
		return "users/userForm";
	}

	@PostMapping("/users/upload")
	public String uploadUser(UserDTO userDto, Model model) throws IOException {
		// 저장할 디렉토리
		String uploadDir = fileConfig.getUploadDir();

		// 아바타 파일 저장
		MultipartFile avatarFile = userDto.getAvatar();
		if(avatarFile != null && !avatarFile.isEmpty()) {
			// 비어있지 않을 때
			String fileName = userDto.getUsername();	// 식별 ID 또는 DB PK로 저장하는것이 좋다.
			String filePath = uploadDir + File.separator + fileName;
			avatarFile.transferTo(Path.of(filePath));
		}

		model.addAttribute("user", userDto);
		return "users/userProfile";
	}

}
