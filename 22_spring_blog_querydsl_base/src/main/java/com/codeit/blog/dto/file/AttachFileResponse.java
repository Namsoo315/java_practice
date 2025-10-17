package com.codeit.blog.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachFileResponse {
    private Long id;
    private String originFileName;
    private String renamedFileName;
    private Long size;
    private String contentType;
    private String url;
    private String downloadUrl;
    private Instant createdAt;
}
