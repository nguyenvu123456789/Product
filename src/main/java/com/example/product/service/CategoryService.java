package com.example.product.service;

import com.example.product.dto.category.request.CreateCategoryRequest;
import com.example.product.dto.category.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<CategoryResponse> getAll(Pageable pageable);

    CategoryResponse getById(Long id);

    CategoryResponse create(CreateCategoryRequest request);

    CategoryResponse update(Long id, CreateCategoryRequest request);

    void delete(Long id);
}
