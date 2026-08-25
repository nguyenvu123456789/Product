package com.example.product.service.impl;

import com.example.product.dto.product.request.CreateProductRequest;
import com.example.product.dto.product.response.ProductResponse;
import com.example.product.dto.common.request.ProductSearchRequest;
import com.example.product.entity.Product;
import com.example.product.exception.ResourceNotFoundException;
import com.example.product.mapper.ProductMapper;
import com.example.product.repository.ProductRepository;
import com.example.product.service.CloudinaryService;
import com.example.product.service.ImageUploadService;
import com.example.product.service.ProductService;
import com.example.product.ultis.file.ExcelExporter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private static final String PRODUCT_IMAGE_FOLDER = "products";
    private final CloudinaryService cloudinaryService;
    private final ImageUploadService imageUploadService;
    private final ExcelExporter excelExporter;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper,
                              CloudinaryService cloudinaryService,
                              ImageUploadService imageUploadService,
                              ExcelExporter excelExporter) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.cloudinaryService = cloudinaryService;
        this.imageUploadService = imageUploadService;
        this.excelExporter = excelExporter;
    }

    @Override
    public Page<ProductResponse> getAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toResponse);
    }

    @Override
    public Page<ProductResponse> search(ProductSearchRequest request, Pageable pageable) {
        return productRepository.search(
                request.getName(),
                request.getProductCode(),
                request.getStatus(),
                request.getMinPrice(),
                request.getMaxPrice(),
                pageable
        ).map(productMapper::toResponse);
    }

    @Override
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("error.product.notfound", id));
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse create(CreateProductRequest request) {
        Product product = productMapper.toEntity(request);
        product.setCreatedDate(new Date());
        product.setModifiedDate(new Date());
        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    @Override
    public ProductResponse update(Long id, CreateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("error.product.notfound", id));
        product.setName(request.getName());
        product.setImage(request.getImage());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setProductCode(request.getProductCode());
        product.setQuantity(request.getQuantity());
        product.setStatus(request.getStatus());
        product.setModifiedDate(new Date());
        product.setModifiedBy(request.getModifiedBy());

        Product updated = productRepository.save(product);
        return productMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("error.product.notfound", id));
        productRepository.delete(product);
    }

    @Override
    public ProductResponse uploadImage(Long id, MultipartFile file) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("error.product.notfound", id));

        String oldImageUrl = product.getImage();

        String newImageUrl = imageUploadService.upload(file, PRODUCT_IMAGE_FOLDER);
        product.setImage(newImageUrl);
        product.setModifiedDate(new Date());
        Product updated = productRepository.save(product);

        if (StringUtils.hasText(oldImageUrl)) {
            String oldPublicId = cloudinaryService.extractPublicId(oldImageUrl);
            cloudinaryService.deleteFile(oldPublicId);
        }

        return productMapper.toResponse(updated);
    }

    @Override
    public byte[] exportExcel(ProductSearchRequest request) {

        List<Product> products = productRepository.searchForExport(
                request.getName(),
                request.getProductCode(),
                request.getStatus(),
                request.getMinPrice(),
                request.getMaxPrice()
        );

        return excelExporter.exportProducts(products);
    }

}