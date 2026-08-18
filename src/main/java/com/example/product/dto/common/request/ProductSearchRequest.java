package com.example.product.dto.common.request;

public class ProductSearchRequest {
    private String name;
    private String productCode;
    private String status;
    private Double minPrice;
    private Double maxPrice;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getMinPrice() { return minPrice; }
    public void setMinPrice(Double minPrice) { this.minPrice = minPrice; }

    public Double getMaxPrice() { return maxPrice; }
    public void setMaxPrice(Double maxPrice) { this.maxPrice = maxPrice; }
}