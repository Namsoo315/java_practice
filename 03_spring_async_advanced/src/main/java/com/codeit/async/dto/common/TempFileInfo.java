package com.codeit.async.dto.common;

public record TempFileInfo(
        String originalFilename,
        String renameFilename,
        int sortOrder
) {}