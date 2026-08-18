package com.example.product.service;

import com.example.product.dto.product.request.CreateProductRequest;
import com.example.product.dto.product.response.ProductResponse;
import com.example.product.dto.common.request.ProductSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    Page<ProductResponse> getAll(Pageable pageable);

    Page<ProductResponse> search(ProductSearchRequest request, Pageable pageable);

    ProductResponse getById(Long id);

    ProductResponse create(CreateProductRequest request);

    ProductResponse update(Long id, CreateProductRequest request);

    void delete(Long id);
    ProductResponse uploadImage(Long id, MultipartFile file);

    String uploadImageOnly(MultipartFile file);
}
