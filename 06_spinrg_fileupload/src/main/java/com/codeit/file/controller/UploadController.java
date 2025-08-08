package com.codeit.file.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.codeit.file.config.FileConfig;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UploadController {

	private final FileConfig fileConfig;

	// 업로드 폼
	@GetMapping("/upload")
	public String upload() {
		return "file/upload";
	}

	@PostMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return "파일이 비었습니다.";
		}

		String uploadDir = fileConfig.getUploadDir();
		String filePath = uploadDir + File.separator + file.getOriginalFilename();
		file.transferTo(Path.of(filePath));

		return "파일 업로드 성공";
	}

	@PostMapping("/uploadMultiple")
	@ResponseBody
	public String uploadMultiple(@RequestParam("files") List<MultipartFile> files) throws IOException {
		String uploadDir = fileConfig.getUploadDir();
		StringBuilder sb = new StringBuilder();
		for (MultipartFile file : files) {
			String filePath = uploadDir + File.separator + file.getOriginalFilename();
			file.transferTo(Path.of(filePath));
			sb.append(file.getOriginalFilename() + "\n");
		}

		return sb.toString();
	}

	@PostMapping("/uploadWithRename")
	@ResponseBody
	public String uploadWithRename(@RequestParam MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return "파일이 비엇습니다.";
		}

		String uploadDir = fileConfig.getUploadDir();
		String originalFilename = file.getOriginalFilename();
		String ext = "";    //확장자
		if (originalFilename != null && originalFilename.contains(".")) {
			ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		}

		String date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String random = UUID.randomUUID().toString().substring(0, 8);
		String savedName = date + "_" + random + "." + ext;

		String filePath = uploadDir + File.separator + savedName;
		file.transferTo(Path.of(filePath));
		return "savedName : " + savedName + "| filepath : " + filePath;
	}

}
