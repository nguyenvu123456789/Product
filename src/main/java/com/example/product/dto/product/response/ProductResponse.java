package com.example.product.dto.product.response;

import com.example.product.dto.category.request.CategorySimpleDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Double price;
    private String productCode;
    private Long quantity;
    private String status;
    private Date createdDate;
    private Date modifiedDate;
    private String createdBy;
    private String modifiedBy;
    private List<CategorySimpleDTO> categories;

}
