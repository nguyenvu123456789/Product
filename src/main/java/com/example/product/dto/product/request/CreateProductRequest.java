package com.example.product.dto.product.request;

import com.example.product.dto.category.request.CategorySimpleDTO;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class CreateProductRequest {

    @NotBlank(message = "{product.name.notblank}")
    @Size(max = 255)
    private String name;
    private String image;

    private String description;

    @NotNull(message = "{product.price.notnull}")
    @DecimalMin(value = "0.01", message = "{product.price.min}")
    private Double price;

    @NotBlank(message = "{product.code.notblank}")
    private String productCode;

    @NotNull(message = "{product.quantity.notnull}")
    @Min(value = 0, message = "{product.quantity.min}")
    private Long quantity;
    private String status;
    private Date createdDate;
    private Date modifiedDate;
    private String createdBy;
    private String modifiedBy;

    private List<CategorySimpleDTO> categories;

}