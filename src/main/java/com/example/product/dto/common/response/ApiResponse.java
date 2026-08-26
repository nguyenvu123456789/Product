package com.example.product.dto.common.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {

    private final String message;
    private final T data;
    private final LocalDateTime timestamp;

    private ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> of(T data, String message) {
        return new ApiResponse<>(message, data);
    }
}