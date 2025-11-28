package com.codeit.async.service;

import com.codeit.async.config.FileConfig;
import com.codeit.async.dto.common.TempFileInfo;
import com.codeit.async.entiry.FileRepository;
import com.codeit.async.entiry.Product;
import com.codeit.async.entiry.ProductFile;
import com.codeit.async.repository.FileRepositoryRepository;
import com.codeit.async.repository.ProductFileRepository;
import com.codeit.async.task.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThumbnailTaskService {
    private final TaskService taskService;
    private final FileConfig fileConfig;
    private final FileService fileService;

    //파일 처리 성공 시에만 DB 저장
    @Async("cpuExecutor")
    // 재시도 어노테이션 중요 ★★★★★
    @Retryable(
            maxAttempts = 5, // 재시도 횟수
            backoff = @Backoff(delay = 1000) // 작업간 재시작 delay
    )
    public void generateThumbnailsAsync(UUID taskId,
                                        Product product,
                                        List<File> files) {
        log.info("[{}] 썸네일 생성 작업 시작 - taskId={}, productId={}",
                Thread.currentThread().getName(), taskId, product.getId());
        taskService.markRunning(taskId);

        File originDir = fileConfig.getUploadDirFile();
        File thumbnailDir = fileConfig.getThumbnailUploadDirFile();

        // 메모리에 임시 저장 (모두 성공 후 DB 저장)
        List<TempFileInfo> tempList = new ArrayList<>();

        try {
            int sortOrder = 1;

            // 1) 파일 처리
            for (File file : files) {

                String originalFilename = file.getName();

                // 확장자
                String extension = "";
                int idx = originalFilename.lastIndexOf(".");
                if (idx != -1) {
                    extension = originalFilename.substring(idx);
                }

                // rename
                String timestamp = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                String renameFilename = timestamp + "_" + UUID.randomUUID() + extension;

                // 원본 복사
                Path originPath = originDir.toPath().resolve(renameFilename);
                Files.copy(file.toPath(), originPath, StandardCopyOption.REPLACE_EXISTING);
                log.info("원본 파일 저장: {}", originPath);

                // 썸네일 생성
                String thumbName = "thumb_" + renameFilename;
                Path thumbPath = thumbnailDir.toPath().resolve(thumbName);
                Files.copy(originPath, thumbPath, StandardCopyOption.REPLACE_EXISTING);

                log.info("썸네일 생성 완료: {}", thumbPath);

                // 성공한 항목만 임시 저장
                tempList.add(new TempFileInfo(
                        originalFilename,
                        renameFilename,
                        sortOrder++
                ));
                Thread.sleep(1000);
                if(sortOrder == 3 && new Random().nextInt(10) % 3 == 0){
                    throw  new RuntimeException("랜덤 예외");
                }
            }
            // 2) 파일 모두 성공 → DB 저장
            for (File file : files) {
                boolean result = file.delete();
                if(!result){
                   log.info("삭제 실패!");
                }
            }
            fileService.saveAllToDatabase(product, tempList);
            taskService.markCompleted(taskId);
        } catch (Exception e) {
            taskService.markFailed(taskId, e.getMessage());
            log.error("썸네일 생성 실패", e);
            throw new RuntimeException(e);
        }
    }
}
