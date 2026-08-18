package com.example.product.mapper;

import com.example.product.dto.category.request.CreateCategoryRequest;
import com.example.product.dto.category.response.CategoryResponse;
import com.example.product.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);

    List<CategoryResponse> toResponseList(List<Category> categories);

    @Mapping(target = "productCategories", ignore = true)
    @Mapping(target = "id", ignore = true)
    Category toEntity(CreateCategoryRequest request);
}
