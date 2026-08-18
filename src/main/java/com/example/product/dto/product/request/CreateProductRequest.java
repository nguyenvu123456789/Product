package com.example.product.dto.product.request;

import com.example.product.dto.category.request.CategorySimpleDTO;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;

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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public Date getModifiedDate() { return modifiedDate; }
    public void setModifiedDate(Date modifiedDate) { this.modifiedDate = modifiedDate; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    public List<CategorySimpleDTO> getCategories() { return categories; }
    public void setCategories(List<CategorySimpleDTO> categories) { this.categories = categories; }
}