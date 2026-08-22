package com.example.product.dto.common.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductSearchRequest {
    private String name;
    private String productCode;
    private String status;
    private Double minPrice;
    private Double maxPrice;

}