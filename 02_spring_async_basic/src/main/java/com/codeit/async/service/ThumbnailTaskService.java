package com.codeit.async.service;

import com.codeit.async.config.FileConfig;
import com.codeit.async.entiry.FileRepository;
import com.codeit.async.entiry.Product;
import com.codeit.async.entiry.ProductFile;
import com.codeit.async.repository.FileRepositoryRepository;
import com.codeit.async.repository.ProductFileRepository;
import com.codeit.async.task.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThumbnailTaskService {

    private final TaskService taskService;
    private final FileConfig fileConfig;
    private final FileRepositoryRepository fileRepositoryRepository;
    private final ProductFileRepository productFileRepository;

    @Async("cpuExecutor")
    @Transactional
    public void generateThumbnailsAsync(UUID taskId,
                                        Product product,
                                        List<File> files) {
        log.info("[{}] 썸네일 생성 작업 시작 - taskId={}, productId={}",
                Thread.currentThread().getName(), taskId, product.getId());
        taskService.markRunning(taskId); // 작업 시작

        File originDir = fileConfig.getUploadDirFile();
        File thumbnailDir = fileConfig.getThumbnailUploadDirFile();

        try {
            int sortOrder = 1;
            for(File file : files) {
                String originalFilename = file.getName();

                // 확장자 추출
                String extension = "";
                int dotIndex = originalFilename.lastIndexOf(".");
                if (dotIndex != -1) {
                    extension = originalFilename.substring(dotIndex);
                }

                String timestamp = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                String renameFilename = timestamp + "_" + UUID.randomUUID() + extension;

                // 원본 파일 디스크 저장
                Path originPath = originDir.toPath().resolve(renameFilename);
                Files.copy(file.toPath(), originPath, StandardCopyOption.REPLACE_EXISTING);

                log.info("원본 파일 저장 완료 - path={}", originPath);

                FileRepository fileEntity = FileRepository.builder()
                        .originalFilename(originalFilename)
                        .renameFilename(renameFilename)
                        .fileType("IMAGE")
                        .build();

                FileRepository savedFile = fileRepositoryRepository.save(fileEntity);

                ProductFile productFile = ProductFile.builder()
                        .product(product)
                        .file(savedFile)
                        .sortOrder(sortOrder++)
                        .build();

                productFileRepository.save(productFile);

                // 썸네일 생성
                String thumbnailName = "thumb_" + renameFilename;
                Path thumbnailPath = thumbnailDir.toPath().resolve(thumbnailName);

                Files.copy(originPath, thumbnailPath, StandardCopyOption.REPLACE_EXISTING);

                log.info("썸네일 파일 생성 완료 path={}", thumbnailPath);
                boolean deleted = file.delete();
                log.warn("임시 업로드 파일 삭제 결과: {}", deleted);
                Thread.sleep(2000);
//                if(sortOrder == 3){
//                    throw new RuntimeException("그냥 예외");
//                }
            }
            taskService.markCompleted(taskId);
        } catch (Exception e) {
            taskService.markFailed(taskId, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
