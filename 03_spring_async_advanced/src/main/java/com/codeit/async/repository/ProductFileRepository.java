package com.codeit.async.repository;

import com.codeit.async.entiry.ProductFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductFileRepository extends JpaRepository<ProductFile, Long> {
}
