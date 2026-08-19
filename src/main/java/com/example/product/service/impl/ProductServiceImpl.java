package com.example.product.service.impl;

import com.example.product.dto.product.request.CreateProductRequest;
import com.example.product.dto.product.response.ProductResponse;
import com.example.product.dto.common.request.ProductSearchRequest;
import com.example.product.entity.Product;
import com.example.product.exception.FileStorageException;
import com.example.product.exception.ResourceNotFoundException;
import com.example.product.mapper.ProductMapper;
import com.example.product.repository.ProductRepository;
import com.example.product.service.CloudinaryService;
import com.example.product.service.ProductService;
import com.example.product.ultis.file.FileValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Map;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private static final String PRODUCT_IMAGE_FOLDER = "products";
    private final CloudinaryService cloudinaryService;
    private final FileValidator fileValidator;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper,
                              CloudinaryService cloudinaryService,
                              FileValidator fileValidator) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.cloudinaryService = cloudinaryService;
        this.fileValidator = fileValidator;
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

        fileValidator.validateImageFile(file);

        String oldImageUrl = product.getImage();

        String newImageUrl = uploadImageOnly(file);
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
    public String uploadImageOnly(MultipartFile file) {
        fileValidator.validateImageFile(file);
        Map<?, ?> uploadResult = cloudinaryService.uploadFile(file, PRODUCT_IMAGE_FOLDER);
        Object secureUrl = uploadResult.get("secure_url");

        if (secureUrl == null) {
            throw new FileStorageException("error.upload.failed");
        }
        return secureUrl.toString();
    }

}
