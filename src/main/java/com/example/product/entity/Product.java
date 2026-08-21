package com.example.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product extends BaseEntity {

    @NotBlank(message = "{product.name.notblank}")
    @Size(max = 255)
    private String name;

    @Column(name = "image", length = 500)
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "{product.price.notnull}")
    @DecimalMin(value = "0.01", message = "{product.price.min}")
    private Double price;

    @NotBlank(message = "{product.code.notblank}")
    @Column(name = "product_code", unique = true)
    private String productCode;

    @NotNull(message = "{product.quantity.notnull}")
    @Min(value = 0, message = "{product.quantity.min}")
    private Long quantity;

    private String status;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<ProductCategory> productCategories = new ArrayList<>();
}