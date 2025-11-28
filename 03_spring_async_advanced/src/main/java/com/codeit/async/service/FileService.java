package com.codeit.async.service;

import com.codeit.async.dto.common.TempFileInfo;
import com.codeit.async.entiry.FileRepository;
import com.codeit.async.entiry.Product;
import com.codeit.async.entiry.ProductFile;
import com.codeit.async.repository.FileRepositoryRepository;
import com.codeit.async.repository.ProductFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileRepositoryRepository fileRepositoryRepository;
    private final ProductFileRepository productFileRepository;

    @Transactional
    public void saveAllToDatabase(Product product, List<TempFileInfo> list) {
        for (TempFileInfo info : list) {
            FileRepository fileEntity = FileRepository.builder()
                    .originalFilename(info.originalFilename())
                    .renameFilename(info.renameFilename())
                    .fileType("IMAGE")
                    .build();

            FileRepository savedFile = fileRepositoryRepository.save(fileEntity);

            ProductFile productFile = ProductFile.builder()
                    .product(product)
                    .file(savedFile)
                    .sortOrder(info.sortOrder())
                    .build();

            productFileRepository.save(productFile);
        }
        log.info("DB 저장 완료 ({}개)", list.size());
    }
}
