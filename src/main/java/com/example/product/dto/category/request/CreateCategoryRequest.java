package com.example.product.dto.category.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateCategoryRequest {

    @NotBlank(message = "{category.name.notblank}")
    @Size(max = 255)
    private String name;

    private String description;

    @NotBlank(message = "{category.code.notblank}")
    private String categoryCode;

    private String status;

}