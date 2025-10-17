package com.codeit.blog.controller;

import com.codeit.blog.config.FileConfig;
import com.codeit.blog.dto.file.AttachFileResponse;
import com.codeit.blog.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final FileConfig fileConfig;

    @GetMapping("/api/files/{id}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        AttachFileResponse file = fileService.findById(id);

        File attachDir = fileConfig.getAttachFileUploadDirFile();
        Path path = new File(attachDir, file.getRenamedFileName()).toPath();

        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
            if (!resource.exists()) {
                throw new FileNotFoundException("File not found: " + file.getRenamedFileName());
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        String encodedFileName = UriUtils.encode(file.getOriginFileName(), StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.getSize()))
                .body(resource);
    }

    @GetMapping("/avatars/{filename:.+}")
    public ResponseEntity<Resource> getAvatar(@PathVariable String filename) {
        File base = fileConfig.getAvatarUploadDirFile();
        Path path = new File(base, filename).toPath();

        UrlResource resource;
        try {
            resource = new UrlResource(path.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @GetMapping("/attachments/{filename:.+}")
    public ResponseEntity<Resource> getAttachmentByName(
            @PathVariable String filename,
            @RequestParam(name = "download", defaultValue = "false") boolean download) {

        File base = fileConfig.getAttachFileUploadDirFile();
        Path path = new File(base, filename).toPath();

        UrlResource resource;
        try {
            resource = new UrlResource(path.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        MediaType mediaType = probeContentType(path);
        ContentDisposition disposition = download
                ? ContentDisposition.attachment().filename(filename, StandardCharsets.UTF_8).build()
                : ContentDisposition.inline().filename(filename, StandardCharsets.UTF_8).build();

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
                .body(resource);
    }

    @GetMapping("/{id}/inline")
    public ResponseEntity<Resource> inlineById(@PathVariable Long id) {
        AttachFileResponse file = fileService.findById(id);

        File attachDir = fileConfig.getAttachFileUploadDirFile();
        Path path = new File(attachDir, file.getRenamedFileName()).toPath();

        UrlResource resource;
        try {
            resource = new UrlResource(path.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        MediaType mediaType = safeParseMediaType(file.getContentType(), path);
        String cd = ContentDisposition.inline()
                .filename(file.getOriginFileName(), StandardCharsets.UTF_8)
                .build().toString();

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, cd)
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.getSize()))
                .body(resource);
    }


    private static MediaType probeContentType(Path path) {
        try {
            String t = Files.probeContentType(path);
            return (t != null) ? MediaType.parseMediaType(t) : MediaType.APPLICATION_OCTET_STREAM;
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    private static MediaType safeParseMediaType(String contentType, Path fallbackPath) {
        try {
            if (contentType != null && !contentType.isBlank()) {
                return MediaType.parseMediaType(contentType);
            }
        } catch (Exception ignore) { }
        return probeContentType(fallbackPath);
    }


}
