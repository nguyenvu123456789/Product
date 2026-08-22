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
@Table(name = "category")
public class Category extends BaseEntity {

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "category_code", unique = true)
    private String categoryCode;

    private String status;

    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<ProductCategory> productCategories = new ArrayList<>();
}