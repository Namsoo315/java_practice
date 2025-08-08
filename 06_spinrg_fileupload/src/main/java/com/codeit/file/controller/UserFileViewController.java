package com.codeit.file.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.codeit.file.config.FileConfig;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserFileViewController {

	private final FileConfig fileConfig;

	@GetMapping("/files")
	public String listUploadFiles(ModelMap model) {
		File folder = new File(fileConfig.getUploadDir());

		if (!folder.exists() || !folder.isDirectory()) {
			model.addAttribute("files", List.of());
			return "file/file-list";
		}

		// 파일명 리스트 추출
		List<String> fileNames = Arrays.stream(folder.listFiles())
			.filter(File::isFile)
			.map(File::getName)
			.toList();
		model.addAttribute("files", fileNames);
		return "file/file-list";
	}

	@GetMapping("/download/{filename}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
		String uploadDir = fileConfig.getUploadDir();
		Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
		Resource resource = new UrlResource(filePath.toUri());

		if (!resource.exists()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok()
			.contentType(MediaType.APPLICATION_OCTET_STREAM)
			.header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + resource.getFilename() + "\"")
			.body(resource);
	}

}
