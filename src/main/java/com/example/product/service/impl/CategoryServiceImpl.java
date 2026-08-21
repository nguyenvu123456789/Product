package com.example.product.service.impl;

import com.example.product.dto.category.request.CreateCategoryRequest;
import com.example.product.dto.category.response.CategoryResponse;
import com.example.product.entity.Category;
import com.example.product.exception.ResourceNotFoundException;
import com.example.product.mapper.CategoryMapper;
import com.example.product.repository.CategoryRepository;
import com.example.product.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Page<CategoryResponse> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toResponse);
    }

    @Override
    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("error.category.notfound", id));
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse create(CreateCategoryRequest request) {
        Category category = categoryMapper.toEntity(request);

        Date now = new Date();

        category.setCreatedDate(now);
        category.setModifiedDate(now);

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        category.setCreatedBy(username);
        category.setModifiedBy(username);

        Category saved = categoryRepository.save(category);

        return categoryMapper.toResponse(saved);
    }

    @Override
    public CategoryResponse update(Long id, CreateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("error.category.notfound", id));

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setCategoryCode(request.getCategoryCode());
        category.setStatus(request.getStatus());
        category.setModifiedDate(new Date());
        category.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        Category updated = categoryRepository.save(category);
        return categoryMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("error.category.notfound", id));
        categoryRepository.delete(category);
    }
}