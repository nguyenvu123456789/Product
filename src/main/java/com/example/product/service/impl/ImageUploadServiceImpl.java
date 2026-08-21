package com.example.product.service.impl;

import com.example.product.exception.FileStorageException;
import com.example.product.service.CloudinaryService;
import com.example.product.service.ImageUploadService;
import com.example.product.ultis.file.FileValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    private final CloudinaryService cloudinaryService;
    private final FileValidator fileValidator;

    public ImageUploadServiceImpl(CloudinaryService cloudinaryService, FileValidator fileValidator) {
        this.cloudinaryService = cloudinaryService;
        this.fileValidator = fileValidator;
    }

    @Override
    public String upload(MultipartFile file, String folder) {
        fileValidator.validateImageFile(file);

        Map<?, ?> uploadResult = cloudinaryService.uploadFile(file, folder);
        Object secureUrl = uploadResult.get("secure_url");

        if (secureUrl == null) {
            throw new FileStorageException("error.upload.failed");
        }
        return secureUrl.toString();
    }
}