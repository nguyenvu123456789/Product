package com.example.product.dto.category.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategorySimpleDTO {
    private Long id;
    private String name;
    private String categoryCode;

}