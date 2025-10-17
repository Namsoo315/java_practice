package com.codeit.blog.config.impl;

import com.codeit.blog.config.FileConfig;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Configuration
@Profile({"dev","test"})
public class FileConfigDev implements FileConfig {

    private String uploadDir;

    @PostConstruct
    public void init() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            uploadDir = "C:/uploads/";
        } else {
            uploadDir = "/var/uploads/";
        }

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                System.out.println("업로드 디렉토리 생성 완료: " + uploadDir);
            } else {
                System.err.println("업로드 디렉토리 생성 실패: " + uploadDir);
            }
        }
    }

    public File getAvatarUploadDirFile() {
        Path dir = Paths.get(uploadDir, "avatars");
        if (Files.notExists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create avatar upload directory", e);
            }
        }
        return dir.toFile();
    }

    public File getAttachFileUploadDirFile() {
        Path dir = Paths.get(uploadDir, "attachments");
        if (Files.notExists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create attachment upload directory", e);
            }
        }
        return dir.toFile();
    }
}
