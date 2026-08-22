package com.example.product.entity;

import jakarta.persistence.*;
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

    private String name;

    @Column(name = "image", length = 500)
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double price;

    @Column(name = "product_code", unique = true)
    private String productCode;

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