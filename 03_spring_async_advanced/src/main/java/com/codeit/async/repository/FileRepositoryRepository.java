package com.codeit.async.repository;

import com.codeit.async.entiry.FileRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepositoryRepository extends JpaRepository<FileRepository, Long> {
}
