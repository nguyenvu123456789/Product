package com.example.product.ultis.file;

public class FileValidationException extends RuntimeException {

    private final String messageKey;
    private final Object[] args;

    public FileValidationException(String messageKey, Object... args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public Object[] getArgs() {
        return args;
    }
}