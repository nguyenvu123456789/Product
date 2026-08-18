package com.example.product.dto.category.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class CreateCategoryRequest {

    @NotBlank(message = "{category.name.notblank}")
    @Size(max = 255)
    private String name;

    private String description;

    @NotBlank(message = "{category.code.notblank}")
    private String categoryCode;

    private String status;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategoryCode() { return categoryCode; }
    public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

}