package com.codeit.blog.storage.impl;

import com.codeit.blog.entity.BinaryContent;
import com.codeit.blog.storage.FileStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("prod")
public class FileStorageS3 implements FileStorage {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.prefix:uploads}")
    private String prefix;

    @Override
    public void saveAvatarFile(String username, MultipartFile avatarFile) {
        String key = String.format("%s/avatars/%s", prefix, username);
        uploadToS3(key, avatarFile);
        log.info("아바타 업로드 완료: {}", key);
    }

    @Override
    public void deleteAvatarFile(String username) {
        String key = String.format("%s/avatars/%s", prefix, username);
        deleteFromS3(key);
        log.info("아바타 삭제 완료: {}", key);
    }

    @Override
    public BinaryContent saveAttachFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("빈 파일은 저장할 수 없습니다");
        }

        String originalName = file.getOriginalFilename();
        String ext = (originalName != null && originalName.contains("."))
                ? originalName.substring(originalName.lastIndexOf('.'))
                : "";

        String renamed = UUID.randomUUID().toString() + ext;
        String key = String.format("%s/attachments/%s", prefix, renamed);

        uploadToS3(key, file);

        log.info("첨부파일 업로드 완료: {}", key);

        return BinaryContent.builder()
                .originFileName(originalName)
                .renamedFileName(renamed)
                .size(file.getSize())
                .contentType(file.getContentType())
                .build();
    }

    @Override
    public void deleteAllAttachments(Collection<BinaryContent> files) {
        for (BinaryContent bc : files) {
            if (bc.getRenamedFileName() == null) continue;
            String key = String.format("%s/attachments/%s", prefix, bc.getRenamedFileName());
            deleteFromS3(key);
            log.info("첨부파일 삭제 완료: {}", key);
        }
    }

    @Override
    public String getAvatarFileUrl(String userId) {
        return "";
    }

    @Override
    public String getAttachFileUrl(Long binaryContentId) {
        return "";
    }


    private void uploadToS3(String key, MultipartFile file) {
        try {
            PutObjectRequest putReq = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putReq, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException("S3 업로드 실패: " + key, e);
        }
    }

    private void deleteFromS3(String key) {
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build());
        } catch (Exception e) {
            log.warn("S3 삭제 실패 (무시 가능): {}", key, e);
        }
    }
}
