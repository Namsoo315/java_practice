package com.codeit.blog.config.impl;

import com.codeit.blog.config.FileConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
@Profile("prod")
public class FileConfigProd implements FileConfig {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Path.of(uploadDir));
        } catch (Exception e) {
            throw new IllegalStateException("업로드 디렉토리 생성 실패: " + uploadDir, e);
        }
    }

    @Override
    public String getUploadDir() {
        return uploadDir;
    }

    @Override
    public File getAvatarUploadDirFile() {
        try {
            Path p = Path.of(uploadDir, "avatars");
            Files.createDirectories(p);
            return p.toFile();
        } catch (Exception e) {
            throw new IllegalStateException("아바타 디렉토리 생성 실패", e);
        }
    }

    @Override
    public File getAttachFileUploadDirFile() {
        try {
            Path p = Path.of(uploadDir, "attachments");
            Files.createDirectories(p);
            return p.toFile();
        } catch (Exception e) {
            throw new IllegalStateException("첨부파일 디렉토리 생성 실패", e);
        }
    }
}
