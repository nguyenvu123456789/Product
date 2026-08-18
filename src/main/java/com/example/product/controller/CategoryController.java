package com.example.product.controller;

import com.example.product.dto.category.request.CreateCategoryRequest;
import com.example.product.dto.category.response.CategoryResponse;
import com.example.product.dto.common.response.PageResponse;
import com.example.product.dto.common.response.ApiResponse;
import com.example.product.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CategoryResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CategoryResponse> categoryPage = categoryService.getAll(pageable);

        PageResponse<CategoryResponse> data = PageResponse.from(categoryPage);

        return ResponseEntity.ok(
                ApiResponse.success(
                        data,
                        "category.list.success"
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getById(
            @PathVariable Long id) {

        CategoryResponse category = categoryService.getById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        category,
                        "category.get.success"
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(
            @Valid @RequestBody CreateCategoryRequest request) {

        CategoryResponse category = categoryService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                category,
                                HttpStatus.CREATED.value(),
                                "category.create.success"
                        )
                );
    }

    @PutMapping("/{id}")    
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateCategoryRequest request) {

        CategoryResponse category = categoryService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        category,
                        "category.update.success"
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        categoryService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        null,
                        "category.delete.success"
                )
        );
    }
}
