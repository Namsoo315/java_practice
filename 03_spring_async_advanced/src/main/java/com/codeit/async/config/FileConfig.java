package com.codeit.async.config;


import com.codeit.async.config.FileConfig;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;

@Getter
@Configuration
public class FileConfig {

    private String baseUploadDir; // OS별 베이스 경로

    @PostConstruct
    public void init() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            baseUploadDir = "C:/uploads/";
        } else {
            baseUploadDir = "/var/uploads/";
        }

        File baseDir = new File(baseUploadDir);
        if (!baseDir.exists()) {
            boolean created = baseDir.mkdirs();
            if (created) {
                System.out.println("업로드 베이스 디렉토리 생성 완료: " + baseUploadDir);
            } else {
                System.err.println("업로드 베이스 디렉토리 생성 실패: " + baseUploadDir);
            }
        }

        // 원본 + 썸네일 디렉토리도 미리 한 번 만들어둔다
        getUploadDirFile();
        getThumbnailUploadDirFile();
    }

    public File getUploadDirFile() {
        File dir = new File(baseUploadDir, "origin");
        if (!dir.exists() && !dir.mkdirs()) {
            throw new RuntimeException("원본 업로드 디렉토리 생성 실패: " + dir.getAbsolutePath());
        }
        return dir;
    }

    public File getThumbnailUploadDirFile() {
        File dir = new File(baseUploadDir, "thumbnails");
        if (!dir.exists() && !dir.mkdirs()) {
            throw new RuntimeException("썸네일 업로드 디렉토리 생성 실패: " + dir.getAbsolutePath());
        }
        return dir;
    }
}

