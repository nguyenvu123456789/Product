package com.example.product.ultis.file;

import lombok.Getter;

@Getter
public class FileValidationException extends RuntimeException {

    private final String messageKey;
    private final Object[] args;

    public FileValidationException(String messageKey, Object... args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }

}