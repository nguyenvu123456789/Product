package com.example.product.dto.category.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private String categoryCode;
    private String status;
    private Date createdDate;
    private Date modifiedDate;
    private String createdBy;
    private String modifiedBy;

}
