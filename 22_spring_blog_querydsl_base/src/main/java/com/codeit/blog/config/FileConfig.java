package com.codeit.blog.config;

import java.io.File;

public interface FileConfig {
    String getUploadDir();
    File getAvatarUploadDirFile();
    File getAttachFileUploadDirFile();
}
