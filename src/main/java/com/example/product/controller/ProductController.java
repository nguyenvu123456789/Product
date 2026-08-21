package com.example.product.controller;

import com.example.product.dto.product.request.CreateProductRequest;
import com.example.product.dto.product.response.ProductResponse;
import com.example.product.dto.common.response.PageResponse;
import com.example.product.dto.common.response.ApiResponse;
import com.example.product.service.ImageUploadService;
import com.example.product.service.ProductService;
import com.example.product.ultis.MessageHelper;
import com.example.product.ultis.paging.PageableUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final String PRODUCT_IMAGE_FOLDER = "products";

    private final ProductService productService;
    private final PageableUtils pageableUtils;
    private final ImageUploadService imageUploadService;
    private final MessageHelper messageHelper;

    public ProductController(ProductService productService,
                             PageableUtils pageableUtils,
                             ImageUploadService imageUploadService,
                             MessageHelper messageHelper) {
        this.productService = productService;
        this.pageableUtils = pageableUtils;
        this.imageUploadService = imageUploadService;
        this.messageHelper = messageHelper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = pageableUtils.build(page, size, sortBy, direction);
        Page<ProductResponse> productPage = productService.getAll(pageable);
        PageResponse<ProductResponse> data = PageResponse.from(productPage);

        return ResponseEntity.ok(
                ApiResponse.success(data, messageHelper.get("product.list.success"))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getById(@PathVariable Long id) {
        ProductResponse product = productService.getById(id);
        return ResponseEntity.ok(
                ApiResponse.success(product, messageHelper.get("product.get.success"))
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> create(
            @Valid @RequestBody CreateProductRequest request) {

        ProductResponse product = productService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        product,
                        HttpStatus.CREATED.value(),
                        messageHelper.get("product.create.success")
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateProductRequest request) {

        ProductResponse product = productService.update(id, request);
        return ResponseEntity.ok(
                ApiResponse.success(product, messageHelper.get("product.update.success"))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success(null, messageHelper.get("product.delete.success"))
        );
    }

    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductResponse>> uploadImage(
            @PathVariable Long id,
            @RequestPart("file") MultipartFile file) {

        ProductResponse product = productService.uploadImage(id, file);
        return ResponseEntity.ok(
                ApiResponse.success(product, messageHelper.get("product.image.upload.success"))
        );
    }

    @PostMapping(value = "/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Map<String, String>>> uploadImageOnly(
            @RequestPart("file") MultipartFile file) {

        String url = imageUploadService.upload(file, PRODUCT_IMAGE_FOLDER);
        return ResponseEntity.ok(
                ApiResponse.success(Map.of("url", url), messageHelper.get("product.image.upload.success"))
        );
    }
}