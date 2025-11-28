package com.codeit.async.service;

import com.codeit.async.dto.product.ProductCreateRequest;
import com.codeit.async.dto.product.ProductCreateResponse;
import com.codeit.async.entiry.Product;
import com.codeit.async.event.ProductCreatedEvent;
import com.codeit.async.repository.ProductRepository;
//import com.codeit.async.task.TaskService;
import com.codeit.async.task.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final TaskService taskService;
    private final ThumbnailTaskService thumbnailTaskService;
    private final ApplicationEventPublisher publisher;

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public ProductCreateResponse createProduct(ProductCreateRequest request,
                                               List<MultipartFile> files) throws IOException {
        log.info("[{}] createProduct 시작!",
                Thread.currentThread().getName());

        // 1) 상품 저장
        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .quantity(request.quantity())
                .build();

        Product savedProduct = productRepository.save(product);

        // 2) 파일이 없으면 비동기 작업 없이 바로 응답
        boolean hasFiles = files != null && !files.isEmpty();
        if (!hasFiles) {
            return new ProductCreateResponse(savedProduct.getId(), null);
        }

        // 3) 비동기 썸네일 작업용 Task 생성
        String description = "상품 썸네일 생성 작업 - productId=" + savedProduct.getId();
        UUID taskId = taskService.createTask(description);

        List<File> storedFiles = new ArrayList<>();
        for (MultipartFile mf : files) {
            File temp = File.createTempFile("upload_", "_" + mf.getOriginalFilename());
            mf.transferTo(temp);
            storedFiles.add(temp);
        }

        // 4) 비동기 작업 실행
        // -> main Thread에서 db poll과 비동기 thread의 poll 달라 commit 처리 되지 않은 값이 전달됨!
        // -> 해결방법 : TransactionalEventListener를 통애 commit 이후 비동기 작업이 실행되도록 조정!
//        thumbnailTaskService.generateThumbnailsAsync(taskId, savedProduct, storedFiles);
        publisher.publishEvent(new ProductCreatedEvent(savedProduct, storedFiles, taskId));

        return new ProductCreateResponse(savedProduct.getId(), taskId.toString());
    }
}
