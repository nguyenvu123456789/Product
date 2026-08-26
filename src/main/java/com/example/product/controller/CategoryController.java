package com.example.product.controller;

import com.example.product.dto.category.request.CreateCategoryRequest;
import com.example.product.dto.category.response.CategoryResponse;
import com.example.product.dto.common.response.PageResponse;
import com.example.product.dto.common.response.ApiResponse;
import com.example.product.service.CategoryService;
import com.example.product.ultis.MessageHelper;
import com.example.product.ultis.paging.PageableUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final PageableUtils pageableUtils;
    private final MessageHelper messageHelper;

    public CategoryController(CategoryService categoryService,
                              PageableUtils pageableUtils,
                              MessageHelper messageHelper) {
        this.categoryService = categoryService;
        this.pageableUtils = pageableUtils;
        this.messageHelper = messageHelper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CategoryResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = pageableUtils.build(page, size, sortBy, direction);
        Page<CategoryResponse> categoryPage = categoryService.getAll(pageable);
        PageResponse<CategoryResponse> data = PageResponse.from(categoryPage);

        return ResponseEntity.ok(
                ApiResponse.of(data, messageHelper.get("category.list.success"))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getById(@PathVariable Long id) {
        CategoryResponse category = categoryService.getById(id);
        return ResponseEntity.ok(
                ApiResponse.of(category, messageHelper.get("category.get.success"))
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(
            @Valid @RequestBody CreateCategoryRequest request) {

        CategoryResponse category = categoryService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of(
                        category,
                        messageHelper.get("category.create.success")
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateCategoryRequest request) {

        CategoryResponse category = categoryService.update(id, request);
        return ResponseEntity.ok(
                ApiResponse.of(category, messageHelper.get("category.update.success"))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.of(null, messageHelper.get("category.delete.success"))
        );
    }
}