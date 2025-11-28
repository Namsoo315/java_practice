package com.codeit.async.controller;

import com.codeit.async.dto.product.ProductCreateRequest;
import com.codeit.async.dto.product.ProductCreateResponse;
import com.codeit.async.entiry.Product;
import com.codeit.async.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductCreateResponse> createProduct(
            @RequestPart("product") ProductCreateRequest request,
            @RequestParam(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {
        ProductCreateResponse response = productService.createProduct(request, files);
        return ResponseEntity.ok(response);
    }
}
