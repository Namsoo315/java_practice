package com.codeit.blog.storage;

import com.codeit.blog.entity.BinaryContent;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface FileStorage {
     void saveAvatarFile(String username, MultipartFile avatarFile);

     void deleteAvatarFile(String username);

     BinaryContent saveAttachFile(MultipartFile file);

     void deleteAllAttachments(Collection<BinaryContent> files);

     String getAvatarFileUrl(String userId);
     String getAttachFileUrl(Long binaryContentId);
}
