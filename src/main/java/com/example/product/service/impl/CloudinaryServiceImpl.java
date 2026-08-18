package com.example.product.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.product.exception.FileStorageException;
import com.example.product.service.CloudinaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final Logger log = LoggerFactory.getLogger(CloudinaryServiceImpl.class);

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public Map<?, ?> uploadFile(MultipartFile file, String folder) {
        try {
            String publicId = folder + "/" + UUID.randomUUID();
            return cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "public_id", publicId,
                            "folder", folder,
                            "resource_type", "image",
                            "overwrite", true
                    )
            );
        } catch (IOException e) {
            log.error("Upload to Cloudinary failed", e);
            throw new FileStorageException("error.upload.failed");
        }
    }

    @Override
    public void deleteFile(String publicId) {
        if (!StringUtils.hasText(publicId)) {
            return;
        }
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            log.warn("Delete file on Cloudinary failed for publicId={}", publicId, e);
        }
    }

    @Override
    public String extractPublicId(String imageUrl) {
        if (!StringUtils.hasText(imageUrl) || !imageUrl.contains("/upload/")) {
            return null;
        }
        try {
            String afterUpload = imageUrl.substring(imageUrl.indexOf("/upload/") + "/upload/".length());
            String[] segments = afterUpload.split("/");

            int startIndex = 0;
            if (segments.length > 0 && segments[0].matches("v\\d+")) {
                startIndex = 1;
            }

            StringBuilder publicId = new StringBuilder();
            for (int i = startIndex; i < segments.length; i++) {
                if (i > startIndex) {
                    publicId.append("/");
                }
                publicId.append(segments[i]);
            }

            String result = publicId.toString();
            int dotIndex = result.lastIndexOf('.');
            return dotIndex > -1 ? result.substring(0, dotIndex) : result;
        } catch (Exception e) {
            log.warn("Could not extract publicId from url={}", imageUrl, e);
            return null;
        }
    }
}