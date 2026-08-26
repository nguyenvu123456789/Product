package com.example.product.exception;

import lombok.Getter;

@Getter
public class FileStorageException extends RuntimeException {

    private final String messageKey;
    private final Object[] args;

    public FileStorageException(String messageKey, Object... args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }

}