package com.codeit.file.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

// 커스텀 정적 리소스
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final FileConfig fileConfig;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String uploadDir = fileConfig.getUploadDir();
		registry.addResourceHandler("/images/**")
			.addResourceLocations("file:///" + uploadDir); // 윈도우도 리눅스도 모두 지원
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
}
