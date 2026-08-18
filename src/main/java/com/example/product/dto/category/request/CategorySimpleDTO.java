package com.example.product.dto.category.request;

public class CategorySimpleDTO {
    private Long id;
    private String name;
    private String categoryCode;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategoryCode() { return categoryCode; }
    public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode; }
}