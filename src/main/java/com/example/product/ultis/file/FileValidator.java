package com.example.product.ultis.file;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {

    public void validateImageFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new FileValidationException("error.file.empty");
        }

        String contentType = file.getContentType();

        if (contentType == null ||
                !contentType.startsWith("image/")) {

            throw new FileValidationException(
                    "error.file.invalid.type"
            );
        }
    }
}