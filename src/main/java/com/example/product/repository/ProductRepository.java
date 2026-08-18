package com.example.product.repository;

import com.example.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByProductCode(String productCode);

    @Query("""
        SELECT p FROM Product p
        WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:productCode IS NULL OR LOWER(p.productCode) LIKE LOWER(CONCAT('%', :productCode, '%')))
          AND (:status IS NULL OR p.status = :status)
          AND (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
        """)
    Page<Product> search(
            @Param("name") String name,
            @Param("productCode") String productCode,
            @Param("status") String status,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable
    );
}