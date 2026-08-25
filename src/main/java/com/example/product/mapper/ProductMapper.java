package com.example.product.mapper;

import com.example.product.dto.category.request.CategorySimpleDTO;
import com.example.product.dto.category.response.CategoryResponse;
import com.example.product.dto.product.request.CreateProductRequest;
import com.example.product.dto.product.response.ProductResponse;
import com.example.product.entity.Category;
import com.example.product.entity.Product;
import com.example.product.entity.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categories", source = "productCategories", qualifiedByName = "mapCategories")
    ProductResponse toResponse(Product product);

    @Mapping(target = "productCategories", ignore = true)
    @Mapping(target = "id", ignore = true)
    Product toEntity(CreateProductRequest request);

    CategoryResponse toCategoryResponse(Category category);

    @Named("mapCategories")
    default List<CategorySimpleDTO> mapCategories(List<ProductCategory> productCategories) {
        if (productCategories == null) return null;
        return productCategories.stream()
                .map(pc -> {
                    CategorySimpleDTO dto = new CategorySimpleDTO();
                    dto.setId(pc.getCategory().getId());
                    dto.setName(pc.getCategory().getName());
                    dto.setCategoryCode(pc.getCategory().getCategoryCode());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
