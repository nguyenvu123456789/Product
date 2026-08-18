package com.example.product.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryService {

    Map<?, ?> uploadFile(MultipartFile file, String folder);

    void deleteFile(String publicId);

    String extractPublicId(String imageUrl);
}