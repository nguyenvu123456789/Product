package com.example.product.dto.common.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
public class ApiResponse<T> {

    private boolean success;
    private int status;
    private String message;
    private T data;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    private ApiResponse(boolean success, int status, String message, T data, Map<String, String> errors) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.data = data;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, 200, message, data, null);
    }

    public static <T> ApiResponse<T> success(T data, int status, String message) {
        return new ApiResponse<>(true, status, message, data, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, 200, "Success", data, null);
    }

    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(false, status, message, null, null);
    }

    public static <T> ApiResponse<T> error(int status, String message, Map<String, String> errors) {
        return new ApiResponse<>(false, status, message, null, errors);
    }

}